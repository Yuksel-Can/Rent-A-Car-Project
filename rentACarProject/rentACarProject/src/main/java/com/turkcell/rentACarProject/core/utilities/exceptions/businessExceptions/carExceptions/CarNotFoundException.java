package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CarNotFoundException extends BusinessException {

    public CarNotFoundException(String message) {
        super(message);
    }
}
