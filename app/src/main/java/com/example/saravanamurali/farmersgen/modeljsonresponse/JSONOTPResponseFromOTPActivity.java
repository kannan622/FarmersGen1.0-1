package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONOTPResponseFromOTPActivity {

    @SerializedName("responsecode")
    int status;
    @SerializedName("message")
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


