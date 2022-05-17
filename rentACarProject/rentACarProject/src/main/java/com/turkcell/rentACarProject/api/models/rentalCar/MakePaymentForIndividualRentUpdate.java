package com.turkcell.rentACarProject.api.models.rentalCar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcell.rentACarProject.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.UpdateRentalCarRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentForIndividualRentUpdate {

    @NotNull
    @Valid
    CreateCreditCardRequest createCreditCardRequest;

    @NotNull
    @Valid
    UpdateRentalCarRequest updateRentalCarRequest;

}
