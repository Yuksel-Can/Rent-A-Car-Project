package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.create.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface BrandService {
	
	DataResult<List<BrandListDto>> getAll();
	
	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;
	Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;
	Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;
	
	DataResult<GetBrandDto> getById(int id) throws BusinessException;
	
	void checkIsExistsByBrandId(int brandId) throws BusinessException;
	void checkIsNotExistByBrandName(String brandName) throws BusinessException;

}
