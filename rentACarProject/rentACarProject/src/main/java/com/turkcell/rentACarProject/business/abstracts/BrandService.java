package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.brandDtos.lists.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.brandDtos.gets.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.brandRequests.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.BrandExistsInCarException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface BrandService {
	
	DataResult<List<BrandListDto>> getAll();
	
	Result add(CreateBrandRequest createBrandRequest) throws BrandAlreadyExistsException;
	Result update(UpdateBrandRequest updateBrandRequest) throws BrandAlreadyExistsException, BrandNotFoundException;
	Result delete(DeleteBrandRequest deleteBrandRequest) throws BrandNotFoundException, BrandExistsInCarException;
	
	DataResult<GetBrandDto> getById(int colorId) throws BrandNotFoundException;
	
	void checkIsExistsByBrandId(int brandId) throws BrandNotFoundException;
	void checkIsNotExistByBrandName(String brandName) throws BrandAlreadyExistsException;

}
