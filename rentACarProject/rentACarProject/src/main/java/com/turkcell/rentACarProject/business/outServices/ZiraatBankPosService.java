package com.turkcell.rentACarProject.business.outServices;

public class ZiraatBankPosService {

    public boolean makePayment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice){

        if(cardOwner.equals("can") || cardOwner.equals("lescom")){
            return true;
        }
        return false;
    }

}

