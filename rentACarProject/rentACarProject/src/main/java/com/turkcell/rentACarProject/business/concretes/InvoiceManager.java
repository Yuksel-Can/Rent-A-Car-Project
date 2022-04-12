package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.*;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetCorporateCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetIndividualCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.lists.invoice.InvoiceListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.generate.GenerateRandomCode;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACarProject.entities.concretes.CorporateCustomer;
import com.turkcell.rentACarProject.entities.concretes.IndividualCustomer;
import com.turkcell.rentACarProject.entities.concretes.Invoice;
import com.turkcell.rentACarProject.entities.concretes.RentalCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {

    private final InvoiceDao invoiceDao;
    private final CarService carService;
    private final CustomerService customerService;
    private final IndividualCustomerService individualCustomerService;
    private final CorporateCustomerService corporateCustomerService;
    private final RentalCarService rentalCarService;
    private final OrderedAdditionalService orderedAdditionalService;
    private final PaymentService paymentService;
    private final ModelMapperService modelMapperService;

    @Autowired
    public InvoiceManager(InvoiceDao invoiceDao, CarService carService, CustomerService customerService,
                          IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService,
                          RentalCarService rentalCarService, ModelMapperService modelMapperService, @Lazy OrderedAdditionalService orderedAdditionalService, PaymentService paymentService) {
        this.invoiceDao = invoiceDao;
        this.carService = carService;
        this.customerService = customerService;
        this.individualCustomerService = individualCustomerService;
        this.corporateCustomerService = corporateCustomerService;
        this.rentalCarService = rentalCarService;
        this.orderedAdditionalService = orderedAdditionalService;
        this.modelMapperService = modelMapperService;
        this.paymentService = paymentService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {

        List<Invoice> invoices = this.invoiceDao.findAll();

        List<InvoiceListDto> result = invoices.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
        manuelFieldSetter(invoices, result);

        return new SuccessDataResult<>(result, "Invoices listed");
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {

        this.rentalCarService.checkIsExistsByRentalCarId(createInvoiceRequest.getRentalCarId());
        createInvoiceRequest.setInvoiceNo(generateCode());

        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
        invoice.setInvoiceId(0);
        invoice.setCustomer(this.customerService.getCustomerById(createInvoiceRequest.getCustomerId()));

        this.invoiceDao.save(invoice);


        return new SuccessResult("Invoice added");
    }

    @Override
    public DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceId(int invoiceId) throws BusinessException {

        checkIfInvoiceIdExists(invoiceId);
        Invoice invoice = this.invoiceDao.getById(invoiceId);
        this.individualCustomerService.checkIfIndividualCustomerIdExists(invoice.getRentalCar().getCustomer().getCustomerId());

        GetIndividualCustomerInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetIndividualCustomerInvoiceDto.class);

        IndividualCustomer individualCustomer = this.individualCustomerService.getIndividualCustomerById(invoice.getCustomer().getCustomerId());
        result.setFirstName(individualCustomer.getFirstName());
        result.setLastName(individualCustomer.getLastName());
        result.setNationalIdentity(individualCustomer.getNationalIdentity());
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Individual Customer Invoice getted by individual invoice id, invoiceId: " + invoiceId);
    }

    @Override
    public DataResult<GetCorporateCustomerInvoiceDto> getCorporateCustomerInvoiceByInvoiceId(int invoiceId) throws BusinessException {

        checkIfInvoiceIdExists(invoiceId);

        Invoice invoice = this.invoiceDao.getById(invoiceId);
        this.corporateCustomerService.checkIfCorporateCustomerIdExists(invoice.getRentalCar().getCustomer().getCustomerId());

        GetCorporateCustomerInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetCorporateCustomerInvoiceDto.class);
        CorporateCustomer corporateCustomer = this.corporateCustomerService.getCorporateCustomerById(invoice.getCustomer().getCustomerId());
        result.setCompanyName(corporateCustomer.getCompanyName());
        result.setTaxNumber(corporateCustomer.getTaxNumber());
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Corporate Customer Invoice getted by corporate invoice id, invoiceId " + invoiceId);
    }

    @Override
    public DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceNo(String invoiceNo) throws BusinessException {

        checkIfInvoiceNoExists(invoiceNo);

        Invoice invoice = this.invoiceDao.getInvoiceByInvoiceNo(invoiceNo);
        this.individualCustomerService.checkIfIndividualCustomerIdExists(invoice.getRentalCar().getCustomer().getCustomerId());

        GetIndividualCustomerInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetIndividualCustomerInvoiceDto.class);
        IndividualCustomer individualCustomer = this.individualCustomerService.getIndividualCustomerById(invoice.getCustomer().getCustomerId());
        result.setFirstName(individualCustomer.getFirstName());
        result.setLastName(individualCustomer.getLastName());
        result.setNationalIdentity(individualCustomer.getNationalIdentity());
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Individual Customer Invoice getted by individual invoice no, invoiceNo: " + invoiceNo);
    }

    @Override
    public DataResult<GetCorporateCustomerInvoiceDto> getCorporateCustomerInvoiceByInvoiceNo(String invoiceNo) throws BusinessException {

        checkIfInvoiceNoExists(invoiceNo);

        Invoice invoice = this.invoiceDao.getInvoiceByInvoiceNo(invoiceNo);

        this.corporateCustomerService.checkIfCorporateCustomerIdExists(invoice.getRentalCar().getCustomer().getCustomerId());

        GetCorporateCustomerInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetCorporateCustomerInvoiceDto.class);
        CorporateCustomer corporateCustomer = this.corporateCustomerService.getCorporateCustomerById(invoice.getCustomer().getCustomerId());
        result.setCompanyName(corporateCustomer.getCompanyName());
        result.setTaxNumber(corporateCustomer.getTaxNumber());
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Corporate Customer Invoice getted by corporate invoice no, invoiceNo: " + invoiceNo);

    }

    @Override
    public DataResult<GetInvoiceDto> getInvoiceByPayment_PaymentId(int paymentId) throws BusinessException {

        checkIfExistsByPaymentId(paymentId);

        Invoice invoice = this.invoiceDao.getInvoiceByPayment_PaymentId(paymentId);

        GetInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);
        manuelFieldSetter(invoice, result);

        return new SuccessDataResult<>(result, "Invoice listed by paymentId, paymentId: " + paymentId);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAllByRentalCar_RentalCarId(int rentalCarId) throws BusinessException {

        this.rentalCarService.checkIsExistsByRentalCarId(rentalCarId);

        List<Invoice> invoiceList = this.invoiceDao.getAllByRentalCar_RentalCarId(rentalCarId);

        List<InvoiceListDto> result = invoiceList.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
        manuelFieldSetter(invoiceList, result);

        return new SuccessDataResult<>(result, "Invoice listed by rentalCarId, rentalCarId: " + rentalCarId);
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAllByCustomer_CustomerId(int customerId) throws BusinessException {

        this.customerService.checkIfCustomerIdExists(customerId);

        List<Invoice> invoiceList = this.invoiceDao.getAllByCustomer_CustomerId(customerId);

        List<InvoiceListDto> result = invoiceList.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
        manuelFieldSetter(invoiceList, result);

        return new SuccessDataResult<>(result, "Invoice listed by customerId, customerId: " + customerId);
    }

    @Override
    public DataResult<List<InvoiceListDto>> findByInvoiceDateBetween(Date startDate, Date endDate) {

        List<Invoice> invoices = this.invoiceDao.getByCreationDateBetween(startDate, endDate);

        List<InvoiceListDto> result = invoices.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
        manuelFieldSetter(invoices, result);

        return new SuccessDataResult<>(result, "Girilen tarihler arasındaki faturalar başarıyla listelendi");
    }

    private void manuelFieldSetter(Invoice invoice, GetInvoiceDto result) {

        result.setRentalCarId(invoice.getRentalCar().getRentalCarId());
        result.setCustomerId(invoice.getRentalCar().getCustomer().getCustomerId());
    }

    private void manuelFieldSetter(List<Invoice> invoices, List<InvoiceListDto> invoiceListDtoList){

        for(int i=0; i<invoices.size();i++){
            invoiceListDtoList.get(i).setRentalCarId(invoices.get(i).getRentalCar().getRentalCarId());
            invoiceListDtoList.get(i).setCustomerId(invoices.get(i).getRentalCar().getCustomer().getCustomerId());
        }

    }

    @Override
    public void createAndAddInvoice(int rentalCarId, int paymentId) throws BusinessException {

        //todo:parametre olarak total price verildi dogru mu?
        RentalCar rentalCar = this.rentalCarService.getById(rentalCarId);
        //todo:bunları buradan sil
        //todo: heryerde tekrar eden veri
        int totalDays = this.rentalCarService.getTotalDaysForRental(rentalCar.getStartDate(), rentalCar.getFinishDate());
        double priceOfDays = this.rentalCarService.calculateRentalCarTotalDayPrice(rentalCar.getStartDate(), rentalCar.getFinishDate(), this.carService.getDailyPriceByCarId(rentalCar.getCar().getCarId()));
        double priceOfDiffCity = this.rentalCarService.calculateCarDeliveredToTheSamCity(rentalCar.getRentedCity().getCityId(), rentalCar.getDeliveredCity().getCityId());
        double priceOfAdditionals = this.orderedAdditionalService.calculateTotalPriceForOrderedAdditionals(rentalCar.getRentalCarId(), totalDays);      //todo:bu kayıtlı additionalları hesaplıyor
        double totalPrice = priceOfDays + priceOfDiffCity + priceOfAdditionals;

        CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
        createInvoiceRequest.setStartDate(rentalCar.getStartDate());
        createInvoiceRequest.setFinishDate(rentalCar.getFinishDate());
        createInvoiceRequest.setTotalRentalDay((short) totalDays);
        createInvoiceRequest.setPriceOfDays(priceOfDays);
        createInvoiceRequest.setPriceOfDiffCity(priceOfDiffCity);
        createInvoiceRequest.setPriceOfAdditionals(priceOfAdditionals);
        createInvoiceRequest.setRentalCarTotalPrice(totalPrice);
        createInvoiceRequest.setRentalCarId(rentalCarId);
        createInvoiceRequest.setCustomerId(rentalCar.getCustomer().getCustomerId());
        createInvoiceRequest.setPaymentId(paymentId);

        add(createInvoiceRequest);
    }

    private String generateCode() {
        while (true){
            String code = GenerateRandomCode.generate();
            if(!this.invoiceDao.existsByInvoiceNo(code)){
                return code;
            }
        }
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

    private void checkIfExistsByPaymentId(int paymentId) throws BusinessException {
        if(!this.invoiceDao.existsByPayment_PaymentId(paymentId)){
            throw new BusinessException("Payment id not found in the invoice table, paymentId: " + paymentId);
        }
    }
}
