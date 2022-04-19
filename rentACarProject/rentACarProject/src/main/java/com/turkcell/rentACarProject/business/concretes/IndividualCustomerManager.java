package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.gets.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.lists.IndividualCustomerListDto;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

    private final IndividualCustomerDao individualCustomerDao;
    private final UserService userService;
    private final RentalCarService rentalCarService;
    private final InvoiceService invoiceService;
    private final CreditCardService creditCardService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService, UserService userService,
                                     RentalCarService rentalCarService, InvoiceService invoiceService, @Lazy CreditCardService creditCardService) {
        this.individualCustomerDao = individualCustomerDao;
        this.userService = userService;
        this.rentalCarService = rentalCarService;
        this.modelMapperService = modelMapperService;
        this.invoiceService = invoiceService;
        this.creditCardService = creditCardService;
    }


    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {

        List<IndividualCustomer> individualCustomers = this.individualCustomerDao.findAll();

        List<IndividualCustomerListDto> result = individualCustomers.stream().map(individualCustomer -> this.modelMapperService.forDto()
                .map(individualCustomer,IndividualCustomerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(result, "IndividualCustomer listed");

    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {

        this.userService.checkIfUserEmailNotExists(createIndividualCustomerRequest.getEmail());
        checkIfNationalIdentityNotExists(createIndividualCustomerRequest.getNationalIdentity());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult("Individual Customer added");

    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {

        checkIfIndividualCustomerIdExists(updateIndividualCustomerRequest.getUserId());
        this.userService.checkIfUserEmailNotExistsForUpdate(updateIndividualCustomerRequest.getUserId(), updateIndividualCustomerRequest.getEmail());
        checkIfNationalIdentityNotExistsForUpdate(updateIndividualCustomerRequest.getUserId(), updateIndividualCustomerRequest.getNationalIdentity());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult("Individual Customer updated");

    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {

        checkIfIndividualCustomerIdExists(deleteIndividualCustomerRequest.getUserId());
        this.rentalCarService.checkIfRentalCar_CustomerIdNotExists(deleteIndividualCustomerRequest.getUserId());
        this.creditCardService.checkIfNotExistsByCustomer_CustomerId(deleteIndividualCustomerRequest.getUserId());
        this.invoiceService.checkIfNotExistsByCustomer_CustomerId(deleteIndividualCustomerRequest.getUserId());

        this.individualCustomerDao.deleteById(deleteIndividualCustomerRequest.getUserId());

        return new SuccessResult("Individual Customer deleted");

    }

    @Override
    public DataResult<GetIndividualCustomerDto> getById(int individualCustomerId) throws BusinessException {

        checkIfIndividualCustomerIdExists(individualCustomerId);

        IndividualCustomer individualCustomer = this.individualCustomerDao.getById(individualCustomerId);

        GetIndividualCustomerDto result = this.modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class);

        return new SuccessDataResult<>(result, "Individual Customer listed");

    }

    @Override
    public IndividualCustomer getIndividualCustomerById(int individualCustomerId){
        return this.individualCustomerDao.getById(individualCustomerId);
    }

    @Override
    public boolean checkIfIndividualCustomerIdExists(int individualCustomerId) throws BusinessException {
        if(!this.individualCustomerDao.existsByIndividualCustomerId(individualCustomerId)){
            throw new BusinessException("Individual Customer Id not exists, individualCustomerId: " + individualCustomerId);
        }
        return false;
    }

    void checkIfNationalIdentityNotExists(String nationalIdentity) throws BusinessException {
        if(this.individualCustomerDao.existsByNationalIdentity(nationalIdentity)){
            throw new BusinessException("National Identity already exists, nationalIdentity: " + nationalIdentity);
        }
    }

    void checkIfNationalIdentityNotExistsForUpdate(int individualCustomerId, String nationalIdentity) throws BusinessException {
        if(this.individualCustomerDao.existsByNationalIdentityAndIndividualCustomerIdIsNot(nationalIdentity, individualCustomerId)){
            throw new BusinessException("National Identity already exists, nationalIdentity: " + nationalIdentity);
        }
    }

}
