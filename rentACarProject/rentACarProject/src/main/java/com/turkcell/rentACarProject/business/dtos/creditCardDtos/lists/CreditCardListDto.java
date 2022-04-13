package com.turkcell.rentACarProject.business.dtos.creditCardDtos.lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardListDto {

    private int creditCardId;
    private String cardNumber;
    private String cardOwner;
    private String cardCvv;
    private String cardExpirationDate;
    private int customerId;

}
