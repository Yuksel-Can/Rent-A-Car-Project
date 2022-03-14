package com.turkcell.rentACarProject.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorListDto {
	
    private int colorId;
	private String colorName;
}
