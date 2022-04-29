package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.cityExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CityAlreadyExistsException extends BusinessException {

    public CityAlreadyExistsException(String message) {
        super(message);
    }
}
