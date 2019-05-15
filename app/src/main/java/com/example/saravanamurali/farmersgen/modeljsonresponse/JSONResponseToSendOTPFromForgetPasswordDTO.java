package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseToSendOTPFromForgetPasswordDTO {
    @SerializedName("Status")
    String status;
    @SerializedName("message")
    String Message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return Message;
    }
}
