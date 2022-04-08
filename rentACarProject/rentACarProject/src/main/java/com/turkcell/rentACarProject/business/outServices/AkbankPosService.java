package com.turkcell.rentACarProject.business.outServices;

public class AkbankPosService {

    public boolean makePayment(String cardOwner, String cardNumber, String cardCvv, String cardExpirationDate, double totalPrice){

        if(cardOwner.equals("can") || cardOwner.equals("lescom")){
            return true;
        }
        return false;
    }

}