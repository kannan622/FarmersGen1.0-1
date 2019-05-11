package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseFromServerDBDTO {

    @SerializedName("status")
    int status;
    @SerializedName("message")
    String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
