package com.turkcell.rentACarProject.business.abstracts;

import com.turkcell.rentACarProject.business.dtos.invoiceDtos.gets.GetCorporateCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.invoiceDtos.gets.GetIndividualCustomerInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.invoiceDtos.gets.GetInvoiceDto;
import com.turkcell.rentACarProject.business.dtos.invoiceDtos.lists.InvoiceListDto;
import com.turkcell.rentACarProject.business.requests.invoiceRequests.CreateInvoiceRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;

import java.util.Date;
import java.util.List;

public interface InvoiceService {

    DataResult<List<InvoiceListDto>> getAll();

    Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;
    void createAndAddInvoice(int rentalCarId, int paymentId) throws BusinessException;

    DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceId(int invoiceId) throws BusinessException;
    DataResult<GetCorporateCustomerInvoiceDto> getCorporateCustomerInvoiceByInvoiceId(int invoiceId) throws BusinessException;

    DataResult<GetIndividualCustomerInvoiceDto> getIndividualCustomerInvoiceByInvoiceNo(String invoiceNo) throws BusinessException;
    DataResult<GetCorporateCustomerInvoiceDto> getCorporateCustomerInvoiceByInvoiceNo(String invoiceNo) throws BusinessException;

    DataResult<GetInvoiceDto> getInvoiceByPayment_PaymentId(int paymentId) throws BusinessException;

    DataResult<List<InvoiceListDto>> getAllByRentalCar_RentalCarId(int rentalCarId) throws BusinessException;
    DataResult<List<InvoiceListDto>> getAllByCustomer_CustomerId(int customerId) throws BusinessException;


    DataResult<List<InvoiceListDto>> findByInvoiceDateBetween(Date startDate, Date endDate);
}
