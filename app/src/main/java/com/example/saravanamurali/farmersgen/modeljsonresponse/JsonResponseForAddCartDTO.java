package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseForAddCartDTO {
    @SerializedName("responsecode")
   private int responseCode;
    @SerializedName("message")
   private String message;

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
