package com.turkcell.rentACarProject.api.models.orderedAdditional;

import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateOrderedAdditionalRequest;
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
public class OrderedAdditionalUpdateModel {

    @Valid
    @NotNull
    CreatePaymentRequest createPaymentRequest;

    @NotNull
    @Valid
    UpdateOrderedAdditionalRequest updateOrderedAdditionalRequest;

}
