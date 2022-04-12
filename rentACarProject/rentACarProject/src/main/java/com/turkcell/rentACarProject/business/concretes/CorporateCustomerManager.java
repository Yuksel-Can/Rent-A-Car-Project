package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.abstracts.UserService;
import com.turkcell.rentACarProject.business.dtos.gets.corporateCustomer.GetCorporateCustomerDto;
import com.turkcell.rentACarProject.business.dtos.lists.corporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteCorporateCustomerRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateCorporateCustomerRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

    private final CorporateCustomerDao corporateCustomerDao;
    private final RentalCarService rentalCarService;
    private final UserService userService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService, RentalCarService rentalCarService, UserService userService) {
        this.corporateCustomerDao = corporateCustomerDao;
        this.rentalCarService = rentalCarService;
        this.userService = userService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {

        List<CorporateCustomer> corporateCustomers = this.corporateCustomerDao.findAll();

        List<CorporateCustomerListDto> result = corporateCustomers.stream().map(corporateCustomer -> this.modelMapperService.forDto()
                .map(corporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Corporate Customer listed");
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {

        this.userService.checkIfUserEmailNotExists(createCorporateCustomerRequest.getEmail());
        checkIfTaxNumberNotExists(createCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate Customer Added");

    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {

        checkIfCorporateCustomerIdExists(updateCorporateCustomerRequest.getUserId());
        this.userService.checkIfUserEmailNotExistsForUpdate(updateCorporateCustomerRequest.getUserId(), updateCorporateCustomerRequest.getEmail());
        checkIfTaxNumberNotExistsForUpdate(updateCorporateCustomerRequest.getUserId(), updateCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate Customer updated");

    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {

        checkIfCorporateCustomerIdExists(deleteCorporateCustomerRequest.getUserId());
        this.rentalCarService.checkIfRentalCar_CustomerIdNotExists(deleteCorporateCustomerRequest.getUserId());

        this.corporateCustomerDao.deleteById(deleteCorporateCustomerRequest.getUserId());

        return new SuccessResult("Corporate Customer deleted");

    }

    @Override
    public DataResult<GetCorporateCustomerDto> getById(int corporateCustomerId) throws BusinessException {

        checkIfCorporateCustomerIdExists(corporateCustomerId);

        CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(corporateCustomerId);

        GetCorporateCustomerDto result = this.modelMapperService.forDto().map(corporateCustomer, GetCorporateCustomerDto.class);

        return new SuccessDataResult<>(result, "Corporate Customer listed");

    }

    @Override
    public CorporateCustomer getCorporateCustomerById(int corporateCustomerId){
        return this.corporateCustomerDao.getById(corporateCustomerId);
    }
    @Override
    public void checkIfCorporateCustomerIdExists(int corporateCustomerId) throws BusinessException {
        if(!this.corporateCustomerDao.existsByCorporateCustomerId(corporateCustomerId)){
            throw new BusinessException("Corporate Customer Id not exists, corporateCustomerId: " + corporateCustomerId);
        }

    }

    void checkIfTaxNumberNotExists(String taxNumber) throws BusinessException {
        if(this.corporateCustomerDao.existsByTaxNumber(taxNumber)){
            throw new BusinessException("Tax Number already exists, taxNumber: " + taxNumber);
        }
    }

    void checkIfTaxNumberNotExistsForUpdate(int corporateCustomerId, String taxNumber) throws BusinessException {
        if(this.corporateCustomerDao.existsByTaxNumberAndCorporateCustomerIdIsNot(taxNumber, corporateCustomerId)){
            throw new BusinessException("Tax Number already exists, taxNumber: " + taxNumber);
        }
    }

}
