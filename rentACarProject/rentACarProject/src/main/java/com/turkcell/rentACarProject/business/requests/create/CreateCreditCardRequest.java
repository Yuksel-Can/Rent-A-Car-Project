package com.turkcell.rentACarProject.business.requests.create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {

    @NotNull
    @NotBlank
    @Size(min = 16, max = 16)
    private String cardNumber;

    @NotNull
    @NotBlank
    private String cardOwner;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 3)
    private String cardCvv;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 5)
    private String cardExpirationDate;

    @NotNull
    @Min(1)
    private int customerId;

}
