package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseForDeleteFavDTO {

    @SerializedName("responsecode")
    int responseCode;
    @SerializedName("message")
    String message;

    public int getResponseCode() {
        return responseCode;
    }

    public String getMessage() {
        return message;
    }
}
