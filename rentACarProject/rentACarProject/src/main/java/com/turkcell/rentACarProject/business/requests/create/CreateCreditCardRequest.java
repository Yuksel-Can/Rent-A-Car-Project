package com.turkcell.rentACarProject.business.requests.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{16}", message = "not number") //todo:açıklama düzelt
    private String cardNumber;

    @NotNull
    @NotBlank
    private String cardOwner;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}", message = "not number") //todo:açıklama düzelt
    private String cardCvv;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 5)
    private String cardExpirationDate;


    @NotNull
    @Min(1)
    @JsonIgnore
    private int customerId;

}
