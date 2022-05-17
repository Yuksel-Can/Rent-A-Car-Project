package com.turkcell.rentACarProject.business.adapters.posAdapters;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.PosServiceExceptions.MakePaymentFailedException;

public interface PosService {

    boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice) throws MakePaymentFailedException;

}
