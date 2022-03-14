package com.turkcell.rentACarProject.business.dtos;

import com.turkcell.rentACarProject.entities.concretes.Car;
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
    private int carId;
    private String brandName;
    private String colorName;

}
