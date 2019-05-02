package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JsonResponseToViewPastOrderedProductListDTO implements Serializable {

    @SerializedName("product_name")
    String pastOrderedProducName;
    @SerializedName("brand_name")
    String pastOrderedBrandName;
    @SerializedName("count")
    String pastOrderedCount;
    @SerializedName("product_price")
    String pastOrderedProductPrice;
    @SerializedName("total_price")
    String pastOrderedTotalPrice;

    public JsonResponseToViewPastOrderedProductListDTO(String pastOrderedProducName, String pastOrderedBrandName, String pastOrderedCount, String pastOrderedProductPrice, String pastOrderedTotalPrice) {
        this.pastOrderedProducName = pastOrderedProducName;
        this.pastOrderedBrandName = pastOrderedBrandName;
        this.pastOrderedCount = pastOrderedCount;
        this.pastOrderedProductPrice = pastOrderedProductPrice;
        this.pastOrderedTotalPrice = pastOrderedTotalPrice;
    }

    public String getPastOrderedProducName() {
        return pastOrderedProducName;
    }

    public String getPastOrderedBrandName() {
        return pastOrderedBrandName;
    }

    public String getPastOrderedCount() {
        return pastOrderedCount;
    }

    public String getPastOrderedProductPrice() {
        return pastOrderedProductPrice;
    }

    public String getPastOrderedTotalPrice() {
        return pastOrderedTotalPrice;
    }
}
