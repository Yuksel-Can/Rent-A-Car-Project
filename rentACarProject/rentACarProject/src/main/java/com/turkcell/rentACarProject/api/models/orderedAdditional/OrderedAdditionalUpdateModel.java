package com.turkcell.rentACarProject.api.models.orderedAdditional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcell.rentACarProject.business.requests.paymentRequests.CreatePaymentRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.UpdateOrderedAdditionalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalUpdateModel {

    @NotNull
    @Valid
    CreateCreditCardRequest createCreditCardRequest;

    @NotNull
    @Valid
    UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest;

}
