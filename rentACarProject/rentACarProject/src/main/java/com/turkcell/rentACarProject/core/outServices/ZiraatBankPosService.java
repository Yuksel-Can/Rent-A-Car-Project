package com.turkcell.rentACarProject.core.outServices;

public class ZiraatBankPosService {

    public static boolean payment(String cardNumber, String cardOwner, String cardCvv, String cardExpirationDate, double totalPrice){
        System.out.println("selam biz banka");
        if(cardOwner.equals("can") || cardOwner.equals("lescom")){
            return true;
        }
        return false;
    }

}

