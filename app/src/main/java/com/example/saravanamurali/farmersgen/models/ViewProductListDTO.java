package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ViewProductListDTO {

    @SerializedName("brand_id")
    String product_ID;

    @SerializedName("device_id")
    String device_ID;

    public ViewProductListDTO(String product_ID, String device_ID) {
        this.product_ID = product_ID;
        this.device_ID = device_ID;
    }

    public String getProduct_ID() {
        return product_ID;
    }

    public String getDevice_ID() {
        return device_ID;
    }
}
