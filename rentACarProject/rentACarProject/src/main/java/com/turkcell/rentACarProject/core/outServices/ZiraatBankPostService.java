package com.turkcell.rentACarProject.core.outServices;

public class ZiraatBankPostService {

    public static boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice){
        if(cardOwner =="can"){
            return true;
        }
        return false;
    }

}

