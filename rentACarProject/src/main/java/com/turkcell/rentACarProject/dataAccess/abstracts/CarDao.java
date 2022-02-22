package com.turkcell.rentACarProject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACarProject.entities.concretes.Car;

public interface CarDao extends JpaRepository<Car, Integer>{
	boolean existsById(int carId); 

}
