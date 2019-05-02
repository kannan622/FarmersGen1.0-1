package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.example.saravanamurali.farmersgen.models.ViewCartPaymentGatewayDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JSONResponseViewCartOrdersatPaymentGateway {

    @SerializedName("records")
    public List<ViewCartPaymentGatewayDTO> viewCartListRecord;

    @SerializedName("grand_total")
    public String grandTotal;

    public List<ViewCartPaymentGatewayDTO> getViewCartListRecord() {
        return viewCartListRecord;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

}


