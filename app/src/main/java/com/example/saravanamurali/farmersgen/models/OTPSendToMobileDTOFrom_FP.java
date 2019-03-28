package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class OTPSendToMobileDTOFrom_FP {

    @SerializedName("mobile")
    String mobile;

    public OTPSendToMobileDTOFrom_FP(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }
}
