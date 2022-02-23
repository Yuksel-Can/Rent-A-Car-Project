package com.turkcell.rentACarProject.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarPagedDto {
	
	private int carId;
	private double dailyPrice;
	private int modelYear;
	private String colorName;
	private String brandName;
	
}
