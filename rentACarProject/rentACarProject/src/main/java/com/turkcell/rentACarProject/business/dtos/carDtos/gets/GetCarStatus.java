package com.turkcell.rentACarProject.business.dtos.carDtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarStatus {

    private int carId;
    private int modelYear;
    private int kilometer;
    private String description;

}
