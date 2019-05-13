package com.example.saravanamurali.farmersgen.models;


import com.google.gson.annotations.SerializedName;

public class GetDataFromSqlLiteDTO {
    @SerializedName("product_code")
    String product_code;
    @SerializedName("count")
    String count;
    @SerializedName("total_price")
    String total_price;
    @SerializedName("device_id")
    String device_ID;


    public GetDataFromSqlLiteDTO(String product_code, String count, String total_price, String device_ID) {
        this.product_code = product_code;
        this.count = count;
        this.total_price = total_price;
        this.device_ID = device_ID;
    }

    public String getProduct_code() {
        return product_code;
    }

    public String getCount() {
        return count;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getDevice_ID() {
        return device_ID;
    }
}
