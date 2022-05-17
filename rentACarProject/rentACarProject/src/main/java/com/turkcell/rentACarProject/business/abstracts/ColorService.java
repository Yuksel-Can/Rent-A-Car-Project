package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.colorDtos.lists.ColorListDto;
import com.turkcell.rentACarProject.business.dtos.colorDtos.gets.GetColorDto;
import com.turkcell.rentACarProject.business.requests.colorRequests.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.colorRequests.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.colorRequests.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.ColorExistsInCarException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions.ColorAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions.ColorNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface ColorService {
	
	DataResult<List<ColorListDto>> getAll();
	
	Result add(CreateColorRequest createColorRequest) throws ColorAlreadyExistsException;
	Result update(UpdateColorRequest updateColorRequest) throws ColorAlreadyExistsException, ColorNotFoundException;
	Result delete(DeleteColorRequest deleteColorRequest) throws ColorExistsInCarException, ColorNotFoundException;
	
	DataResult<GetColorDto> getById(int colorId) throws ColorNotFoundException;

	void checkIsExistsByColorId(int colorId) throws ColorNotFoundException;
}
