package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ViewCartPaymentGatewayDTO {

    @SerializedName("id")
    String id;
    @SerializedName("brand_id")
    String brand_ID;
    @SerializedName("brand_name")
    String brand_Name;
    @SerializedName("product_code")
    String product_Code;
    @SerializedName("product_name")
    String Product_Name;
    @SerializedName("product_image")
    String product_Image;
    @SerializedName("price")
    String price;
    @SerializedName("count")
    String count;
    @SerializedName("total_price")
    String total_price;
    @SerializedName("device_id")
    String device_ID;


    public String getId() {
        return id;
    }

    public String getBrand_ID() {
        return brand_ID;
    }

    public String getBrand_Name() {
        return brand_Name;
    }

    public String getProduct_Code() {
        return product_Code;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public String getProduct_Image() {
        return product_Image;
    }

    public String getPrice() {
        return price;
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
