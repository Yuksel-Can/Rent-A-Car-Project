package com.turkcell.rentACarProject.business.requests.delete;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteColorRequest {

	@NotNull
	@Min(1)
	private int colorId;
	
}
