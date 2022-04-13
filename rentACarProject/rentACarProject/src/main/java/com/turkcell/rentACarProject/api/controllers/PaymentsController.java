package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalAddModel;
import com.turkcell.rentACarProject.api.models.orderedAdditional.OrderedAdditionalUpdateModel;
import com.turkcell.rentACarProject.api.models.rentalCar.*;
import com.turkcell.rentACarProject.business.abstracts.PaymentService;
import com.turkcell.rentACarProject.business.concretes.CreditCardManager;
import com.turkcell.rentACarProject.business.dtos.paymentDtos.gets.GetPaymentDto;
import com.turkcell.rentACarProject.business.dtos.paymentDtos.lists.PaymentListDto;
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

    @PostMapping("/makePaymentForIndividualRentAdd")
    public Result makePaymentForIndividualRentAdd(@RequestBody @Valid MakePaymentForIndividualRentAdd makePaymentForIndividualRentAdd, @RequestParam CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        return this.paymentService.makePaymentForIndividualRentAdd(makePaymentForIndividualRentAdd, cardSaveInformation);
    }

    @PostMapping("/makePaymentForCorporateRentAdd")
    public Result makePaymentForCorporateRentAdd(@RequestBody @Valid MakePaymentForCorporateRentAdd makePaymentForCorporateRentAdd, @RequestParam CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        return this.paymentService.makePaymentForCorporateRentAdd(makePaymentForCorporateRentAdd, cardSaveInformation);
    }

    @PutMapping("/makePaymentForIndividualRentUpdate")
    public Result makePaymentForIndividualRentUpdate(@RequestBody @Valid MakePaymentForIndividualRentUpdate makePaymentForIndividualRentUpdate, @RequestParam CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        return this.paymentService.makePaymentForIndividualRentUpdate(makePaymentForIndividualRentUpdate, cardSaveInformation);
    }

    @PutMapping("/makePaymentForCorporateRentUpdate")
    public Result makePaymentForCorporateRentUpdate(@RequestBody @Valid MakePaymentForCorporateRentUpdate makePaymentForCorporateRentUpdate, @RequestParam CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        return this.paymentService.makePaymentForCorporateRentUpdate(makePaymentForCorporateRentUpdate, cardSaveInformation);
    }

    @PutMapping("/makePaymentForRentDeliveryDateUpdate")
    public Result makePaymentForRentDeliveryDateUpdate(@RequestBody @Valid MakePaymentForRentDeliveryDateUpdate makePaymentForRentDeliveryDateUpdate, @RequestParam CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        return this.paymentService.makePaymentForRentDeliveryDateUpdate(makePaymentForRentDeliveryDateUpdate, cardSaveInformation);
    }

   @PostMapping("/makePaymentForOrderedAdditionalAdd")
    public Result makePaymentForOrderedAdditionalAdd(@RequestBody @Valid OrderedAdditionalAddModel orderedAdditionalAddModel, @RequestParam CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        return this.paymentService.makePaymentForOrderedAdditionalAdd(orderedAdditionalAddModel, cardSaveInformation);
    }

    @PutMapping("/makePaymentForOrderedAdditionalUpdate")
    public Result makePaymentForOrderedAdditionalUpdate(@RequestBody @Valid OrderedAdditionalUpdateModel orderedAdditionalUpdateModel, @RequestParam CreditCardManager.CardSaveInformation cardSaveInformation) throws BusinessException {
        return this.paymentService.makePaymentForOrderedAdditionalUpdate(orderedAdditionalUpdateModel, cardSaveInformation);
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
