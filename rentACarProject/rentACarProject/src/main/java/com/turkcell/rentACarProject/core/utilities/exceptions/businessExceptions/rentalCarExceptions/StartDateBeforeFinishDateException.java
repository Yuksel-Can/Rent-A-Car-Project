package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class StartDateBeforeFinishDateException extends BusinessException {

    public StartDateBeforeFinishDateException(String message) {
        super(message);
    }
}
