package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.lists.IndividualCustomerListDto;
import com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.gets.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;

import java.util.List;

public interface IndividualCustomerService {

    DataResult<List<IndividualCustomerListDto>> getAll();

    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;
    Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;
    Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException;

    DataResult<GetIndividualCustomerDto> getById(int individualCustomerId) throws BusinessException;
    IndividualCustomer getIndividualCustomerById(int individualCustomerId);

    boolean checkIfIndividualCustomerIdExists(int individualCustomerId) throws BusinessException;
}

