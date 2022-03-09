package com.turkcell.rentACarProject.business.requests.create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest {
	
	@NotNull					//  hiç atanmama durumu
	@NotBlank					//  	 "" olma durumu //int icin kullanilamaz
	@Size(min = 3, max = 30)
	private String brandName;

}
