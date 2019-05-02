package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseForNPasswordAndCPasswrod {

    @SerializedName("Status")
    int Status;
    @SerializedName("Message")
    String message;

    public JSONResponseForNPasswordAndCPasswrod(int status, String message) {
        Status = status;
        this.message = message;
    }

    public int getStatus() {
        return Status;
    }

    public String getMessage()
    {
        return message;
    }
}
