package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.CreateBrandRequest;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private BrandService brandService; 
	
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}
	
	@GetMapping("/getAll")
	public List<BrandListDto> getAll(){
		return this.brandService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateBrandRequest createBrandRequest) {
		this.brandService.add(createBrandRequest);
	}
	
	@GetMapping("/getById")
	public GetBrandDto getById(@RequestParam int id){
		return this.brandService.getById(id);
	}
}
