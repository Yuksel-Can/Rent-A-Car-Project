package com.turkcell.rentACarProject.business.requests.rentalCarRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalCarRequest {

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate finishDate;

    @NotNull
    @Min(1)
    private int carId;

    @NotNull
    @Min(1)
    private int rentedCityCityId;

    @NotNull
    @Min(1)
    private int deliveredCityId;

    @NotNull
    @Min(1)
    private int customerId;

}