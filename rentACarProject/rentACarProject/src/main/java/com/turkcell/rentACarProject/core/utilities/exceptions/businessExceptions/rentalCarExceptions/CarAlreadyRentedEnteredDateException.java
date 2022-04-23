package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CarAlreadyRentedEnteredDateException extends BusinessException {

    public CarAlreadyRentedEnteredDateException(String message) {
        super(message);
    }
}
