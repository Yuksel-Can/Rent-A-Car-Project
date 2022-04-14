package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.carDtos.gets.GetCarDto;
import com.turkcell.rentACarProject.business.dtos.carDtos.lists.*;
import com.turkcell.rentACarProject.business.requests.carRequests.CreateCarRequest;
import com.turkcell.rentACarProject.business.requests.carRequests.DeleteCarRequest;
import com.turkcell.rentACarProject.business.requests.carRequests.UpdateCarRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

public interface CarService {

	DataResult<List<CarListDto>> getAll();
	
	Result add(CreateCarRequest createCarRequest) throws BusinessException;
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;

	void updateKilometer(int carId, int kilometer) throws BusinessException;

	DataResult<List<CarListByDailyPriceDto>> findByDailyPriceLessThenEqual(double dailyPrice);
	DataResult<List<CarPagedDto>> getAllPagedCar(int pageNo, int pageSize);
	DataResult<List<CarSortedDto>> getAllSortedCar(int sort);
	DataResult<GetCarDto> getById(int id) throws BusinessException;
	DataResult<List<CarListByBrandIdDto>> getAllByCar_BrandId(int brandId) throws BusinessException;
	DataResult<List<CarListByColorIdDto>> getAllByCar_ColorId(int colorId) throws BusinessException;

	void checkIsExistsByCarId(int carId) throws  BusinessException;
	void checkIsNotExistsByCar_BrandId(int brandId) throws BusinessException;
	void checkIsNotExistsByCar_ColorId(int colorId) throws BusinessException;

	double getDailyPriceByCarId(int carId);
}
