package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseCancelCouponDTO {

    @SerializedName("response_code")
    String responseCode;

    @SerializedName("message")
    String message;

    public JSONResponseCancelCouponDTO(String responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
