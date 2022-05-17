package com.turkcell.rentACarProject.api.controllers;

import java.util.List;

import javax.validation.Valid;

import com.turkcell.rentACarProject.business.dtos.carDtos.lists.*;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions.CarExistsInCarCrashException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.CarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.ModelYearAfterThisYearException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarExistsInCarMaintenanceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions.ColorNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.CarAlreadyExistsInRentalCarException;
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
import com.turkcell.rentACarProject.business.dtos.carDtos.gets.GetCarDto;
import com.turkcell.rentACarProject.business.requests.carRequests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.carRequests.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.carRequests.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	
	private final CarService carService;
	
	@Autowired
	public CarsController(CarService carService) {
		this.carService = carService;
	}
	

	@GetMapping("/getAll")
	public DataResult<List<CarListDto>> getAll(){
		return this.carService.getAll();
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws ColorNotFoundException, ModelYearAfterThisYearException, BrandNotFoundException {
		return this.carService.add(createCarRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) throws ColorNotFoundException, CarNotFoundException, ModelYearAfterThisYearException, BrandNotFoundException {
		return this.carService.update(updateCarRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarRequest deleteCarRequest) throws CarNotFoundException, CarAlreadyExistsInRentalCarException, CarExistsInCarMaintenanceException, CarExistsInCarCrashException {
		return this.carService.delete(deleteCarRequest);
	}
	
	@GetMapping("/getById")
	public DataResult<GetCarDto> getById(@RequestParam int carId) throws CarNotFoundException {
		return this.carService.getById(carId);
	}

	@GetMapping("/getAllByCar_BrandId")
	public DataResult<List<CarListByBrandIdDto>> getAllByCar_BrandId(@RequestParam int brandId) throws BrandNotFoundException {
		return this.carService.getAllByCar_BrandId(brandId);
	}

	@GetMapping("/getAllByCar_ColorId")
	public DataResult<List<CarListByColorIdDto>> getAllByCar_ColorId(@RequestParam int colorId) throws ColorNotFoundException {
		return this.carService.getAllByCar_ColorId(colorId);
	}

	@GetMapping("findByDailyPriceLessThenEqual")
	public DataResult<List<CarListByDailyPriceDto>> findByDailyPriceLessThenEqual(@RequestParam double dailyPrice){
		return this.carService.findByDailyPriceLessThenEqual(dailyPrice);
	}
	
	@GetMapping("getAllPagedCar")
	public DataResult<List<CarPagedDto>> getAllPagedCar(@RequestParam int pageNo, int pageSize) throws CarNotFoundException {
		return this.carService.getAllPagedCar(pageNo, pageSize);
	}
	
	@GetMapping("getAllSortedCar")
	public DataResult<List<CarSortedDto>> getAllSortedCar(@RequestParam int sort){
		return this.carService.getAllSortedCar(sort);
	}

}
