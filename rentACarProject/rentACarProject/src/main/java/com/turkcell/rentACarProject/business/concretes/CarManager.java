package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.CarListByDailyPrice;
import com.turkcell.rentACarProject.business.dtos.CarListDto;
import com.turkcell.rentACarProject.business.dtos.CarPagedDto;
import com.turkcell.rentACarProject.business.dtos.CarSortedDto;
import com.turkcell.rentACarProject.business.requests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.result.ErrorResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
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
	public DataResult<List<CarListDto>> getAll() {
		
		List<Car> cars = this.carDao.findAll();
		
		List<CarListDto> carsDto = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<CarListDto>>(carsDto, "Cars listed");
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult("Car added");
		
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {
		try {
			isExistsCarId(updateCarRequest.getCarId());
			
			Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
			this.carDao.save(car);
			
			return new SuccessResult("Car updated");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ErrorResult("Car not updated");
		}
	}

	@Override
	public Result delete(int id) {
		try {
			isExistsCarId(id);
			this.carDao.deleteById(id);
			
			return new SuccessResult("Car deleted");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ErrorResult("Car not deleted");
		}
		
	}

	@Override
	public DataResult<GetCarDto> getById(int id) {
		try {
			isExistsCarId(id);
			
			Car car = this.carDao.getById(id);
			GetCarDto carDto = this.modelMapperService.forDto().map(car, GetCarDto.class);
			
			return new SuccessDataResult<GetCarDto>(carDto, "Car listed");
			
		} catch (BusinessException e) {
			System.out.println(e.getMessage());
			
			return new ErrorDataResult<GetCarDto>("Car not listed");
		}
	}
	
	@Override
	public DataResult<List<CarListByDailyPrice>> findByDailyPriceLessThenEqual(double dailyPrice) {
			List<Car> cars = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
			List<CarListByDailyPrice> response = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarListByDailyPrice.class))
					.collect(Collectors.toList());
		return new SuccessDataResult<List<CarListByDailyPrice>>(response, "Less than Car listed");
	}
	
	@Override
	public DataResult<List<CarPagedDto>> getAllPagedCar(int pageNo, int pageSize) {
		
		if(pageNo <= 0 || pageSize <= 0) {
			return new ErrorDataResult<>("please enter valid value");
		}
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<Car> cars = this.carDao.findAll(pageable).getContent();
		
		List<CarPagedDto> carPagedDtos = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarPagedDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarPagedDto>>(carPagedDtos, "All cars paged");
	}
	
	@Override
	public DataResult<List<CarSortedDto>> getAllSortedCar(int sort) {
		
		Sort sortList=selectSortedType(sort);
		
		List<Car> cars = this.carDao.findAll(sortList);
		
		List<CarSortedDto> carSortedDtos = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarSortedDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarSortedDto>>(carSortedDtos, "All cars sorted");
	}
	
	/**/
	
	boolean isExistsCarId(int id) throws BusinessException {
		if(this.carDao.existsById(id)) {
			return true;
		}
		throw new BusinessException("Car id not exists");
	}
	
	Sort selectSortedType(int sort) {
		
		if(sort==1) {
			return Sort.by(Sort.Direction.ASC, "dailyPrice");
		}else if(sort==0) {
			return Sort.by(Sort.Direction.DESC, "dailyPrice");
		}else {
			return Sort.by(Sort.Direction.DESC, "dailyPrice");
		}
	}
}
