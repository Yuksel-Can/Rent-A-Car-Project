package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalCarDao extends JpaRepository<RentalCar, Integer> {

    boolean existsByRentalCarId(int rentalCarId);
    boolean existsByCar_CarId(int carId);

    List<RentalCar> getAllByCar_CarId(int carId);

}
