package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CarExistsInCarCrashException extends BusinessException {

    public CarExistsInCarCrashException(String message) {
        super(message);
    }
}
