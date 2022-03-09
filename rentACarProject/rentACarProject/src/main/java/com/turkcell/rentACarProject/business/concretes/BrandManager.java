package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.create.CreateBrandRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteBrandRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.result.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACarProject.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {
		List<Brand> brands = this.brandDao.findAll();

		List<BrandListDto> result = brands.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<BrandListDto>>(result, "Brand listed");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		if (!isExistByName(brand.getBrandName())) {
			this.brandDao.save(brand);
			
			return new SuccessResult("Brand added");
		}
		return new ErrorResult("Brand not added");

	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {
		
		try {
			isExistsByBrandId(updateBrandRequest.getBrandId());
			
			Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
			if (!isExistByName(brand.getBrandName())) {
				this.brandDao.save(brand);
				return new SuccessResult("Brand updated");
			}		
			return new ErrorResult("Brand not updated ");
			
		} catch (Exception e) {
			return new ErrorResult("Brand not updated");
		}
		
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) {
		try {
			isExistsByBrandId(deleteBrandRequest.getBrandId());
			this.brandDao.deleteById(deleteBrandRequest.getBrandId());
			
			return new SuccessResult("Brand deleted");
			
		} catch (Exception e) {
			return new ErrorResult("Brand not deleted");
		}
		
	}

	@Override
	public DataResult<GetBrandDto> getById(int id) {
		try {
			isExistsByBrandId(id);
			
			Brand brand = this.brandDao.getById(id);
			GetBrandDto getBrandDto = this.modelMapperService.forDto().map(brand, GetBrandDto.class);
			
			return new SuccessDataResult<GetBrandDto>(getBrandDto, "Brand listed");
			
		} catch (Exception e) {
			return new ErrorDataResult<>("Brand dont list");
		}
		
	}
	
	/**/
	
	private boolean isExistsByBrandId(int id) throws BusinessException {
		if(this.brandDao.existsByBrandId(id)) {
			return true;
		}
		throw new BusinessException("Brand id not exists");
	}

	public boolean isExistByName(String name) {
		return this.brandDao.existsByBrandName(name);
	}

}
