package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class MenuCartUpdateDTO {

    @SerializedName("count")
    String count;
    @SerializedName("product_code")
    String productCode;
    @SerializedName("product_price")
    String productPrice;
    @SerializedName("device_id")
    String mobile_ID;

    //Constructor for Delete
    public MenuCartUpdateDTO(String productCode, String mobile_ID){
        this.mobile_ID=mobile_ID;
        this.productCode=productCode;
    }


//Constructor for Update
    public MenuCartUpdateDTO(String count, String productCode, String productPrice, String mobile_ID) {
        this.count = count;
        this.productCode = productCode;
        this.productPrice = productPrice;
        this.mobile_ID = mobile_ID;
    }
}
