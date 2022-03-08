package com.turkcell.rentACarProject.business.requests.delete;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBrandRequest {

	@NotNull
	@NotBlank
	@Min(1)
	private int brandId;
	
}
