package com.study.dacord.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.study.dacord.model.Record;
import com.study.dacord.repository.RecordRepository;

@Controller
@RequestMapping("/record")
public class RecordController {
	
	@Autowired
	private RecordRepository recordRepository;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Record> records = recordRepository.findAll();
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
		
		if (bindingResult.hasErrors()) {
			return "record/form";
		}
		
		recordRepository.save(record);
		return "redirect:/record/list";
	}
	
}
