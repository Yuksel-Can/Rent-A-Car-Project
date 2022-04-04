package com.turkcell.rentACarProject.core.outServices;

public class ZiraatBankPostService {

    public static boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice){
        System.out.println("selam biz banka");
        if(cardOwner.equals("can")){
            return true;
        }
        return false;
    }

}

