package com.turkcell.rentACarProject.business.requests.update;



import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

	@NotNull
	@NotBlank
	@Min(0)
	private int carId;
	
	@NotNull
	@NotBlank
	@Min(1)
	private double dailyPrice;
	
	@NotNull
	@NotBlank
	@Min(1900)
	private int modelYear;
	
	private String description;
	
	@NotNull
	@NotBlank
	@Min(1)
	private int brandId;
	
	@NotNull
	@NotBlank
	@Min(1)
	private int colorId;
	
}
