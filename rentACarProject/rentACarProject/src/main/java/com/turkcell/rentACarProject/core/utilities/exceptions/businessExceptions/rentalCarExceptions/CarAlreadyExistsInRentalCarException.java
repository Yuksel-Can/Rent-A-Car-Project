package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CarAlreadyExistsInRentalCarException extends BusinessException {

    public CarAlreadyExistsInRentalCarException(String message) {
        super(message);
    }
}
