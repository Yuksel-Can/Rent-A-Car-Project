package com.turkcell.rentACarProject.api.models.rentalCar;

import com.turkcell.rentACarProject.business.requests.create.CreateInvoiceRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateOrderedAdditionalForRentalCarRequest;
import com.turkcell.rentACarProject.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACarProject.business.requests.create.CreateRentalCarRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakePaymentForCorporateCustomer {

    CreatePaymentRequest createPaymentRequest;
    CreateRentalCarRequest createRentalCarRequest;
    List<CreateOrderedAdditionalForRentalCarRequest> orderedAdditionalForRentalCarRequestList;
    CreateInvoiceRequest createInvoiceRequest;

}
