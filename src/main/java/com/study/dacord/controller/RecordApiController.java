package com.study.dacord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.dacord.model.Record;
import com.study.dacord.repository.RecordRepository;

@RestController
@RequestMapping("/api")
class RecordApiController {

	@Autowired
	private RecordRepository repository;

  @GetMapping("/records")
  List<Record> all(@RequestParam(required = false, defaultValue = "") String title,
		  		   @RequestParam(required = false, defaultValue = "") String content) {
	  if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
		  return repository.findAll();
	  } else {
		  return repository.findByTitleOrContent(title, content);
	  }
  }
  // end::get-aggregate-root[]

  @PostMapping("/records")
  Record newRecord(@RequestBody Record newRecord) {
	  return repository.save(newRecord);
  }

  // Single item
  
  @GetMapping("/records/{id}")
  Record one(@PathVariable Long id) {
    
	  return repository.findById(id).orElse(null);
  }

  @PutMapping("/records/{id}")
  Record replaceRecord(@RequestBody Record newRecord, @PathVariable Long id) {
    
	  return repository.findById(id)
			  .map(record -> {
				  record.setTitle(newRecord.getTitle());
				  record.setContent(newRecord.getContent());
				  return repository.save(record);
      })
      .orElseGet(() -> {
        newRecord.setId(id);
        return repository.save(newRecord);
      });
  }

  @DeleteMapping("/records/{id}")
  void deleteRecord(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
