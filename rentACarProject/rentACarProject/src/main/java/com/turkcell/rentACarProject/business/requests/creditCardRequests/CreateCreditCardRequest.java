package com.turkcell.rentACarProject.business.requests.creditCardRequests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
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
    @Pattern(regexp = "^[0-9]{16}", message = BusinessMessages.CreditCardMessages.CREDIT_CARD_CARD_NUMBER_NOT_VALID)
    private String cardNumber;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{5,50}", message = BusinessMessages.CreditCardMessages.CREDIT_CARD_OWNER_NOT_VALID)
    private String cardOwner;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{3}", message = BusinessMessages.CreditCardMessages.CREDIT_CARD_CVV_NOT_VALID)
    private String cardCvv;

    @NotNull
    @NotBlank
    @Size(min = 4, max = 5)
    private String cardExpirationDate;

    @JsonIgnore
    private int customerId;

}
