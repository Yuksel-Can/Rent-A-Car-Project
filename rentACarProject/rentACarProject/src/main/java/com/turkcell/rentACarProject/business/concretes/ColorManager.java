package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.ColorExistsInCarException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions.ColorAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions.ColorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.colorDtos.lists.ColorListDto;
import com.turkcell.rentACarProject.business.dtos.colorDtos.gets.GetColorDto;
import com.turkcell.rentACarProject.business.requests.colorRequests.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.colorRequests.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.colorRequests.UpdateColorRequest;
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

		return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws ColorAlreadyExistsException {
		
		checkIsNotExistsByColorName(createColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws ColorAlreadyExistsException, ColorNotFoundException {

		checkIsExistsByColorId(updateColorRequest.getColorId());
		checkIsNotExistsByColorName(updateColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

		this.colorDao.save(color);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateColorRequest.getColorId());
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws ColorExistsInCarException, ColorNotFoundException {

		checkIsExistsByColorId(deleteColorRequest.getColorId());
		this.carService.checkIsNotExistsByCar_ColorId(deleteColorRequest.getColorId());
		
		this.colorDao.deleteById(deleteColorRequest.getColorId());

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + deleteColorRequest.getColorId());
	}

	@Override
	public DataResult<GetColorDto> getById(int id) throws ColorNotFoundException {

		checkIsExistsByColorId(id);
		
		Color color = this.colorDao.getById(id);

		GetColorDto getColorDto = this.modelMapperService.forDto().map(color, GetColorDto.class);

		return new SuccessDataResult<>(getColorDto, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + id);
	}
	
	public void checkIsExistsByColorId(int brandId) throws ColorNotFoundException {
		if(!this.colorDao.existsByColorId(brandId)) {
			throw new ColorNotFoundException(BusinessMessages.ColorMessages.COLOR_ID_NOT_FOUND + brandId);
		}
	}

	private void checkIsNotExistsByColorName(String brandName) throws ColorAlreadyExistsException {
		if(this.colorDao.existsByColorName(brandName)) {
			throw new ColorAlreadyExistsException(BusinessMessages.ColorMessages.COLOR_NAME_ALREADY_EXISTS + brandName);
		}
	}

}
