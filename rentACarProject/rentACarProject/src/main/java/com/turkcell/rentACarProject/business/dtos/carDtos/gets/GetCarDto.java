package com.turkcell.rentACarProject.business.dtos.carDtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarDto {

	private int carId;
	private double dailyPrice;
	private int modelYear;
	private int kilometer;
	private String description;
	private String colorName;
	private String brandName;

}
