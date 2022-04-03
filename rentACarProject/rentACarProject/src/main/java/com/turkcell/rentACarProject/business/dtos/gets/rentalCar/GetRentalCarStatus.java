package com.turkcell.rentACarProject.business.dtos.gets.rentalCar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalCarStatus {

    private int rentalCarId;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int carId;
    private String brandName;
    private String colorName;
    private Integer rentedKilometer;
    private Integer deliveredKilometer;
    private int rentedCityId;
    private String rentedCityName;
    private int deliveredCityId;
    private String deliveredCityName;
    private String email;

}
