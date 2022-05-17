package com.turkcell.rentACarProject.api.models.rentalCar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.CreateRentalCarRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentForCorporateRentAdd {

    @NotNull
    @Valid
    CreateCreditCardRequest createCreditCardRequest;

    @NotNull
    @Valid
    CreateRentalCarRequest createRentalCarRequest;

    @Valid
    List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList;

}
