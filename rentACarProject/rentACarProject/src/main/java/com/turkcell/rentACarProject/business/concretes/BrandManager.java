package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.dtos.BrandListDto;
import com.turkcell.rentACarProject.business.dtos.GetBrandDto;
import com.turkcell.rentACarProject.business.requests.CreateBrandRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACarProject.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<BrandListDto> getAll() {
		List<Brand> brands = this.brandDao.findAll();

		List<BrandListDto> result = brands.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());

		return result;
	}

	@Override
	public void add(CreateBrandRequest createBrandRequest) {

		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		if (!isExistByName(brand.getBrandName())) {
			this.brandDao.save(brand);
		}

	}

	public boolean isExistByName(String name) {
		return this.brandDao.existsByBrandName(name);
	}

	@Override
	public GetBrandDto getById(int id) {
		
		Brand brand = this.brandDao.getById(id);
		
		GetBrandDto getBrandDto = this.modelMapperService.forDto().map(brand, GetBrandDto.class);
		
		return getBrandDto;
		
	}

}
