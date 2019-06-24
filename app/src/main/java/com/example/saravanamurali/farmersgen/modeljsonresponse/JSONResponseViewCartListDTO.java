package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.ViewCartDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseViewCartListDTO {

    @SerializedName("records")
    public List<ViewCartDTO> viewCartListRecord;

    //After Coupon apply
    @SerializedName("final_total")
    public String grandTotal;

    //Before Coupon apply
    @SerializedName("grand_total")
    String g_Total;

    @SerializedName("discount")
    String discountPrice;

    public String getG_Total() {
        return g_Total;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public List<ViewCartDTO> getViewCartListRecord() {
        return viewCartListRecord;
    }
}
