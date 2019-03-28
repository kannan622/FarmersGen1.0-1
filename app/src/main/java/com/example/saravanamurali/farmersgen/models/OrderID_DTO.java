package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class OrderID_DTO {

    @SerializedName("order_id")
    String orderID;

    public OrderID_DTO(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderID() {
        return orderID;
    }
}
