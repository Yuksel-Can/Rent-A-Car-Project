package com.turkcell.rentACarProject.api.models.rentalCar;

import com.turkcell.rentACarProject.business.requests.create.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentForIndividualCustomer {

    @NotNull
    @Valid
    CreatePaymentRequest createPaymentRequest;

    @NotNull
    @Valid
    CreateRentalCarRequest createRentalCarRequest;

    @Valid
    List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList;

}
