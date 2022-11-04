package com.study.dacord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.dacord.model.Album;
import com.study.dacord.repository.AlbumRepository;

@Controller
@RequestMapping("/album")
public class AlbumController {
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Album> albums = albumRepository.findAll();
		model.addAttribute("albums", albums);
		
		return "album/list";
	}
}
