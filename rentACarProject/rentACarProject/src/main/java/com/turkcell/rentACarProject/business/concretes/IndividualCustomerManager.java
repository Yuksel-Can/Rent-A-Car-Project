package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.dtos.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

    private IndividualCustomerDao individualCustomerDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {
        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {

        List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll();

        List<IndividualCustomerListDto> result = individualCustomers.stream().map(individualCustomer -> this.modelMapperService.forDto()
                .map(individualCustomer,IndividualCustomerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(result, "IndividualCustomer listed");

    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);
        return new SuccessResult("Individual Customer added");

    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult("Individual Customer updated");

    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) {

        this.individualCustomerDao.deleteById(deleteIndividualCustomerRequest.getUserId());

        return new SuccessResult("Individual Customer deleted");

    }

    @Override
    public DataResult<GetIndividualCustomerDto> getById(int individualCustomerId) {

        IndividualCustomer individualCustomer = this.individualCustomerDao.getById(individualCustomerId);

        GetIndividualCustomerDto result = this.modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class);

        return new SuccessDataResult<>(result, "Individual Customer listed");

    }
}
