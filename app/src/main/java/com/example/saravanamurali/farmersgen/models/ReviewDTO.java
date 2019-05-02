package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ReviewDTO {

    @SerializedName("brand_id")
    String brandID;

    @SerializedName("product_code")
    String productCode;

    public ReviewDTO(String productCode) {
        this.productCode = productCode;
    }


}
