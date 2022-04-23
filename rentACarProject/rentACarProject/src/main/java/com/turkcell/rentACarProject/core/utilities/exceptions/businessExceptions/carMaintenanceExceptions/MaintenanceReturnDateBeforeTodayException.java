package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class MaintenanceReturnDateBeforeTodayException extends BusinessException {

    public MaintenanceReturnDateBeforeTodayException(String message) {
        super(message);
    }
}
