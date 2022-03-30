package com.turkcell.rentACarProject.business.requests.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.entities.concretes.OrderedAdditional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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
    private List<CreateOrderedAdditionalForRentalCarRequest> orderedAdditionals;

}
//todo:bu class'ı sil