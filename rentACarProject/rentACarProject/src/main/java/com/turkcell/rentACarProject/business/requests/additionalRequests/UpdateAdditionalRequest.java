package com.turkcell.rentACarProject.business.requests.additionalRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalRequest {

    @NotNull
    @Min(1)
    private int additionalId;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 300)
    private String additionalName;

    @NotNull
    @Min(1)
    private double additionalDailyPrice;

    @NotNull
    @Min(1)
    private short maxUnitsPerRental;

}
