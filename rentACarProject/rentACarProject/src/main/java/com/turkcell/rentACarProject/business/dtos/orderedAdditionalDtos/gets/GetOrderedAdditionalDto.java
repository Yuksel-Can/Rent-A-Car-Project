package com.turkcell.rentACarProject.business.dtos.orderedAdditionalDtos.gets;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderedAdditionalDto {

    private int orderedAdditionalId;
    private short orderedAdditionalQuantity;
    private int additionalId;
    private String additionalName;
    private int rentalCarId;

}
