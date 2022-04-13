package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.brandDtos.lists.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.brandDtos.gets.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.brandRequests.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
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

		return new SuccessDataResult<>(result, "Brand listed");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {

		checkIsNotExistByBrandName(createBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		this.brandDao.save(brand);

		return new SuccessResult("Brand added");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
				
		checkIsExistsByBrandId(updateBrandRequest.getBrandId());
		checkIsNotExistByBrandName(updateBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

		this.brandDao.save(brand);

		return new SuccessResult("Brand updated");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
		
		checkIsExistsByBrandId(deleteBrandRequest.getBrandId());
		this.carService.checkIsNotExistsByCar_BrandId(deleteBrandRequest.getBrandId());
		
		this.brandDao.deleteById(deleteBrandRequest.getBrandId());

		return new SuccessResult("Brand deleted");
	}

	@Override
	public DataResult<GetBrandDto> getById(int id) throws BusinessException {

		checkIsExistsByBrandId(id);
			
		Brand brand = this.brandDao.getById(id);

		GetBrandDto getBrandDto = this.modelMapperService.forDto().map(brand, GetBrandDto.class);

		return new SuccessDataResult<>(getBrandDto, "Brand listed");
	}
	
	/**/
	
	public void checkIsExistsByBrandId(int id) throws BusinessException {
		if(!this.brandDao.existsByBrandId(id)) {
			throw new BusinessException("Brand id not exists");
		}
		
	}

	public void checkIsNotExistByBrandName(String name) throws BusinessException {
		if(this.brandDao.existsByBrandName(name)) {
			throw new BusinessException("Brand name already exists");
		}
	}

}
