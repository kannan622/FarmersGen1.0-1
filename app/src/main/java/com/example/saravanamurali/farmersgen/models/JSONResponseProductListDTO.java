package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseProductListDTO {

    @SerializedName("records")
   public List<ProductListDTO> productListRecord;

    public List<ProductListDTO> getProductListRecord() {
        return productListRecord;
    }
}
