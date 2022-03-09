package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.BrandService;
import com.turkcell.rentACarProject.business.abstracts.CarService;
import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.CarListByDailyPrice;
import com.turkcell.rentACarProject.business.dtos.CarListDto;
import com.turkcell.rentACarProject.business.dtos.CarPagedDto;
import com.turkcell.rentACarProject.business.dtos.CarSortedDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.entities.concretes.Car;

@Service
public class CarManager implements CarService{

	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private BrandService brandService;
	private ColorService colorService;
	
	@Autowired
	public  CarManager(CarDao carDao,ModelMapperService modelMapperService, @Lazy BrandService brandService, @Lazy ColorService colorService ) {
		this.carDao = carDao;
		this.brandService = brandService;
		this.colorService = colorService;
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
	public Result add(CreateCarRequest createCarRequest)  throws BusinessException {
		
		this.brandService.isExistsByBrandId(createCarRequest.getBrandId());
		this.colorService.isExistsByColorId(createCarRequest.getColorId());
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult("Car added");
		
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {

		isExistsByCarId(updateCarRequest.getCarId());
		this.brandService.isExistsByBrandId(updateCarRequest.getBrandId());
		this.colorService.isExistsByColorId(updateCarRequest.getColorId());
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult("Car updated");
		
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
		
		isExistsByCarId(deleteCarRequest.getCarId());
		
		this.carDao.deleteById(deleteCarRequest.getCarId());			
		return new SuccessResult("Car deleted");
		
	}

	@Override
	public DataResult<GetCarDto> getById(int id) throws BusinessException {

		isExistsByCarId(id);
		
		Car car = this.carDao.getById(id);
		GetCarDto carDto = this.modelMapperService.forDto().map(car, GetCarDto.class);
		return new SuccessDataResult<GetCarDto>(carDto, "Car listed");
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
	
	public void isExistsByCarId(int carId) throws BusinessException {
		if(!this.carDao.existsByCarId(carId)) {
			throw new BusinessException("Car id not exists");
		}
	}
	
	@Override
	public void isNotExistsByCar_BrandId(int brandId) throws BusinessException {
		if(this.carDao.existsByBrand_BrandId(brandId)) {
			throw new BusinessException("Brand id used by a car");
		}
	}
	
	@Override
	public void isNotExistsByCar_ColorId(int colorId) throws BusinessException {
		if(this.carDao.existsByColor_ColorId(colorId)) {
			throw new BusinessException("Color id used by a car");
		}
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
