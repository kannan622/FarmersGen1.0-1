package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class OrderDetailsDTO {

    @SerializedName("product_code")
    private String productCode;
    @SerializedName("count")
    private String count;
    @SerializedName("total_price")
    private String totalPrice;
    @SerializedName("device_id")
    private String deviceId;

    public OrderDetailsDTO(String productCode, String count, String totalPrice, String deviceId) {
        this.productCode = productCode;
        this.count = count;
        this.totalPrice = totalPrice;
        this.deviceId = deviceId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getCount() {
        return count;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
