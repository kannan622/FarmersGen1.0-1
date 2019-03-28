package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JSONOTPResponseFromOTPActivity {

    @SerializedName("Status")
    String status;
    @SerializedName("Message")
    String message;

    public JSONOTPResponseFromOTPActivity(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}


