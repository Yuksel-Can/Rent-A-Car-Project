package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetCorporateCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetIndividualCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.gets.invoice.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.lists.invoice.InvoiceListDto;
import com.turkcell.rentACarProject.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.delete.DeleteInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.update.UpdateInvoiceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.List;

public interface InvoiceService {

    DataResult<List<InvoiceListDto>> getAll();

    Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;
    Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;
    Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException;

    DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceId(int invoiceId) throws BusinessException;
    DataResult<GetCorporateCustomerInvoiceDto> getCorporateCustomerInvoiceByInvoiceId(int invoiceId) throws BusinessException;

    DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceNo(String invoiceNo) throws BusinessException;
    DataResult<GetCorporateCustomerInvoiceDto> getCorporateCustomerInvoiceByInvoiceNo(String invoiceNo) throws BusinessException;
}