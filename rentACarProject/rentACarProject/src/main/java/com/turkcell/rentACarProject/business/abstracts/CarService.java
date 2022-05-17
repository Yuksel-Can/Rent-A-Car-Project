package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.carDtos.gets.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.carDtos.lists.*;
import com.turkcell.rentACarProject.business.requests.carRequests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.carRequests.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.carRequests.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.brandExceptions.BrandNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions.CarExistsInCarCrashException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions.*;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions.CarExistsInCarMaintenanceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.colorExceptions.ColorNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.CarAlreadyExistsInRentalCarException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface CarService {

	DataResult<List<CarListDto>> getAll();
	
	Result add(CreateCarRequest createCarRequest) throws ModelYearAfterThisYearException, BrandNotFoundException, ColorNotFoundException;
	Result update(UpdateCarRequest updateCarRequest) throws ModelYearAfterThisYearException, BrandNotFoundException, CarNotFoundException, ColorNotFoundException;
	Result delete(DeleteCarRequest deleteCarRequest) throws CarExistsInCarCrashException, CarExistsInCarMaintenanceException, CarNotFoundException, CarAlreadyExistsInRentalCarException;

	void updateKilometer(int carId, int kilometer) throws ReturnKilometerLessThanRentKilometerException, CarNotFoundException;

	DataResult<List<CarListByDailyPriceDto>> findByDailyPriceLessThenEqual(double dailyPrice);
	DataResult<List<CarPagedDto>> getAllPagedCar(int pageNo, int pageSize) throws CarNotFoundException;
	DataResult<List<CarSortedDto>> getAllSortedCar(int sort);
	DataResult<GetCarDto> getById(int id) throws CarNotFoundException;
	DataResult<List<CarListByBrandIdDto>> getAllByCar_BrandId(int brandId) throws BrandNotFoundException;
	DataResult<List<CarListByColorIdDto>> getAllByCar_ColorId(int colorId) throws ColorNotFoundException;

	void checkIsExistsByCarId(int carId) throws CarNotFoundException;
	void checkIsNotExistsByCar_BrandId(int brandId) throws BrandExistsInCarException;
	void checkIsNotExistsByCar_ColorId(int colorId) throws ColorExistsInCarException;

	double getDailyPriceByCarId(int carId);
}
