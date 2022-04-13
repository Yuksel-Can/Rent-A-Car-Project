package com.turkcell.rentACarProject.business.requests.additionalRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAdditionalRequest {

    @NotNull
    @Min(1)
    private int additionalId;

}
