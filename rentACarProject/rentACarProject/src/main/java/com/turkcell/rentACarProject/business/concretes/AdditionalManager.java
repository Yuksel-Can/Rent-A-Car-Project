package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.AdditionalService;
import com.turkcell.rentACarProject.business.abstracts.OrderedAdditionalService;
import com.turkcell.rentACarProject.business.dtos.AdditionalListDto;
import com.turkcell.rentACarProject.business.dtos.GetAdditionalDto;
import com.turkcell.rentACarProject.business.requests.create.CreateAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteAdditionalRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateAdditionalRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.AdditionalDao;
import com.turkcell.rentACarProject.entities.concretes.Additional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalManager implements AdditionalService {

    private AdditionalDao additionalDao;
    private OrderedAdditionalService orderedAdditionalService;
    private ModelMapperService modelMapperService;

    @Autowired
    public AdditionalManager(AdditionalDao additionalDao, ModelMapperService modelMapperService, @Lazy OrderedAdditionalService orderedAdditionalService) {
        this.additionalDao = additionalDao;
        this.orderedAdditionalService = orderedAdditionalService;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<AdditionalListDto>> getAll() {

        List<Additional> additionals = this.additionalDao.findAll();

        List<AdditionalListDto> result = additionals.stream().map(additional -> this.modelMapperService.forDto().map(additional, AdditionalListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Additional Services listed");

    }

    @Override
    public Result add(CreateAdditionalRequest createAdditionalRequest) throws BusinessException {

        checkIsNotExistsByAdditionalName(createAdditionalRequest.getAdditionalName());

        Additional additional = this.modelMapperService.forRequest().map(createAdditionalRequest, Additional.class);

        this.additionalDao.save(additional);

        return new SuccessResult("Additional Service added");

    }

    @Override
    public Result update(UpdateAdditionalRequest updateAdditionalRequest) throws BusinessException {

        checkIfExistsByAdditionalId(updateAdditionalRequest.getAdditionalId());
        checkIsNotExistsByAdditionalName(updateAdditionalRequest.getAdditionalName());

        Additional additional = this.modelMapperService.forRequest().map(updateAdditionalRequest, Additional.class);

        this.additionalDao.save(additional);

        return new SuccessResult("Additional Service updated, id: " + updateAdditionalRequest.getAdditionalId());

    }

    @Override
    public Result delete(DeleteAdditionalRequest deleteAdditionalRequest) throws BusinessException {

        checkIfExistsByAdditionalId(deleteAdditionalRequest.getAdditionalId());
        this.orderedAdditionalService.checkIsNotExistsByOrderedAdditional_AdditionalId(deleteAdditionalRequest.getAdditionalId());

        this.additionalDao.deleteById(deleteAdditionalRequest.getAdditionalId());

        return new SuccessResult("Additional Service deleted");

    }

    @Override
    public DataResult<GetAdditionalDto> getByAdditionalId(int additionalId) throws BusinessException {

        checkIfExistsByAdditionalId(additionalId);

        Additional addition = this.additionalDao.getById(additionalId);

        GetAdditionalDto result = this.modelMapperService.forDto().map(addition, GetAdditionalDto.class);

        return new SuccessDataResult<>(result, "Additional Service getted by id: " + additionalId);

    }

    @Override
    public void checkIfExistsByAdditionalId(int additionalId) throws BusinessException {
        if(!this.additionalDao.existsByAdditionalId(additionalId)){
            throw new BusinessException("Additional Service ıd not exists");
        }
    }

    private void checkIsNotExistsByAdditionalName(String additionalName) throws BusinessException {
        if(this.additionalDao.existsByAdditionalName(additionalName)){
            throw new BusinessException("Additional Service name already exists, name: " + additionalName);
        }
    }




}
