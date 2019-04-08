package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JsonOrderResponse {

    @SerializedName("responsecode")
    String responseCode;

    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public String getResponseCode() {
        return responseCode;
    }
}
