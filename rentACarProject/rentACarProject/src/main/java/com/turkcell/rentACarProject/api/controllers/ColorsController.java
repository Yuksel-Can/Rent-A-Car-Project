package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.ColorListDto;
import com.turkcell.rentACarProject.business.requests.CreateColorRequest;



@RestController
@RequestMapping("api/colors")
public class ColorsController {
	
	private ColorService colorService;
	
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}
	
	@GetMapping("/getAll")
	public List<ColorListDto> getAll(){
		return this.colorService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateColorRequest createColorRequest) {
		this.colorService.add(createColorRequest);
	}
	
	//http metodları
}
