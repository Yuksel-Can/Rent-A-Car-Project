package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.CarListDto;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.requests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.UpdateCarRequest;



@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private CarService carService;
	
	@Autowired
	public CarsController(CarService carService) {
		this.carService = carService;
	}
	
	
	@GetMapping("/getAll")
	public List<CarListDto> getAll(){
		return this.carService.getAll();
	}
	
	@PutMapping("/update")
	public void update(@RequestBody UpdateCarRequest updateCarRequest) {
		this.carService.update(updateCarRequest);
	}
	
	@DeleteMapping("/delete")
	public void delete(@RequestParam int id) {
		this.carService.delete(id);
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateCarRequest createCarRequest) {
		this.carService.add(createCarRequest);
	}
	
	@GetMapping("/getById")
	public GetCarDto getById(@RequestParam int id) {
		return this.carService.getById(id);
	}
	
}
