package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class ReturnKilometerLessThanRentKilometerException extends BusinessException {

    public ReturnKilometerLessThanRentKilometerException(String message) {
        super(message);
    }
}
