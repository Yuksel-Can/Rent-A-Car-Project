package com.turkcell.rentACarProject.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceListDto {

    private int maintenanceId;
    private String description;
    private LocalDate returnDate;
    private int carId;
    private double dailyPrice;
    private String brandName;
    private String colorName;

}
