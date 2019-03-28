package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ViewCartUpdateDTO {
    @SerializedName("count")
    String count;
    @SerializedName("product_code")
    String productCode;
    @SerializedName("product_price")
    String productPrice;
    @SerializedName("device_id")
    String mobile_ID;

    public ViewCartUpdateDTO(String productCode,String mobile_ID){
        this.productCode=productCode;
        this.mobile_ID=mobile_ID;
    }



    //Constructor for update count in View Cart
    public ViewCartUpdateDTO(String count, String productCode, String productPrice, String mobile_ID) {
        this.count = count;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.mobile_ID = mobile_ID;
    }

    public String getCount() {
        return count;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getMobile_ID() {
        return mobile_ID;
    }
}
