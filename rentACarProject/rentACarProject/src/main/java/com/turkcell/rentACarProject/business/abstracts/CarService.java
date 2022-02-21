package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.CarDto;
import com.turkcell.rentACarProject.business.dtos.CarListDto;
import com.turkcell.rentACarProject.business.requests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.UpdateCarRequest;

public interface CarService {

	
	List<CarListDto> getAll();
	
	void add(CreateCarRequest createCarRequest);
	void update(UpdateCarRequest updateCarRequest);
	void delete(int id);
	
	CarDto getById(int id);
}
