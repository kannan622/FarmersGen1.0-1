package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.ProductListDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseProductListDTO {

    @SerializedName("records")
   public List<ProductListDTO> productListRecord;

    public List<ProductListDTO> getProductListRecord() {
        return productListRecord;
    }
}
