package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.constants.messaaages.BusinessMessages;
import com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.gets.GetIndividualCustomerDto;
import com.turkcell.rentACarProject.business.dtos.individualCustomerDtos.lists.IndividualCustomerListDto;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentACarProject.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.creditCardExceptions.CreditCardAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.individualCustomerExceptions.IndividualCustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.individualCustomerExceptions.NationalIdentityAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions.CustomerNotFoundInInvoiceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.CustomerAlreadyExistsInRentalCarException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserAlreadyExistsException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.userExceptions.UserEmailNotValidException;
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

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws NationalIdentityAlreadyExistsException, UserAlreadyExistsException {

        this.userService.checkIfUserEmailNotExists(createIndividualCustomerRequest.getEmail());
        checkIfNationalIdentityNotExists(createIndividualCustomerRequest.getNationalIdentity());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws NationalIdentityAlreadyExistsException, IndividualCustomerNotFoundException, UserEmailNotValidException {

        checkIfIndividualCustomerIdExists(updateIndividualCustomerRequest.getUserId());
        this.userService.checkIfUserEmailNotExistsForUpdate(updateIndividualCustomerRequest.getUserId(), updateIndividualCustomerRequest.getEmail());
        checkIfNationalIdentityNotExistsForUpdate(updateIndividualCustomerRequest.getUserId(), updateIndividualCustomerRequest.getNationalIdentity());

        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);

        this.individualCustomerDao.save(individualCustomer);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY + updateIndividualCustomerRequest.getUserId());

    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws CreditCardAlreadyExistsException, IndividualCustomerNotFoundException, CustomerNotFoundInInvoiceException, CustomerAlreadyExistsInRentalCarException {

        checkIfIndividualCustomerIdExists(deleteIndividualCustomerRequest.getUserId());
        this.rentalCarService.checkIfRentalCar_CustomerIdNotExists(deleteIndividualCustomerRequest.getUserId());
        this.creditCardService.checkIfNotExistsByCustomer_CustomerId(deleteIndividualCustomerRequest.getUserId());
        this.invoiceService.checkIfNotExistsByCustomer_CustomerId(deleteIndividualCustomerRequest.getUserId());

        this.individualCustomerDao.deleteById(deleteIndividualCustomerRequest.getUserId());

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY + deleteIndividualCustomerRequest.getUserId());
    }

    @Override
    public DataResult<GetIndividualCustomerDto> getById(int individualCustomerId) throws IndividualCustomerNotFoundException {

        checkIfIndividualCustomerIdExists(individualCustomerId);

        IndividualCustomer individualCustomer = this.individualCustomerDao.getById(individualCustomerId);

        GetIndividualCustomerDto result = this.modelMapperService.forDto().map(individualCustomer, GetIndividualCustomerDto.class);

        return new SuccessDataResult<>(result, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY + individualCustomerId);
    }

    @Override
    public IndividualCustomer getIndividualCustomerById(int individualCustomerId){
        return this.individualCustomerDao.getById(individualCustomerId);
    }

    @Override
    public void checkIfIndividualCustomerIdExists(int individualCustomerId) throws IndividualCustomerNotFoundException {
        if(!this.individualCustomerDao.existsByIndividualCustomerId(individualCustomerId)){
            throw new IndividualCustomerNotFoundException(BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_ID_NOT_FOUND + individualCustomerId);
        }
    }

    void checkIfNationalIdentityNotExists(String nationalIdentity) throws NationalIdentityAlreadyExistsException {
        if(this.individualCustomerDao.existsByNationalIdentity(nationalIdentity)){
            throw new NationalIdentityAlreadyExistsException(BusinessMessages.IndividualCustomerMessages.NATIONAL_IDENTITY_ALREADY_EXISTS + nationalIdentity);
        }
    }

    void checkIfNationalIdentityNotExistsForUpdate(int individualCustomerId, String nationalIdentity) throws NationalIdentityAlreadyExistsException {
        if(this.individualCustomerDao.existsByNationalIdentityAndIndividualCustomerIdIsNot(nationalIdentity, individualCustomerId)){
            throw new NationalIdentityAlreadyExistsException(BusinessMessages.IndividualCustomerMessages.NATIONAL_IDENTITY_ALREADY_EXISTS + nationalIdentity);
        }
    }

}
