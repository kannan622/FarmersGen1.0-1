package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseToGetPastOrderDTO {

    @SerializedName("order_id")
    String pastOrderID;
    @SerializedName("delivery_status")
    String pastOrderDeliveryStatus;
    @SerializedName("created_on")
    String pastOrderDate;
    @SerializedName("total_amount")
    String pastOrderGrandTotal;

    public JSONResponseToGetPastOrderDTO(String pastOrderID, String pastOrderDeliveryStatus, String pastOrderDate, String pastOrderGrandTotal) {
        this.pastOrderID = pastOrderID;
        this.pastOrderDeliveryStatus = pastOrderDeliveryStatus;
        this.pastOrderDate = pastOrderDate;
        this.pastOrderGrandTotal = pastOrderGrandTotal;
    }

    public String getPastOrderID() {
        return pastOrderID;
    }

    public String getPastOrderDeliveryStatus() {
        return pastOrderDeliveryStatus;
    }

    public String getPastOrderDate() {
        return pastOrderDate;
    }

    public String getPastOrderGrandTotal() {
        return pastOrderGrandTotal;
    }
}
