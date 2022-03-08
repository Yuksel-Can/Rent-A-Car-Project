package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.ColorListDto;
import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.requests.create.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface ColorService {
	
	DataResult<List<ColorListDto>> getAll();
	
	Result add(CreateColorRequest createColorRequest);
	Result update(UpdateColorRequest updateColorRequest);
	Result delete(int id);
	
	DataResult<GetColorDto> getById(int id);
}
