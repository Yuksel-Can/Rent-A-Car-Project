package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.CarDto;
import com.turkcell.rentACarProject.business.dtos.CarListDto;
import com.turkcell.rentACarProject.business.requests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.entities.concretes.Car;

@Service
public class CarManager implements CarService{


	private CarDao carDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public  CarManager(CarDao carDao,ModelMapperService modelMapperService ) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}
	@Override
	public List<CarListDto> getAll() {
		
		List<Car> cars = this.carDao.findAll();
		
		List<CarListDto> carsDto = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		return carsDto;
	}

	@Override
	public void add(CreateCarRequest createCarRequest) {
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		
	}

	@Override
	public void update(UpdateCarRequest updateCarRequest) {

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);
	}

	@Override
	public void delete(int id) {
		try {
			isCarExists(id);
			this.carDao.deleteById(id);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public CarDto getById(int id) {
		Car car = this.carDao.getById(id);
		CarDto carDto = this.modelMapperService.forDto().map(car, CarDto.class);
		return carDto;
	}
	
	boolean isCarExists(int id) throws BusinessException {
		if(this.carDao.existsById(id)) {
			return true;
		}
		throw new BusinessException("Lütfen geçerli bir id giriniz");
	}
}
