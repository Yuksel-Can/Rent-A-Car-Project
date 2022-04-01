package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACarProject.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.abstracts.RentalCarService;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetCorporateCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetIndividualCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.lists.invoice.InvoiceListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.generate.GenerateRandomCode;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACarProject.entities.concretes.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {

    private final InvoiceDao invoiceDao;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;
    private final RentalCarService rentalCarService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public InvoiceManager(InvoiceDao invoiceDao, IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService
            , RentalCarService rentalCarService, ModelMapperService modelMapperService) {
        this.invoiceDao = invoiceDao;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.rentalCarService = rentalCarService;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {

        List<Invoice> invoices = this.invoiceDao.findAll();

        List<InvoiceListDto> result = invoices.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());
        for(int i=0; i<invoices.size();i++){
            result.get(i).setRentalCarId(invoices.get(i).getRentalCar().getRentalCarId());
            result.get(i).setCustomerId(invoices.get(i).getRentalCar().getCustomer().getCustomerId());
        }

        return new SuccessDataResult<>(result, "Invoices listed");
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

        this.rentalCarService.checkIsExistsByRentalCarId(createInvoiceRequest.getRentalCarId());
        createInvoiceRequest.setInvoiceNo(GenerateRandomCode.generate());

        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
        invoice.setInvoiceId(0);

        this.invoiceDao.save(invoice);

        return new SuccessResult("Invoice added");
    }

    @Override
    public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
        //todo:burada update olacakmı
        checkIfInvoiceIdExists(updateInvoiceRequest.getInvoiceId());

        Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);

        //this.invoiceDao.save(invoice);

        return new SuccessResult("Invoice updated, invoiceId: " + updateInvoiceRequest.getInvoiceId());
    }

    @Override
    public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {

        checkIfInvoiceIdExists(deleteInvoiceRequest.getInvoiceId());

        this.invoiceDao.deleteById(deleteInvoiceRequest.getInvoiceId());

        return new SuccessResult("Invoice deleted");
    }

    @Override
    public DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceId(int invoiceId) throws BusinessException {

        checkIfInvoiceIdExists(invoiceId);
        Invoice invoice = this.invoiceDao.getById(invoiceId);
        this.individualCustomerService.checkIfIndividualCustomerIdExists(invoice.getRentalCar().getCustomer().getCustomerId());

        GetIndividualCustomerInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetIndividualCustomerInvoiceDto.class);
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Individual Customer Invoice getted by individual invoice id, invoiceId: " + invoiceId);
    }

    @Override
    public DataResult<GetCorporateCustomerInvoiceDto> getCorporateCustomerInvoiceByInvoiceId(int invoiceId) throws BusinessException {

        checkIfInvoiceIdExists(invoiceId);

        Invoice invoice = this.invoiceDao.getById(invoiceId);

        this.corporateCustomerService.checkIfCorporateCustomerIdExists(invoice.getRentalCar().getCustomer().getCustomerId());

        GetCorporateCustomerInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetCorporateCustomerInvoiceDto.class);
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Corporate Customer Invoice getted by corporate invoice id, invoiceId " + invoiceId);
    }

    @Override
    public DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceNo(String invoiceNo) throws BusinessException {

        checkIfInvoiceNoExists(invoiceNo);

        Invoice invoice = this.invoiceDao.getInvoiceByInvoiceNo(invoiceNo);

        this.individualCustomerService.checkIfIndividualCustomerIdExists(invoice.getRentalCar().getCustomer().getCustomerId());

        GetIndividualCustomerInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetIndividualCustomerInvoiceDto.class);
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Individual Customer Invoice getted by individual invoice no, invoiceNo: " + invoiceNo);
    }

    @Override
    public DataResult<GetCorporateCustomerInvoiceDto> getCorporateCustomerInvoiceByInvoiceNo(String invoiceNo) throws BusinessException {

        checkIfInvoiceNoExists(invoiceNo);

        Invoice invoice = this.invoiceDao.getInvoiceByInvoiceNo(invoiceNo);

        this.corporateCustomerService.checkIfCorporateCustomerIdExists(invoice.getRentalCar().getCustomer().getCustomerId());

        GetCorporateCustomerInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetCorporateCustomerInvoiceDto.class);
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Corporate Customer Invoice getted by corporate invoice no, invoiceNo: " + invoiceNo);

    }

    private void manuelFieldSetter(Invoice invoice, GetInvoiceDto result) {

        result.setRentalCarId(invoice.getRentalCar().getRentalCarId());
        result.setCustomerId(invoice.getRentalCar().getCustomer().getCustomerId());
    }

    private void checkIfInvoiceIdExists(int invoiceId) throws BusinessException {
        if(!this.invoiceDao.existsByInvoiceId(invoiceId)){
            throw new BusinessException("Invoice id not exists, invoiceId: " + invoiceId);
        }

    }

    private void checkIfInvoiceNoExists(String invoiceNo) throws BusinessException {
        if(!this.invoiceDao.existsByInvoiceNo(invoiceNo)){
            throw new BusinessException("Invoice no not exists, invoiceNo: " + invoiceNo);
        }
    }
}
