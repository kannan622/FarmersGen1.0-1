package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.io.Serializable;

public class JSONResponseToViewOrderedProductList implements Serializable {

    @SerializedName("name")
    String orderedProductUserName;

    @SerializedName("records")
    List<JsonResponseToViewOrderedProductListDTO> jsonResponseToViewOrderedProductListDTOS;

    @SerializedName("total_amount")
    String grandTotal;

    public String getOrderedProductUserName() {
        return orderedProductUserName;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public List<JsonResponseToViewOrderedProductListDTO> getJsonResponseToViewOrderedProductListDTOS() {
        return jsonResponseToViewOrderedProductListDTOS;
    }
}
