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
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.result.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.ColorDao;
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
	public DataResult<List<ColorListDto>> getAll() {
		List<Color> colors = this.colorDao.findAll();
		
		List<ColorListDto> result = colors.stream().map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
		.collect(Collectors.toList());
		
		return new SuccessDataResult<List<ColorListDto>>(result, "Color listed");
		
 }

	@Override
	public Result add(CreateColorRequest createColorRequest) {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		if(!existByName(color.getColorName())) {
			this.colorDao.save(color);
			
			return new SuccessResult("Color added");
		}
		return new ErrorResult("Color not added");
		
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {

		try {
			isExistsByColorId(updateColorRequest.getColorId());
			
			Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

			if(!existByName(color.getColorName())) {
				this.colorDao.save(color);
				
				return new SuccessResult("Color updated");
			}
			return new ErrorResult("Color not updated");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return new ErrorResult("Color not updated");
		}
		
	}

	@Override
	public Result delete(int id) {
		try {
			isExistsByColorId(id);
			this.colorDao.deleteById(id);
			
			return new SuccessResult("Color deleted");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			
			return new ErrorResult("Color not deleted");
		}
		
	}

	@Override
	public DataResult<GetColorDto> getById(int id) {
		try {
			isExistsByColorId(id);
			Color color = this.colorDao.getById(id);
			GetColorDto getColorDto = this.modelMapperService.forDto().map(color, GetColorDto.class);
			
			return new SuccessDataResult<GetColorDto>(getColorDto, "Color listed");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ErrorDataResult<GetColorDto>("Color not listed");
		}
		
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
