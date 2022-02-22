package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.ColorListDto;
import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.requests.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACarProject.entities.concretes.Brand;
import com.turkcell.rentACarProject.entities.concretes.Color;

@Service
public class ColorManager implements ColorService{
	
	private ColorDao colorDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public ColorManager(ColorDao colorDao,ModelMapperService modelMapperService) {
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<ColorListDto> getAll() {
		List<Color> colors = this.colorDao.findAll();
		
		List<ColorListDto> result = colors.stream().map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
		.collect(Collectors.toList());
		
		return result;
		
 }

	@Override
	public void add(CreateColorRequest createColorRequest) {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		if(!existByName(color.getColorName())) {
			this.colorDao.save(color);

		}
		
	}

	@Override
	public void update(UpdateColorRequest updateColorRequest) {

		try {
			isExistsByColorId(updateColorRequest.getColorId());
			
			Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
			this.colorDao.save(color);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public void delete(int id) {
		try {
			isExistsByColorId(id);
			this.colorDao.deleteById(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public GetColorDto getById(int id) {
		try {
			isExistsByColorId(id);
			Color color = this.colorDao.getById(id);
			GetColorDto getColorDto = this.modelMapperService.forDto().map(color, GetColorDto.class);
			return getColorDto;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	/**/
	
	private boolean isExistsByColorId(int id) throws BusinessException {
		if(this.colorDao.existsByColorId(id)) {
			return true;
		}
		throw new BusinessException("Color id not exists");
	}
	
	public boolean existByName(String name) {
		return this.colorDao.existsByColorName(name);
	}

}
