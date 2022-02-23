package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
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
		try {
			isExistsCarId(updateCarRequest.getCarId());
			
			Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
			this.carDao.save(car);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void delete(int id) {
		try {
			isExistsCarId(id);
			this.carDao.deleteById(id);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	@Override
	public GetCarDto getById(int id) {
		try {
			isExistsCarId(id);
			
			Car car = this.carDao.getById(id);
			GetCarDto carDto = this.modelMapperService.forDto().map(car, GetCarDto.class);
			return carDto;
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**/
	
	boolean isExistsCarId(int id) throws BusinessException {
		if(this.carDao.existsById(id)) {
			return true;
		}
		throw new BusinessException("Car id not exists");
	}
}
