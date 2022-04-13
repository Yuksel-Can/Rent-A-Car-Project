package com.turkcell.rentACarProject.business.requests.rentalCarRequests;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NotNull
public class UpdateDeliveryDateRequest {

    @NotNull
    @Min(1)
    private int rentalCarId;

    @NotNull
    private LocalDate finishDate;

}
