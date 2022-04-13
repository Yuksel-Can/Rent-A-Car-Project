package com.turkcell.rentACarProject.business.requests.colorRequests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateColorRequest {
	
	@NotNull
	@NotBlank
	@Size(min = 3, max = 30)
	private String colorName;

}
