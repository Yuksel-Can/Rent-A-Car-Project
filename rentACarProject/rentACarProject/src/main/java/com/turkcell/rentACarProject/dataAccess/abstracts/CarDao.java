package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.turkcell.rentACarProject.entities.concretes.Car;

public interface CarDao extends JpaRepository<Car, Integer>{
	
	boolean existsByCarId(int carId); 
	boolean existsByBrand_BrandId(int brandId);
	boolean existsByColor_ColorId(int colorId);
	
	List<Car> findByDailyPriceLessThanEqual(double dailyPrice);	//gunluk fiyata göre kucuk esit
	
}
