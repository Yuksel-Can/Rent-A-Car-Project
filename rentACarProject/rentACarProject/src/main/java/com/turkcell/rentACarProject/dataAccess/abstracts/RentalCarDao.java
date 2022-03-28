package com.turkcell.rentACarProject.dataAccess.abstracts;

import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalCarDao extends JpaRepository<RentalCar, Integer> {

    boolean existsByRentalCarId(int rentalCarId);
    boolean existsByCar_CarId(int carId);
    boolean existsByRentedCity_CityId(int cityId);
    boolean existsByDeliveredCity_CityId(int cityId);
    boolean existsByCustomer_CustomerId(int customerId);

    List<RentalCar> getAllByCar_CarId(int carId);
    List<RentalCar> getAllByRentedCity_CityId(int rentedCityId);
    List<RentalCar> getAllByDeliveredCity_CityId(int deliveredCityId);
    List<RentalCar> getAllByCustomer_CustomerId(int customerId);

}
