package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.dtos.invoiceDtos.gets.*;
import com.turkcell.rentACarProject.business.dtos.invoiceDtos.lists.InvoiceListDto;
import com.turkcell.rentACarProject.business.requests.invoiceRequests.InvoiceGetDateBetweenRequest;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.corporateCustomerExceptions.CorporateCustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.customerExceptions.CustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.individualCustomerExceptions.IndividualCustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions.InvoiceNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.invoiceExceptions.PaymentNotFoundInInvoiceException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.paymentExceptions.PaymentNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.rentalCarExceptions.RentalCarNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }


    @GetMapping("/getAll")
    public DataResult<List<InvoiceListDto>> getAll(){
        return this.invoiceService.getAll();
    }

    @GetMapping("/getIndividualCustomerInvoiceByInvoiceId")
    public DataResult<GetIndividualCustomerInvoiceByInvoiceIdDto> getIndividualCustomerInvoiceByInvoiceId(@RequestParam int invoiceId) throws InvoiceNotFoundException, IndividualCustomerNotFoundException {
        return this.invoiceService.getIndividualCustomerInvoiceByInvoiceId(invoiceId);
    }

    @GetMapping("/getCorporateCustomerInvoiceByInvoiceId")
    public DataResult<GetCorporateCustomerInvoiceByInvoiceIdDto> getCorporateCustomerInvoiceByInvoiceId(@RequestParam int invoiceId) throws CorporateCustomerNotFoundException, InvoiceNotFoundException {
        return this.invoiceService.getCorporateCustomerInvoiceByInvoiceId(invoiceId);
    }

    @GetMapping("/getIndividualCustomerInvoiceByInvoiceNo")
    public DataResult<GetIndividualCustomerInvoiceByInvoiceNoDto> getIndividualCustomerInvoiceByInvoiceNo(@RequestParam String invoiceNo) throws InvoiceNotFoundException, IndividualCustomerNotFoundException {
        return this.invoiceService.getIndividualCustomerInvoiceByInvoiceNo(invoiceNo);
    }

    @GetMapping("/getCorporateCustomerInvoiceByInvoiceNo")
    public DataResult<GetCorporateCustomerInvoiceByInvoiceNoDto> getCorporateCustomerInvoiceByInvoiceNo(@RequestParam String invoiceNo) throws CorporateCustomerNotFoundException, InvoiceNotFoundException {
        return this.invoiceService.getCorporateCustomerInvoiceByInvoiceNo(invoiceNo);
    }

    @GetMapping("/getInvoiceByPayment_PaymentId")
    public DataResult<GetInvoiceDto> getInvoiceByPayment_PaymentId(@RequestParam int paymentId) throws PaymentNotFoundInInvoiceException, PaymentNotFoundException {
        return this.invoiceService.getInvoiceByPayment_PaymentId(paymentId);
    }

    @GetMapping("/getAllByRentalCar_RentalCarId")
    public DataResult<List<InvoiceListDto>> getAllByRentalCar_RentalCarId(@RequestParam int rentalCarId) throws RentalCarNotFoundException {
        return this.invoiceService.getAllByRentalCar_RentalCarId(rentalCarId);
    }

    @GetMapping("/getAllByCustomer_CustomerId")
    public DataResult<List<InvoiceListDto>> getAllByCustomer_CustomerId(@RequestParam int customerId) throws CustomerNotFoundException {
        return this.invoiceService.getAllByCustomer_CustomerId(customerId);
    }

    @GetMapping("/getDateBetween")
    public DataResult<List<InvoiceListDto>> findByInvoiceDateBetween(@RequestBody @Valid InvoiceGetDateBetweenRequest invoiceGetDateBetweenRequest){
        return this.invoiceService.findByInvoiceDateBetween(invoiceGetDateBetweenRequest);
    }

}

