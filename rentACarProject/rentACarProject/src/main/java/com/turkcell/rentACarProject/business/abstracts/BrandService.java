package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.CreateBrandRequest;

public interface BrandService {
	
	List<BrandListDto> getAll();
	
	void add(CreateBrandRequest createBrandRequest);
	
	GetBrandDto getById(int id);
	

}
