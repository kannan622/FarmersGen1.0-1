package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseToSendMobileNoFromLoginForgetPasswordDTO {

    @SerializedName("mobile")
    String mobileForLoginForgetPassword;

    @SerializedName("Status")
    int status;

    public String getMobileForLoginForgetPassword() {
        return mobileForLoginForgetPassword;
    }

    public int getStatus() {
        return status;
    }
}
