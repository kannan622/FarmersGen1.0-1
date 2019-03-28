package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JSONResponseToSendMobileNoFromLoginForgetPasswordDTO {

    @SerializedName("mobile")
    String mobileForLoginForgetPassword;

    public String getMobileForLoginForgetPassword() {
        return mobileForLoginForgetPassword;
    }
}
