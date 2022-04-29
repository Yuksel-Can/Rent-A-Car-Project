package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.BrandExistsInCarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.brandDtos.lists.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.brandDtos.gets.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.brandRequests.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	
	private final BrandService brandService;
	
	@Autowired
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}
	
	
	@GetMapping("/getAll")
	public DataResult<List<BrandListDto>> getAll(){
		return this.brandService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) throws BrandAlreadyExistsException {
		return this.brandService.add(createBrandRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest) throws BrandAlreadyExistsException, BrandNotFoundException {
		return this.brandService.update(updateBrandRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteBrandRequest deleteBrandRequest) throws BrandNotFoundException, BrandExistsInCarException {
		return this.brandService.delete(deleteBrandRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetBrandDto> getById(@RequestParam int brandId) throws BrandNotFoundException {
		return this.brandService.getById(brandId);
	}

}
