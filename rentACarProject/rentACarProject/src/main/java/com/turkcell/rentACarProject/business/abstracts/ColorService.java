package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.ColorListDto;
import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.requests.create.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface ColorService {
	
	DataResult<List<ColorListDto>> getAll();
	
	Result add(CreateColorRequest createColorRequest) throws BusinessException;
	Result update(UpdateColorRequest updateColorRequest) throws BusinessException;
	Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException;
	
	DataResult<GetColorDto> getById(int colorId) throws BusinessException;

	void checkIsExistsByColorId(int colorId) throws BusinessException;
	void checkIsNotExistsByColorName(String colorName) throws BusinessException;
}
