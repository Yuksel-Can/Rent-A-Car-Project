package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class RentalCarNotFoundException extends BusinessException {

    public RentalCarNotFoundException(String message) {
        super(message);
    }
}
