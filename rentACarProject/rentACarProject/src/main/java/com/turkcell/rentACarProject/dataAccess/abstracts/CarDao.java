package com.turkcell.rentACarProject.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACarProject.entities.concretes.Car;

@Repository
public interface CarDao extends JpaRepository<Car, Integer>{
	
	boolean existsByCarId(int carId); 
	boolean existsByBrand_BrandId(int brandId);
	boolean existsByColor_ColorId(int colorId);

	List<Car> getAllByBrand_BrandId(int brandId);
	List<Car> getAllByColor_ColorId(int colorId);
	List<Car> findByDailyPriceLessThanEqual(double dailyPrice);

}
