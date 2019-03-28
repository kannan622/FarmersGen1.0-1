package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ProceedBtnCheckingDTO {

    @SerializedName("")
    private String device_ID;

    @SerializedName("")
   private String mobileNumberChecked;

    @SerializedName("")
    private String addresschecked;

    //Sending mbile id to check 3 kinds of activity
    public ProceedBtnCheckingDTO(String device_ID) {
        this.device_ID = device_ID;
    }

    public String getMobileNumberChecked() {
        return mobileNumberChecked;
    }

    public String getAddresschecked() {
        return addresschecked;
    }

    public String getDevice_ID() {
        return device_ID;
    }
}
