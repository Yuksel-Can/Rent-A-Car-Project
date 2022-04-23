package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CityNotFoundException extends BusinessException {

    public CityNotFoundException(String message) {
        super(message);
    }
}
