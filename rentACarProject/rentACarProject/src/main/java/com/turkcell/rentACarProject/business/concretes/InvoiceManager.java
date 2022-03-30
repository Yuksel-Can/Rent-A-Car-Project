package com.turkcell.rentACarProject.business.concretes;

import com.turkcell.rentACarProject.business.abstracts.InvoiceService;
import com.turkcell.rentACarProject.business.dtos.gets.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.lists.InvoiceListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import com.turkcell.rentACarProject.core.utilities.result.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.result.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACarProject.entities.concretes.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {

    private final InvoiceDao invoiceDao;
    private final ModelMapperService modelMapperService;

    @Autowired
    public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService) {
        this.invoiceDao = invoiceDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {

        List<Invoice> invoices = this.invoiceDao.findAll();

        List<InvoiceListDto> result = invoices.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<>(result, "Invoices listed");
    }

    @Override
    public Result add(CreateInvoiceRequest createInvoiceRequest) {

        //todo:check

        Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);

        this.invoiceDao.save(invoice);

        return new SuccessResult("Invoice added");
    }

    @Override
    public Result update(UpdateInvoiceRequest updateInvoiceRequest) {

        //todo:check
        //todo:check

        Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);

        this.invoiceDao.save(invoice);

        return new SuccessResult("Invoice updated, invoiceId: " + updateInvoiceRequest.getInvoiceId());
    }

    @Override
    public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) {

        //todo:check
        //todo:check other

        this.invoiceDao.deleteById(deleteInvoiceRequest.getInvoiceId());

        return new SuccessResult("Invoice deleted");
    }

    @Override
    public DataResult<GetInvoiceDto> getInvoiceByInvoiceId(int invoiceId) {

        //todo:check

        Invoice invoice = this.invoiceDao.getById(invoiceId);

        GetInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

        return new SuccessDataResult<>(result, "Invoice listed by invoice id, invoiceId: " + invoiceId);
    }

    @Override
    public DataResult<GetInvoiceDto> getInvoiceByInvoiceNo(String invoiceNo) {

        //todo:check

        Invoice invoice = this.invoiceDao.getInvoiceByInvoiceNo(invoiceNo);

        GetInvoiceDto result = this.modelMapperService.forDto().map(invoice, GetInvoiceDto.class);

        return new SuccessDataResult<>(result, "Invoice listed by invoice number, invoiceNo: " + invoiceNo);
    }
}
