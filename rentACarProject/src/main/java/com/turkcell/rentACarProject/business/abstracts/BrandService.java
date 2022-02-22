package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.UpdateBrandRequest;

public interface BrandService {
	
	List<BrandListDto> getAll();
	
	void add(CreateBrandRequest createBrandRequest);
	void update(UpdateBrandRequest updateBrandRequest);
	void delete(int id);
	
	GetBrandDto getById(int id);
	

}
