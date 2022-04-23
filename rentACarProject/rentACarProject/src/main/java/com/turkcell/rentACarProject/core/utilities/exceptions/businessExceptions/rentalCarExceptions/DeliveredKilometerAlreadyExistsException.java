package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class DeliveredKilometerAlreadyExistsException extends BusinessException {

    public DeliveredKilometerAlreadyExistsException(String message) {
        super(message);
    }
}
