package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.ColorListDto;
import com.turkcell.rentACarProject.business.requests.CreateColorRequest;
import com.turkcell.rentACarProject.entities.concretes.Color;

public interface ColorService {
	
	List<ColorListDto> getAll();
	
	void add(CreateColorRequest cleareColorRequest);
	
	Color getById(int id);
}
