package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDTO {

    @SerializedName("user_id")
    private String userId;
    @SerializedName("address_id")
    private String addressId;
    @SerializedName("type")
    private String type;
    @SerializedName("total_amount")
    private String grandTotalToPay;
    @SerializedName("coupon_id")
    String coupon_ID;
    @SerializedName("order_details")
    private List<OrderDetailsDTO> orderDetails;

    public OrderDTO(String userId, String addressId, String type, String grandTotalToPay, String coupon_ID, List<OrderDetailsDTO> orderDetails) {
        this.userId = userId;
        this.addressId = addressId;
        this.type = type;
        this.grandTotalToPay = grandTotalToPay;
        this.coupon_ID = coupon_ID;
        this.orderDetails = orderDetails;
    }

    public String getUserId() {
        return userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getType() {
        return type;
    }

    public String getgrandTotalToPay() {
        return grandTotalToPay;
    }

    public String getCoupon_ID() {
        return coupon_ID;
    }


    public List<OrderDetailsDTO> getOrderDetails() {
        return orderDetails;
    }


}
