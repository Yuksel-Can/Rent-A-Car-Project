package com.turkcell.rentACarProject.business.requests.update;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarRequest {

    @NotNull
    @Min(1)
    private int rentalCarId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate finishDate;

    @JsonIgnore
    private double rentalCarTotalPrice;

    @NotNull
    @Min(1)
    private int carId;

    @NotNull
    @Min(1)
    private int rentedCityId;

    @NotNull
    @Min(1)
    private int deliveredCityId;

    @NotNull
    @Min(1)
    private int customerId;

}