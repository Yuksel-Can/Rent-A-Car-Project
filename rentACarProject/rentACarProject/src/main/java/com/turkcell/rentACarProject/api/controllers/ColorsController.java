package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.ColorListDto;
import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.requests.create.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

@RestController
@RequestMapping("api/colors")
public class ColorsController {
	
	private ColorService colorService;
	
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<ColorListDto>> getAll(){
		return this.colorService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) {
		return this.colorService.add(createColorRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest) {
		return this.colorService.update(updateColorRequest);
	}
	@DeleteMapping("/delete")
	public Result delete(@RequestParam int id) {
		return this.colorService.delete(id);
	}
	
	@GetMapping("getById")
	public DataResult<GetColorDto> getById(@RequestParam int id) {
		return this.colorService.getById(id);
	}
	
	
	//http metodları
}
