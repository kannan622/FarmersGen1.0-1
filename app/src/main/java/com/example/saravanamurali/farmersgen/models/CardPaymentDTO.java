package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class CardPaymentDTO {
    @SerializedName("price")
    String userPrice_CardPayment;
    @SerializedName("name")
    String userName_CardPayment;
    @SerializedName("email")
    String userEmail_CardPayment;
    @SerializedName("mobile")
    String userMobile_CardPayment;

    public CardPaymentDTO(String userPrice_CardPayment, String userName_CardPayment, String userEmail_CardPayment, String userMobile_CardPayment) {
        this.userPrice_CardPayment = userPrice_CardPayment;
        this.userName_CardPayment = userName_CardPayment;
        this.userEmail_CardPayment = userEmail_CardPayment;
        this.userMobile_CardPayment = userMobile_CardPayment;
    }

    public String getUserPrice_CardPayment() {
        return userPrice_CardPayment;
    }

    public String getUserName_CardPayment() {
        return userName_CardPayment;
    }

    public String getUserEmail_CardPayment() {
        return userEmail_CardPayment;
    }

    public String getUserMobile_CardPayment() {
        return userMobile_CardPayment;
    }
}
