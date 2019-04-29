package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ProductDescDTO {

    @SerializedName("brand_id")
    String brandID;
    @SerializedName("product_code")
    String productID;

    public ProductDescDTO(String brandID, String productID) {
        this.brandID = brandID;
        this.productID = productID;
    }

    public String getBrandID() {
        return brandID;
    }

    public String getProductID() {
        return productID;
    }
}
