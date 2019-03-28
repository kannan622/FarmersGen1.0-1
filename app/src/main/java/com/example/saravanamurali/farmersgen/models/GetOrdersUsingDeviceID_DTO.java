package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class GetOrdersUsingDeviceID_DTO {
    @SerializedName("device_id")
    private String addCartMobile_ID;

    public GetOrdersUsingDeviceID_DTO(String addCartMobile_ID) {
        this.addCartMobile_ID = addCartMobile_ID;
    }

    public String getAddCartMobile_ID() {
        return addCartMobile_ID;
    }
}
