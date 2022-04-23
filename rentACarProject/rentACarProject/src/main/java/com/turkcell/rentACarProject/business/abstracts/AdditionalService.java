package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.additionalDtos.lists.AdditionalListDto;
import com.turkcell.rentACarProject.business.dtos.additionalDtos.gets.GetAdditionalDto;
import com.turkcell.rentACarProject.business.requests.additionalRequests.DeleteAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.additionalRequests.CreateAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.additionalRequests.UpdateAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.additionalExceptions.AdditionalAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.additionalExceptions.AdditionalNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.orderedAdditionalExceptions.AdditionalAlreadyExistsInOrderedAdditionalException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface AdditionalService {

    DataResult<List<AdditionalListDto>> getAll();

    Result add(CreateAdditionalRequest createAdditionalRequest) throws AdditionalAlreadyExistsException;
    Result update(UpdateAdditionalRequest updateAdditionalRequest) throws AdditionalAlreadyExistsException, AdditionalNotFoundException;
    Result delete(DeleteAdditionalRequest deleteAdditionalRequest) throws AdditionalNotFoundException, AdditionalAlreadyExistsInOrderedAdditionalException;

    DataResult<GetAdditionalDto> getByAdditionalId(int additionalId) throws AdditionalNotFoundException;

    void checkIfExistsByAdditionalId(int additionalId) throws AdditionalNotFoundException;
    
}
