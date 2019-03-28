package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ProceedBtn_GetCheckedDTO {

    @SerializedName("")
    String mobileNumberChecked;

    @SerializedName("")
    String addresschecked;

    public ProceedBtn_GetCheckedDTO(String mobileNumberChecked, String addresschecked) {
        this.mobileNumberChecked = mobileNumberChecked;
        this.addresschecked = addresschecked;
    }

    public String getMobileNumberChecked() {
        return mobileNumberChecked;
    }

    public String getAddresschecked() {
        return addresschecked;
    }
}
