package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.additionalDtos.lists.AdditionalListDto;
import com.turkcell.rentACarProject.business.dtos.additionalDtos.gets.GetAdditionalDto;
import com.turkcell.rentACarProject.business.requests.additionalRequests.DeleteAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.additionalRequests.CreateAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.additionalRequests.UpdateAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface AdditionalService {

    DataResult<List<AdditionalListDto>> getAll();

    Result add(CreateAdditionalRequest createAdditionalRequest) throws BusinessException;
    Result update(UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException;
    Result delete(DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException;

    DataResult<GetAdditionalDto> getByAdditionalId(int additionalId) throws BusinessException;

    void checkIfExistsByAdditionalId(int additionalId) throws BusinessException;
    
}
