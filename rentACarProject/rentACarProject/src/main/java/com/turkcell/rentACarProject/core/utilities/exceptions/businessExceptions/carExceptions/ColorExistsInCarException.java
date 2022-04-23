package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class ColorExistsInCarException extends BusinessException {

    public ColorExistsInCarException(String message) {
        super(message);
    }
}
