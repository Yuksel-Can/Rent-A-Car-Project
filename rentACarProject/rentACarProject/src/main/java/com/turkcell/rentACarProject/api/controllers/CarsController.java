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
import com.turkcell.rentACarProject.business.dtos.CarListByDailyPrice;
import com.turkcell.rentACarProject.business.dtos.CarListDto;
import com.turkcell.rentACarProject.business.dtos.CarPagedDto;
import com.turkcell.rentACarProject.business.dtos.CarSortedDto;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;



@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private CarService carService;
	
	@Autowired
	public CarsController(CarService carService) {
		this.carService = carService;
	}
	
	
	@GetMapping("/getAll")
	public DataResult<List<CarListDto>> getAll(){
		return this.carService.getAll();
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestParam int id) {
		return this.carService.delete(id);
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateCarRequest createCarRequest) {
		return this.carService.add(createCarRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetCarDto> getById(@RequestParam int id) {
		return this.carService.getById(id);
	}
	
	@GetMapping("findByDailyPriceLessThenEqual")
	public DataResult<List<CarListByDailyPrice>> findByDailyPriceLessThenEqual(@RequestParam double dailyPrice){
		return this.carService.findByDailyPriceLessThenEqual(dailyPrice);
	}
	
	@GetMapping("getAllPagedCar")
	public DataResult<List<CarPagedDto>> getAllPagedCar(@RequestParam int pageNo, int pageSize){
		return this.carService.getAllPagedCar(pageNo, pageSize);
	}
	
	@GetMapping("getAllSortedCar")
	public DataResult<List<CarSortedDto>> getAllSortedCar(@RequestParam int sort){
		return this.carService.getAllSortedCar(sort);
	}
	
	
}
