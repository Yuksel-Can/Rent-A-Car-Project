package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class RentedKilometerNullException extends BusinessException {

    public RentedKilometerNullException(String message) {
        super(message);
    }
}
