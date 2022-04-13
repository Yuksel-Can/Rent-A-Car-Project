package com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalRequest {

    @NotNull
    @Min(1)
    private short orderedAdditionalQuantity;

    @NotNull
    @Min(1)
    private int additionalId;

    @JsonIgnore
    private int rentalCarId;

}
