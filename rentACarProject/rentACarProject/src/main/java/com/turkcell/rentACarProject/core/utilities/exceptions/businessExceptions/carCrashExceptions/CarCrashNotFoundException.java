package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CarCrashNotFoundException extends BusinessException {

    public CarCrashNotFoundException(String message) {
        super(message);
    }
}
