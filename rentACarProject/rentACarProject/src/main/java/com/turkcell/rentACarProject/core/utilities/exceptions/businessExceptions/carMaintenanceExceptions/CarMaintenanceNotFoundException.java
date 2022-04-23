package com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.carMaintenanceExceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;

public class CarMaintenanceNotFoundException extends BusinessException {

    public CarMaintenanceNotFoundException(String message) {
        super(message);
    }
}
