package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class JSONResponseToViewPastOrderedProductList implements Serializable {

    @SerializedName("name")
    String pastOrderedProductUserName;

    @SerializedName("records")
    List<JsonResponseToViewPastOrderedProductListDTO> jsonResponseToViewPastOrderedProductListDTO;

    @SerializedName("total_amount")
    String pastOrderGrandTotal;

    public String getPastOrderedProductUserName() {
        return pastOrderedProductUserName;
    }

    public List<JsonResponseToViewPastOrderedProductListDTO> getJsonResponseToViewPastOrderedProductListDTO() {
        return jsonResponseToViewPastOrderedProductListDTO;
    }

    public String getPastOrderGrandTotal() {
        return pastOrderGrandTotal;
    }
}
