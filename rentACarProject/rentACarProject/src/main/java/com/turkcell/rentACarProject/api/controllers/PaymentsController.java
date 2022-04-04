package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForCorporateCustomer;
import com.turkcell.rentACarProject.api.models.rentalCar.MakePaymentForIndividualCustomer;
import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.dtos.gets.payment.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.lists.payment.PaymentListDto;
import com.turkcell.rentACarProject.business.requests.create.CreatePaymentRequest;
import com.turkcell.rentACarProject.core.utilities.exception.BusinessException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import com.turkcell.rentACarProject.core.utilities.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

    private PaymentService paymentService;

    @Autowired
    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getAll")
    public DataResult<List<PaymentListDto>> getAll(){
        return this.paymentService.getAll();
    }

    @PostMapping("/makePaymentForIndividualCustomer")
    public Result makePaymentForIndividualCustomer(@RequestBody @Valid MakePaymentForIndividualCustomer makePaymentForIndividualCustomer) throws BusinessException {
        return this.paymentService.makePaymentForIndividualCustomer(makePaymentForIndividualCustomer);
    }

    @PostMapping("/makePaymentForCorporateCustomer")
    public Result makePaymentForCorporateCustomer(@RequestBody @Valid MakePaymentForCorporateCustomer makePaymentForCorporateCustomer) throws BusinessException {
        return this.paymentService.makePaymentForCorporateCustomer(makePaymentForCorporateCustomer);
    }

    @GetMapping("/getById")
    public DataResult<GetPaymentDto> getById(@RequestParam int paymentId) throws BusinessException {
        return this.paymentService.getById(paymentId);
    }

    @GetMapping("/getAllByRentalCar_RentalCarId")
    public DataResult<List<PaymentListDto>> getAllPaymentByRentalCar_RentalCarId(@RequestParam int rentalCarId) throws BusinessException {
        return this.paymentService.getAllPaymentByRentalCar_RentalCarId(rentalCarId);
    }

}
