package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.ViewCartDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseViewCartListDTO {

    @SerializedName("records")
    public List<ViewCartDTO> viewCartListRecord;

    @SerializedName("grand_total")
    public String grandTotal;

    public String getGrandTotal() {
        return grandTotal;
    }

    public List<ViewCartDTO> getViewCartListRecord() {
        return viewCartListRecord;
    }
}
