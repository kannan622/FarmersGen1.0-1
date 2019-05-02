package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonOrderResponse {

    @SerializedName("response_code")
    int responseCode;

    @SerializedName("status")
    int status;

    @SerializedName("message")
    String message;

    @SerializedName("order_id")
    String orderId;

    public int getStatus() {
        return status;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getMessage() {
        return message;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
