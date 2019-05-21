package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseUpdateCartDTO {


    @SerializedName("responsecode")
    int status;

    @SerializedName("product_code")
    String updateProductCode;

    @SerializedName("count")
    String updateCount;

    @SerializedName("total_price")
    String updateTotalPrice;

    @SerializedName("device_id")
    String updateDevice_ID;

    @SerializedName("grand_total")
    String grandTotal;

    public JSONResponseUpdateCartDTO(String updateProductCode, String updateCount, String updateTotalPrice, String updateDevice_ID, String grandTotal) {
        this.updateProductCode = updateProductCode;
        this.updateCount = updateCount;
        this.updateTotalPrice = updateTotalPrice;
        this.updateDevice_ID = updateDevice_ID;
        this.grandTotal = grandTotal;
    }

    public int getStatus() {
        return status;
    }

    public String getGrandTotal() {
        return grandTotal;
    }


    public String getUpdateProductCode() {
        return updateProductCode;
    }

    public String getUpdateCount() {
        return updateCount;
    }

    public String getUpdateTotalPrice() {
        return updateTotalPrice;
    }

    public String getUpdateDevice_ID() {
        return updateDevice_ID;
    }


}


