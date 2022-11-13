package com.study.dacord.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.dacord.model.Record;
import com.study.dacord.repository.RecordRepository;
import com.study.dacord.validator.RecordValidator;

@Controller
@RequestMapping("/record")
public class RecordController {
	
	@Autowired
	private RecordRepository recordRepository;
	
	@Autowired
	private RecordValidator recordValidator;
	
	@GetMapping("/list")
	public String list(Model model, @PageableDefault(size = 2) Pageable pageable, 
					   @RequestParam(required=false, defaultValue="") String searchText) {
//		Page<Record> records = recordRepository.findAll(pageable);
		Page<Record> records = recordRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
		int startPage = Math.max(1, records.getPageable().getPageNumber() - 4);
		int endPage = Math.min(records.getTotalPages(), records.getPageable().getPageNumber() + 4);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);		
		model.addAttribute("records", records);
		
		return "record/list";
	}
	
	@GetMapping("/form")
	public String form(Model model, @RequestParam(required = false) Long id) {
		
		if(id == null) {
			model.addAttribute("record", new Record());			
		} else {
			Record record = recordRepository.findById(id).orElse(null);
			model.addAttribute("record", record);
		}
		
		return "record/form";
	}
	
	@PostMapping("/form")
	public String formSubmit(@Valid Record record, BindingResult bindingResult) {
		recordValidator.validate(record, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "record/form";
		}
		
		recordRepository.save(record);
		return "redirect:/record/list";
	}
	
}
