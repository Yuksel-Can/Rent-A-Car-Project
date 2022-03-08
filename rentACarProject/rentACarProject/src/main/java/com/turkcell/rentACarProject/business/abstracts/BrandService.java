package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.create.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface BrandService {
	
	DataResult<List<BrandListDto>> getAll();
	
	Result add(CreateBrandRequest createBrandRequest);
	Result update(UpdateBrandRequest updateBrandRequest);
	Result delete(int id);
	
	DataResult<GetBrandDto> getById(int id);

}
