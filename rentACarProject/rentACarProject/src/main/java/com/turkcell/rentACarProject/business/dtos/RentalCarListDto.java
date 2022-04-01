package com.turkcell.rentACarProject.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCarListDto {

    private int rentalCarId;
    private LocalDate startDate;
    private LocalDate finishDate;
//    private double rentalCarTotalPrice;
    private int carId;
    private String brandName;
    private String colorName;
    private int rentedCityId;
    private String rentedCityName;
    private int deliveredCityId;
    private String deliveredCityName;
    private String email;
}
