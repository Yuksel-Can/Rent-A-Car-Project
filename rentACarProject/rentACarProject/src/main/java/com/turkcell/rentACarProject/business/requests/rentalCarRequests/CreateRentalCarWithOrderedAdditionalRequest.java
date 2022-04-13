package com.turkcell.rentACarProject.business.requests.rentalCarRequests;

import com.turkcell.rentACarProject.business.requests.orderedAdditionalRequests.CreateOrderedAdditionalForExtraRequest;
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
public class CreateRentalCarWithOrderedAdditionalRequest {

    @NotNull
    @Valid
    private CreateRentalCarRequest createRentalCarRequest;

    /*******FOR ORDERED***************/

    @Valid
    private List<CreateOrderedAdditionalForExtraRequest> orderedAdditionals;

}
//todo:bu class'ı sil