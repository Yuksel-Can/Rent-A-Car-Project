package com.turkcell.rentACarProject.api.models.orderedAdditional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.business.requests.creditCardRequests.CreateCreditCardRequest;
import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.paymentRequests.CreatePaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalAddModel {

    @NotNull
    @Valid
    CreateCreditCardRequest createCreditCardRequest;

    @NotNull
    @Valid
    List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList;

    @NotNull
    @Min(1)
    private int rentalCarId;

}
