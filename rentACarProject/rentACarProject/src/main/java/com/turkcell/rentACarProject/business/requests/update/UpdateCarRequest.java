package com.turkcell.rentACarProject.business.requests.update;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

	@NotNull
	@Min(1)
	private int carId;
	
	@NotNull
	@Min(1)
	private double dailyPrice;
	
	@NotNull
	@Min(1900)
	private int modelYear;

	@NotNull
	@Min(0)
	private int kilometer;
	
	private String description;
	
	@NotNull
	@Min(1)
	private int brandId;
	
	@NotNull
	@Min(1)
	private int colorId;
	
}
