package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CustomerAlreadyExistsInRentalCarException extends BusinessException {

    public CustomerAlreadyExistsInRentalCarException(String message) {
        super(message);
    }
}
