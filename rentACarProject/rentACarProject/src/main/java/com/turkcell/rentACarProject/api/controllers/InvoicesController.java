package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetIndividualCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.lists.invoice.InvoiceListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
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

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
        return this.invoiceService.add(createInvoiceRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
        return this.invoiceService.update(updateInvoiceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody @Valid DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
        return this.invoiceService.delete(deleteInvoiceRequest);
    }

    @GetMapping("/getIndividualCustomerInvoiceByInvoiceId")
    public DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceId(@RequestParam int invoiceId) throws BusinessException {
        return this.invoiceService.getIndividualCustomerInvoiceByInvoiceId(invoiceId);
    }

    @GetMapping("/getCorporateCustomerInvoiceByInvoiceId")
    public DataResult getCorporateCustomerInvoiceByInvoiceId(@RequestParam int invoiceId) throws BusinessException {
        return this.invoiceService.getCorporateCustomerInvoiceByInvoiceId(invoiceId);
    }

    @GetMapping("/getIndividualCustomerInvoiceByInvoiceNo")
    public DataResult getIndividualCustomerInvoiceByInvoiceNo(@RequestParam String invoiceNo) throws BusinessException {
        return this.invoiceService.getIndividualCustomerInvoiceByInvoiceNo(invoiceNo);
    }

    @GetMapping("/getCorporateCustomerInvoiceByInvoiceNo")
    public DataResult getCorporateCustomerInvoiceByInvoiceNo(@RequestParam String invoiceNo) throws BusinessException {
        return this.invoiceService.getCorporateCustomerInvoiceByInvoiceNo(invoiceNo);
    }
}

