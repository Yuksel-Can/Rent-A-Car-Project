package com.turkcell.rentACarProject.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.business.dtos.carDtos.gets.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.carDtos.lists.*;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions.CarExistsInCarCrashException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.*;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarExistsInCarMaintenanceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions.ColorNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.CarAlreadyExistsInRentalCarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.requests.carRequests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.carRequests.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.carRequests.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CarDao;
import com.turkcell.rentACarProject.entities.concretes.Car;

@Service
public class CarManager implements CarService{

	private final CarDao carDao;
	private final ModelMapperService modelMapperService;
	private final BrandService brandService;
	private final ColorService colorService;
	private final RentalCarService rentalCarService;
	private final CarMaintenanceService carMaintenanceService;
	private final CarCrashService carCrashService;
	
	@Autowired
	public  CarManager(CarDao carDao, ModelMapperService modelMapperService, @Lazy BrandService brandService, @Lazy ColorService colorService,
					   RentalCarService rentalCarService, @Lazy CarMaintenanceService carMaintenanceService, @Lazy CarCrashService carCrashService) {
		this.carDao = carDao;
		this.brandService = brandService;
		this.colorService = colorService;
		this.rentalCarService = rentalCarService;
		this.carMaintenanceService = carMaintenanceService;
		this.carCrashService = carCrashService;
		this.modelMapperService = modelMapperService;

	}

	@Override
	public DataResult<List<CarListDto>> getAll() {
		
		List<Car> cars = this.carDao.findAll();

		List<CarListDto> carsDto = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(carsDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws ModelYearAfterThisYearException, BrandNotFoundException, ColorNotFoundException {

		this.brandService.checkIsExistsByBrandId(createCarRequest.getBrandId());
		this.colorService.checkIsExistsByColorId(createCarRequest.getColorId());
		checkIsModelYearBeforeThisYear(createCarRequest.getModelYear());

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

		this.carDao.save(car);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws ModelYearAfterThisYearException, BrandNotFoundException, CarNotFoundException, ColorNotFoundException {

		checkIsExistsByCarId(updateCarRequest.getCarId());
		this.brandService.checkIsExistsByBrandId(updateCarRequest.getBrandId());
		this.colorService.checkIsExistsByColorId(updateCarRequest.getColorId());
		checkIsModelYearBeforeThisYear(updateCarRequest.getModelYear());

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

		this.carDao.save(car);

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateCarRequest.getCarId());
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws CarExistsInCarCrashException, CarExistsInCarMaintenanceException, CarNotFoundException, CarAlreadyExistsInRentalCarException {

		checkIsExistsByCarId(deleteCarRequest.getCarId());
		this.rentalCarService.checkIsNotExistsByRentalCar_CarId(deleteCarRequest.getCarId());
		this.carMaintenanceService.checkIsExistsByCar_CarId(deleteCarRequest.getCarId());
		this.carCrashService.checkIfNotExistsCarCrashByCar_CarId(deleteCarRequest.getCarId());

		this.carDao.deleteById(deleteCarRequest.getCarId());

		return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + deleteCarRequest.getCarId());
	}

	@Override
	public void updateKilometer(int carId, int kilometer) throws ReturnKilometerLessThanRentKilometerException, CarNotFoundException {

		checkIsExistsByCarId(carId);
		Car car = this.carDao.getById(carId);
		checkIfReturnKilometerValid(car.getKilometer(), kilometer);

		car.setKilometer(kilometer);
		this.carDao.save(car);
	}

	@Override
	public DataResult<GetCarDto> getById(int id) throws CarNotFoundException {

		checkIsExistsByCarId(id);

		Car car = this.carDao.getById(id);

		GetCarDto carDto = this.modelMapperService.forDto().map(car, GetCarDto.class);

		return new SuccessDataResult<>(carDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarListByBrandIdDto>> getAllByCar_BrandId(int brandId) throws BrandNotFoundException {

		this.brandService.checkIsExistsByBrandId(brandId);

		List<Car> cars = this.carDao.getAllByBrand_BrandId(brandId);

		List<CarListByBrandIdDto> result = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarListByBrandIdDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(result, BusinessMessages.CarMessages.CAR_LISTED_BY_BRAND_ID + brandId);
	}

	@Override
	public DataResult<List<CarListByColorIdDto>> getAllByCar_ColorId(int colorId) throws ColorNotFoundException {

		this.colorService.checkIsExistsByColorId(colorId);

		List<Car> cars = this.carDao.getAllByColor_ColorId(colorId);

		List<CarListByColorIdDto> result = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarListByColorIdDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(result, BusinessMessages.CarMessages.CAR_LISTED_BY_COLOR_ID + colorId);
	}

	@Override
	public DataResult<List<CarListByDailyPriceDto>> findByDailyPriceLessThenEqual(double dailyPrice) {

		List<Car> cars = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);

		List<CarListByDailyPriceDto> response = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarListByDailyPriceDto.class))
					.collect(Collectors.toList());

		return new SuccessDataResult<>(response, BusinessMessages.CarMessages.CAR_LISTED_BY_LESS_THEN_EQUAL + dailyPrice);
	}

	@Override
	public DataResult<List<CarPagedDto>> getAllPagedCar(int pageNo, int pageSize) throws CarNotFoundException {

		checkIfPageNoAndPageSizeValid(pageNo, pageSize);

		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<Car> cars = this.carDao.findAll(pageable).getContent();

		List<CarPagedDto> result = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarPagedDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(result,BusinessMessages.CarMessages.ALL_CARS_PAGED);
	}

	@Override
	public DataResult<List<CarSortedDto>> getAllSortedCar(int sort) {

		Sort sortList=selectSortedType(sort);

		List<Car> cars = this.carDao.findAll(sortList);

		List<CarSortedDto> result = cars.stream().map(car -> this.modelMapperService.forDto().map(car, CarSortedDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<>(result, BusinessMessages.CarMessages.ALL_CARS_SORTED);
	}


	/**/

	private void checkIsModelYearBeforeThisYear(int modelYear) throws ModelYearAfterThisYearException {
		if(modelYear > LocalDate.now().getYear()){
			throw new ModelYearAfterThisYearException(BusinessMessages.CarMessages.MODEL_YEAR_CANNOT_AFTER_TODAY + modelYear);
		}
	}

	private void checkIfReturnKilometerValid(int beforeKilometer, int afterKilometer) throws ReturnKilometerLessThanRentKilometerException {
		if(beforeKilometer> afterKilometer){
			throw new ReturnKilometerLessThanRentKilometerException(BusinessMessages.CarMessages.DELIVERED_KILOMETER_CANNOT_LESS_THAN_RENTED_KILOMETER);
		}
	}

	private void checkIfPageNoAndPageSizeValid(int pageNo, int pageSize) throws CarNotFoundException {
		if(pageNo <= 0 || pageSize <= 0) {
			throw new CarNotFoundException(BusinessMessages.CarMessages.PAGE_NO_OR_PAGE_SIZE_NOT_VALID);
		}
	}

	@Override
	public void checkIsExistsByCarId(int carId) throws CarNotFoundException {
		if(!this.carDao.existsByCarId(carId)) {
			throw new CarNotFoundException(BusinessMessages.CarMessages.CAR_ID_NOT_FOUND + carId);
		}
	}

	@Override
	public void checkIsNotExistsByCar_BrandId(int brandId) throws BrandExistsInCarException {

		if(this.carDao.existsByBrand_BrandId(brandId)) {
			throw new BrandExistsInCarException(BusinessMessages.CarMessages.BRAND_ID_ALREADY_EXISTS_IN_THE_CAR_TABLE + brandId);
		}
	}

	@Override
	public void checkIsNotExistsByCar_ColorId(int colorId) throws ColorExistsInCarException {

		if(this.carDao.existsByColor_ColorId(colorId)) {
			throw new ColorExistsInCarException(BusinessMessages.CarMessages.COLOR_ID_ALREADY_EXISTS_IN_THE_CAR_TABLE + colorId);
		}
	}

	@Override
	public double getDailyPriceByCarId(int carId) {
		Car car = this.carDao.getById(carId);
		return car.getDailyPrice();
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
