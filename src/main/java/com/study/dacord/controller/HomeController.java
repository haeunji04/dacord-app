package com.study.dacord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.dacord.model.Record;
import com.study.dacord.repository.RecordRepository;

@Controller
public class HomeController {

	@Autowired
	private RecordRepository recordRepository;
	
	@GetMapping
	public String index(Model model) {
		List<Record> records = recordRepository.findAll();
		model.addAttribute("records", records);
		
		return "index";
	}
	
}
 