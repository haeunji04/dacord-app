package com.study.dacord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String form(Model model) {
		model.addAttribute("record", new Record());
		return "record/form";
	}
	
	@PostMapping("/form")
	public String formSubmit(@ModelAttribute Record record) {
		recordRepository.save(record);
		return "redirect:/record/list";
	}
	
}
