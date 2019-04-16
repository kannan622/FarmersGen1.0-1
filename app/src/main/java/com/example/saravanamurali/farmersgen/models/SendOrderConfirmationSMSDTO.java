package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class SendOrderConfirmationSMSDTO {

    @SerializedName("order_id")
    String orderIDToSMS;

    @SerializedName("user_id")
    String userIDToSendSMS;

    public SendOrderConfirmationSMSDTO(String orderIDToSMS, String userIDToSendSMS) {
        this.orderIDToSMS = orderIDToSMS;
        this.userIDToSendSMS = userIDToSendSMS;
    }

    public String getOrderIDToSMS() {
        return orderIDToSMS;
    }

    public String getUserIDToSendSMS() {
        return userIDToSendSMS;
    }
}
