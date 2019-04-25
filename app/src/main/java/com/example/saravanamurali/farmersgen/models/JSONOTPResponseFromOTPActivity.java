package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JSONOTPResponseFromOTPActivity {

    @SerializedName("Status")
    int status;
    @SerializedName("Message")
    String message;

    public JSONOTPResponseFromOTPActivity(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}


