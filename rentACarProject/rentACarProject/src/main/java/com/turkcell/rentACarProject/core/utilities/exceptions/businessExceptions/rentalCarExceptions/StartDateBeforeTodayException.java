package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class StartDateBeforeTodayException extends BusinessException {

    public StartDateBeforeTodayException(String message) {
        super(message);
    }
}
