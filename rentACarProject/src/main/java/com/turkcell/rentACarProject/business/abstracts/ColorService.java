package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.ColorListDto;
import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.requests.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.UpdateColorRequest;

public interface ColorService {
	
	List<ColorListDto> getAll();
	
	void add(CreateColorRequest createColorRequest);
	void update(UpdateColorRequest updateColorRequest);
	void delete(int id);
	
	GetColorDto getById(int id);
}
