package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class OTPandMobileNoDTO {

    @SerializedName("otp")
    String otp;
    @SerializedName("mobile")
    String mobileNo;

    public OTPandMobileNoDTO(String otp, String mobileNo) {
        this.otp = otp;
        this.mobileNo = mobileNo;
    }

    public String getOtp() {
        return otp;
    }

    public String getMobileNo() {
        return mobileNo;
    }
}
