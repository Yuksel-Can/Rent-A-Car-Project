package com.turkcell.rentACarProject.business.requests.carCrashRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarCrashRequest {

    @NotNull
    private LocalDate crashDate;

    @NotNull
    @Min(1)
    private double crashValuation;

    @NotNull
    @Min(1)
    private int carId;

}
