package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.turkcell.rentACarProject.business.requests.delete.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

@RestController
@RequestMapping("api/colors")
public class ColorsController {
	
	private ColorService colorService;
	
	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}
	
	
	@GetMapping("/getAll")
	public DataResult<List<ColorListDto>> getAll(){
		return this.colorService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateColorRequest createColorRequest) throws BusinessException {
		return this.colorService.add(createColorRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateColorRequest updateColorRequest) throws BusinessException {
		return this.colorService.update(updateColorRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteColorRequest deleteColorRequest) throws BusinessException {
		return this.colorService.delete(deleteColorRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetColorDto> getById(@RequestParam int colorId) throws BusinessException {
		return this.colorService.getById(colorId);
	}
	
	
}
