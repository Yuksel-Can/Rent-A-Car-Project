package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class UserEmailNotValidException extends BusinessException {

    public UserEmailNotValidException(String message) {
        super(message);
    }
}
