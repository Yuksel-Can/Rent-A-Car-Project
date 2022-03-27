package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACarProject.business.dtos.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface IndividualCustomerService {

    DataResult<List<IndividualCustomerListDto>> getAll();

    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);
    Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest);
    Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest);

    DataResult<GetIndividualCustomerDto> getById(int individualCustomerId);
}

