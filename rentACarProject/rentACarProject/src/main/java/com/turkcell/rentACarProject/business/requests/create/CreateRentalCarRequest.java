package com.turkcell.rentACarProject.business.requests.create;

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
    //@NotBlank
    private LocalDate startDate;


    @NotNull
    //@NotBlank
    private LocalDate finishDate;

    @NotNull
    @Min(1)
    private int carId;

}
