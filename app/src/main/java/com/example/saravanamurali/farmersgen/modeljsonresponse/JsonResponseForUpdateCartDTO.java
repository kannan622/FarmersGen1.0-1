package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseForUpdateCartDTO {

    @SerializedName("responsecode")
    int updateResponseCode;
    @SerializedName("message")
    String message;
    @SerializedName("product_code")
    String productCode;
    @SerializedName("count")
    String count;
    @SerializedName("total_price")
    String totalPrice;
    @SerializedName("device_id")
    String deviceID;
    @SerializedName("grand_total")
    String grandTotal;

    public int getUpdateResponseCode() {
        return updateResponseCode;
    }

    public String getMessage() {
        return message;
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

    public String getDeviceID() {
        return deviceID;
    }

    public String getGrandTotal() {
        return grandTotal;
    }
}
