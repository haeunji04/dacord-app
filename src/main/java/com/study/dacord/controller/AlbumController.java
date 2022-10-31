package com.study.dacord.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/album")
public class AlbumController {
	
	@GetMapping("/list")
	public String list() {
		return "album/list";
	}
}
