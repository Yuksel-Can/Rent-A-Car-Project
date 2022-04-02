package com.turkcell.rentACarProject.api.models.rentalCar;


import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalForRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCarAddModel {

    @NotNull
    @Valid
    private CreateRentalCarRequest createRentalCarRequest;

    @Valid
    @Nullable
    private List<CreateOrderedAdditionalForRentalCarRequest> orderedAdditionals;

}
