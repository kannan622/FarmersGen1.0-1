package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseForCancelOrderDTO {

    @SerializedName("order_id")
    String orderId;
    @SerializedName("delivery_status")
    String deliveryStatus;
    @SerializedName("created_on")
    String date;
    @SerializedName("total_amount")
    String grandTotal;

    public JSONResponseForCancelOrderDTO(String orderId, String deliveryStatus, String date, String grandTotal) {
        this.orderId = orderId;
        this.deliveryStatus = deliveryStatus;
        this.date = date;
        this.grandTotal = grandTotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public String getDate() {
        return date;
    }

    public String getGrandTotal() {
        return grandTotal;
    }
}
