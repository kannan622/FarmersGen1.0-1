package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class OrderID_DTO {

    @SerializedName("order_id")
    String orderID;

    @SerializedName("user_id")
    String userIDToCancelOrder;

    public OrderID_DTO(String orderID) {
        this.orderID = orderID;
    }

    public OrderID_DTO(String orderID, String userIDToCancelOrder) {
        this.orderID = orderID;
        this.userIDToCancelOrder = userIDToCancelOrder;
    }

    public String getUserIDToCancelOrder() {
        return userIDToCancelOrder;
    }

    public String getOrderID() {
        return orderID;
    }
}
