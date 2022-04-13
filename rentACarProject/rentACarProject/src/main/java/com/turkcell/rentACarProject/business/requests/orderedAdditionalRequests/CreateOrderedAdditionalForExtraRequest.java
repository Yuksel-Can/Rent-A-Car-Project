package com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalForExtraRequest {

    @NotNull
    @Min(1)
    private short orderedAdditionalQuantity;

    @NotNull
    @Min(1)
    private int additionalId;

    @NotNull
    @Min(1)
    private int rentalCarId;
}
//todo:kullanan yoksa bunu sil