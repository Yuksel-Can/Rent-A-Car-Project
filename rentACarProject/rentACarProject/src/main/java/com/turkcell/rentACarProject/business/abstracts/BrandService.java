package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.brandDtos.lists.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.brandDtos.gets.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.brandRequests.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface BrandService {
	
	DataResult<List<BrandListDto>> getAll();
	
	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;
	Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;
	Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;
	
	DataResult<GetBrandDto> getById(int colorId) throws BusinessException;
	
	void checkIsExistsByBrandId(int brandId) throws BusinessException;
	void checkIsNotExistByBrandName(String brandName) throws BusinessException;

}
