package com.turkcell.rentACarProject.business.requests.rentalCarRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveTheCarRequest {

    @NotNull
    @Min(1)
    private int rentalCarId;

    @NotNull
    @Min(1)
    private int carId;

    @NotNull
    @Min(1)
    private int deliveredKilometer;

}
