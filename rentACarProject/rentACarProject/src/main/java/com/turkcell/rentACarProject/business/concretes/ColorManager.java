package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.ColorListDto;
import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.requests.create.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACarProject.entities.concretes.Color;

@Service
public class ColorManager implements ColorService{
	
	private final ColorDao colorDao;
	private final CarService carService;
	private final ModelMapperService modelMapperService;
	
	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService, CarService carService) {
		this.colorDao = colorDao;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() {
		
		List<Color> colors = this.colorDao.findAll();

		List<ColorListDto> result = colors.stream().map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(result, "Color listed");
		
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {
		
		checkIsNotExistsByColorName(createColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult("Color added");
		
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {

		checkIsExistsByColorId(updateColorRequest.getColorId());
		checkIsNotExistsByColorName(updateColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult("Color updated");

	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {

		checkIsExistsByColorId(deleteColorRequest.getColorId());
		this.carService.checkIsNotExistsByCar_ColorId(deleteColorRequest.getColorId());
		
		this.colorDao.deleteById(deleteColorRequest.getColorId());

		return new SuccessResult("Color deleted");
			
	}

	@Override
	public DataResult<GetColorDto> getById(int id) throws BusinessException {

		checkIsExistsByColorId(id);
		
		Color color = this.colorDao.getById(id);

		GetColorDto getColorDto = this.modelMapperService.forDto().map(color, GetColorDto.class);

		return new SuccessDataResult<>(getColorDto, "Color listed");
		
	}
	
	/**/
	
	public void checkIsExistsByColorId(int brandId) throws BusinessException {
		if(!this.colorDao.existsByColorId(brandId)) {
			throw new BusinessException("Color id not exists");
		}
	}
	
	public void checkIsNotExistsByColorName(String brandName) throws BusinessException {
		if(this.colorDao.existsByColorName(brandName)) {
			throw new BusinessException("Color name already exists");
		}
	}

}
