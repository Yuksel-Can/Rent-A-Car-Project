package com.turkcell.rentACarProject.api.models.rentalCar;

import com.turkcell.rentACarProject.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcell.rentACarProject.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentACarProject.business.requests.rentalCarRequests.UpdateDeliveryDateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentForRentDeliveryDateUpdate {

    @NotNull
    @Valid
    CreateCreditCardRequest createCreditCardRequest;

    @NotNull
    @Valid
    UpdateDeliveryDateRequest updateDeliveryDateRequest;
    
}
