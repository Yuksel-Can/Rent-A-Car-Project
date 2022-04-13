package com.turkcell.rentACarProject.business.dtos.carDtos.lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListByDailyPrice {
	
	private int carId;
	private double dailyPrice;
	private int modelYear;
	private int kilometer;
	private String colorName;
	private String brandName;
	
}
