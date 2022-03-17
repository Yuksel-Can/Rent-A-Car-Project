package com.turkcell.rentACarProject.business.requests.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalCarWithOrderedAdditionalRequest {

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

    /*******FOR ORDERED***************/

    private List<CreateOrderedAdditionalForRentalCarRequest> orderedAdditionals;

}
