package com.turkcell.rentACarProject.core.postServices;

import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;

public interface PostService {

    void payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws BusinessException;

}
