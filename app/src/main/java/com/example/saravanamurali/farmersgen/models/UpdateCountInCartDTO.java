package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class UpdateCountInCartDTO {

    @SerializedName("product_code")
    private String updateCartProductCode;
    @SerializedName("count")
    private String upateCartCount;

    @SerializedName("device_id")
    private String uCapatertMobile_ID;

    @SerializedName("product_price")
    private String updateProductPrice;

    public UpdateCountInCartDTO(String addCartCount,String updateCartProductCode,  String updateProductPrice, String uCapatertMobile_ID) {
        this.updateCartProductCode = updateCartProductCode;
        this.upateCartCount = addCartCount;
        this.uCapatertMobile_ID = uCapatertMobile_ID;
        this.updateProductPrice = updateProductPrice;
    }

    public String getAddCartProductCode() {
        return updateCartProductCode;
    }

    public String getAddCartCount() {
        return upateCartCount;
    }

    public String getAddCartMobile_ID() {
        return uCapatertMobile_ID;
    }

    public String getProductPrice() {
        return updateProductPrice;
    }
}
