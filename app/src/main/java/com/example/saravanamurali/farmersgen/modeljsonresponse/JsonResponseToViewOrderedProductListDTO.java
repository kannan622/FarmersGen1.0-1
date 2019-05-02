package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class JsonResponseToViewOrderedProductListDTO implements Serializable {

    @SerializedName("product_name")
    String OrderedProducName;
    @SerializedName("brand_name")
    String OrderedBrandName;
    @SerializedName("count")
    String OrderedCount;
    @SerializedName("product_price")
    String OrderedProductPrice;
    @SerializedName("total_price")
    String OrderedTotalPrice;


    public JsonResponseToViewOrderedProductListDTO(String orderedProducName, String orderedBrandName, String orderedCount, String orderedProductPrice, String orderedTotalPrice){
        OrderedProducName = orderedProducName;
        OrderedBrandName = orderedBrandName;
        OrderedCount = orderedCount;
        OrderedProductPrice = orderedProductPrice;
        OrderedTotalPrice = orderedTotalPrice;

    }


    public String getOrderedProducName() {
        return OrderedProducName;
    }

    public String getOrderedBrandName() {
        return OrderedBrandName;
    }

    public String getOrderedCount() {
        return OrderedCount;
    }

    public String getOrderedProductPrice() {
        return OrderedProductPrice;
    }

    public String getOrderedTotalPrice() {
        return OrderedTotalPrice;
    }


}

