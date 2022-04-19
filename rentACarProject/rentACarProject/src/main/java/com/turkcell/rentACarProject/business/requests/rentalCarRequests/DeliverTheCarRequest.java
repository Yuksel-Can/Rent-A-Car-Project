package com.turkcell.rentACarProject.business.requests.rentalCarRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliverTheCarRequest {

    @NotNull
    @Min(1)
    private int rentalCarId;

    @NotNull
    @Min(1)
    private int carId;

}
