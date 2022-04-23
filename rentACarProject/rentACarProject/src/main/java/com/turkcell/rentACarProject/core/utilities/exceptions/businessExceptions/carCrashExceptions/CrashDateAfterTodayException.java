package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carCrashExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CrashDateAfterTodayException extends BusinessException {

    public CrashDateAfterTodayException(String message) {
        super(message);
    }
}
