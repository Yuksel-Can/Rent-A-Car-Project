package com.turkcell.rentACarProject.business.dtos.carCrashDtos.lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarCrashListByCarIdDto {

    private int carCrashId;
    private LocalDate crashDate;
    private double crashValuation;
    private int carId;

}
