package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class AddCartDTO {

    @SerializedName("product_code")
    private String addCartProductCode;
    @SerializedName("count")
    private String addCartCount;

    @SerializedName("device_id")
    private String addCartMobile_ID;

    @SerializedName("product_price")
    private String productPrice;

    public AddCartDTO(String addCartMobile_ID){
        this.addCartMobile_ID=addCartMobile_ID;
    }


    //constructor for add count in add cart
    public AddCartDTO(String addCartProductCode, String addCartCount, String productPrice,  String addCartMobile_ID ) {
        this.addCartProductCode = addCartProductCode;
        this.addCartCount = addCartCount;
        this.productPrice=productPrice;
        this.addCartMobile_ID = addCartMobile_ID;
    }

    public String getAddCartProductCode() {
        return addCartProductCode;
    }

    public void setAddCartProductCode(String addCartProductCode) {
        this.addCartProductCode = addCartProductCode;
    }

    public String getAddCartCount() {
        return addCartCount;
    }

    public void setAddCartCount(String addCartCount) {
        this.addCartCount = addCartCount;
    }

    public String getAddCartMobile_ID() {
        return addCartMobile_ID;
    }

    public void setAddCartMobile_ID(String addCartMobile_ID) {
        this.addCartMobile_ID = addCartMobile_ID;
    }
}
