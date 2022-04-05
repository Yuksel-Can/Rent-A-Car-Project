package com.turkcell.rentACarProject.api.models.orderedAdditional;

import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.create.CreatePaymentRequest;
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

    @Valid
    @NotNull
    CreatePaymentRequest createPaymentRequest;

    @NotNull
    @Valid
    List<CreateOrderedAdditionalRequest> createOrderedAdditionalRequestList;

    @NotNull
    @Min(1)
    private int rentalCarId;

}
