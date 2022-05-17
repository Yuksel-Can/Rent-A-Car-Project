package com.turkcell.rentACarProject.core.utilities.exceptions;

import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.ErrorDataResult;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException){

        Map<String, String> validationErrors = new HashMap<String, String>();

        for(FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorDataResult<Object> error = new ErrorDataResult<>(validationErrors, "Validation.Error");
        return error;

    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleBusinessException(BusinessException businessException){

        ErrorDataResult<Object> error = new ErrorDataResult<>(businessException.getMessage(), businessException.getClass().getSimpleName()+".Error");

        return error;
    }

    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException httpMessageNotReadableException) {

        ErrorDataResult<Object> error = new ErrorDataResult<>(httpMessageNotReadableException.getMessage(), "JsonMessageFormat.Error");

        return error;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException){

        ErrorDataResult<Object> error = new ErrorDataResult<>(illegalArgumentException.getMessage(),"IllegalArgument.Error");

        return error;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException){

        ErrorDataResult<Object> error = new ErrorDataResult<>(dataIntegrityViolationException.getMessage(),"DataIntegrityViolation.Error");

        return error;
    }
/*
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleDataIntegrityViolationException(Exception exception){

        ErrorDataResult<Object> error = new ErrorDataResult<>(exception.getMessage(),"Exception.Error");

        return error;
    }*/
}
