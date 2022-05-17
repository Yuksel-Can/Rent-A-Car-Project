package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.BrandExistsInCarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.brandDtos.lists.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.brandDtos.gets.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.brandRequests.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACarProject.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private final BrandDao brandDao;
	private final CarService carService;
	private final ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService, CarService carService) {
		this.brandDao = brandDao;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {
		
		List<Brand> brands = this.brandDao.findAll();

		List<BrandListDto> result = brands.stream().map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BrandAlreadyExistsException {

		checkIsNotExistByBrandName(createBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		this.brandDao.save(brand);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BrandAlreadyExistsException, BrandNotFoundException {
				
		checkIsExistsByBrandId(updateBrandRequest.getBrandId());
		checkIsNotExistByBrandName(updateBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

		this.brandDao.save(brand);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateBrandRequest.getBrandId());
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BrandNotFoundException, BrandExistsInCarException {
		
		checkIsExistsByBrandId(deleteBrandRequest.getBrandId());
		this.carService.checkIsNotExistsByCar_BrandId(deleteBrandRequest.getBrandId());
		
		this.brandDao.deleteById(deleteBrandRequest.getBrandId());

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + deleteBrandRequest.getBrandId());
	}

	@Override
	public DataResult<GetBrandDto> getById(int id) throws BrandNotFoundException {

		checkIsExistsByBrandId(id);
			
		Brand brand = this.brandDao.getById(id);

		GetBrandDto getBrandDto = this.modelMapperService.forDto().map(brand, GetBrandDto.class);

		return new SuccessDataResult<>(getBrandDto, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + id);
	}
	
	/**/
	
	public void checkIsExistsByBrandId(int id) throws BrandNotFoundException {
		if(!this.brandDao.existsByBrandId(id)) {
			throw new BrandNotFoundException(BusinessMessages.BrandMessages.BRAND_ID_NOT_FOUND + id);
		}
		
	}

	public void checkIsNotExistByBrandName(String name) throws BrandAlreadyExistsException {
		if(this.brandDao.existsByBrandName(name)) {
			throw new BrandAlreadyExistsException(BusinessMessages.BrandMessages.BRAND_NAME_ALREADY_EXISTS + name);
		}
	}

}
