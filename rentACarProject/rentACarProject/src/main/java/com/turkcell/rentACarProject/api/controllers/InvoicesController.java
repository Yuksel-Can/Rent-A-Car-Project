package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.dtos.invoiceDtos.gets.*;
import com.turkcell.rentACarProject.business.dtos.invoiceDtos.lists.InvoiceListDto;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public DataResult<GetIndividualCustomerInvoiceByInvoiceIdDto> getIndividualCustomerInvoiceByInvoiceId(@RequestParam int invoiceId) throws BusinessException {
        return this.invoiceService.getIndividualCustomerInvoiceByInvoiceId(invoiceId);
    }

    @GetMapping("/getCorporateCustomerInvoiceByInvoiceId")
    public DataResult<GetCorporateCustomerInvoiceByInvoiceIdDto> getCorporateCustomerInvoiceByInvoiceId(@RequestParam int invoiceId) throws BusinessException {
        return this.invoiceService.getCorporateCustomerInvoiceByInvoiceId(invoiceId);
    }

    @GetMapping("/getIndividualCustomerInvoiceByInvoiceNo")
    public DataResult<GetIndividualCustomerInvoiceByInvoiceNoDto> getIndividualCustomerInvoiceByInvoiceNo(@RequestParam String invoiceNo) throws BusinessException {
        return this.invoiceService.getIndividualCustomerInvoiceByInvoiceNo(invoiceNo);
    }

    @GetMapping("/getCorporateCustomerInvoiceByInvoiceNo")
    public DataResult<GetCorporateCustomerInvoiceByInvoiceNoDto> getCorporateCustomerInvoiceByInvoiceNo(@RequestParam String invoiceNo) throws BusinessException {
        return this.invoiceService.getCorporateCustomerInvoiceByInvoiceNo(invoiceNo);
    }

    @GetMapping("/getInvoiceByPayment_PaymentId")
    public DataResult<GetInvoiceDto> getInvoiceByPayment_PaymentId(@RequestParam int paymentId) throws BusinessException {
        return this.invoiceService.getInvoiceByPayment_PaymentId(paymentId);
    }

    @GetMapping("/getAllByRentalCar_RentalCarId")
    public DataResult<List<InvoiceListDto>> getAllByRentalCar_RentalCarId(@RequestParam int rentalCarId) throws BusinessException {
        return this.invoiceService.getAllByRentalCar_RentalCarId(rentalCarId);
    }

    @GetMapping("/getAllByCustomer_CustomerId")
    public DataResult<List<InvoiceListDto>> getAllByCustomer_CustomerId(@RequestParam int customerId) throws BusinessException {
        return this.invoiceService.getAllByCustomer_CustomerId(customerId);
    }

    @GetMapping("/getDateBetween")
    public DataResult<List<InvoiceListDto>> findByInvoiceDateBetween(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        return this.invoiceService.findByInvoiceDateBetween(startDate, endDate);
    }
}

