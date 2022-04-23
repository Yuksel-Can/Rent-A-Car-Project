package com.turkcell.rentACarProject.api.controllers;

import com.turkcell.rentACarProject.business.abstracts.CreditCardService;
import com.turkcell.rentACarProject.business.dtos.creditCardDtos.gets.GetCreditCardDto;
import com.turkcell.rentACarProject.business.dtos.creditCardDtos.lists.CreditCardListDto;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.creditCardExceptions.CreditCardNotFoundException;
import com.turkcell.rentACarProject.core.utilities.exceptions.businessExceptions.customerExceptions.CustomerNotFoundException;
import com.turkcell.rentACarProject.core.utilities.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/creditCards")
public class CreditCardsController {

    private final CreditCardService creditCardService;

    @Autowired
    public CreditCardsController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }


    @GetMapping("/getAll")
    public DataResult<List<CreditCardListDto>> getAll(){
        return this.creditCardService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<GetCreditCardDto> getById(@RequestParam int creditCardId) throws CreditCardNotFoundException {
        return this.creditCardService.getById(creditCardId);
    }

    @GetMapping("/getAllCreditCardByCustomer_CustomerId")
    public DataResult<List<CreditCardListDto>> getAllCreditCardByCustomer_CustomerId(@RequestParam int customerId) throws CustomerNotFoundException {
        return this.creditCardService.getAllCreditCardByCustomer_CustomerId(customerId);
    }

}
