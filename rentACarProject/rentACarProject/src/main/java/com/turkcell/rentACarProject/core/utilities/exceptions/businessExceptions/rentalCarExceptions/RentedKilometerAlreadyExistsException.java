package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class RentedKilometerAlreadyExistsException extends BusinessException {

    public RentedKilometerAlreadyExistsException(String message) {
        super(message);
    }
}
