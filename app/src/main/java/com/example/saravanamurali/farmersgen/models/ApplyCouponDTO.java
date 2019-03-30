package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ApplyCouponDTO {
    @SerializedName("off_userid")
    String user_ID;

    @SerializedName("coupon_id")
    String coupon_ID;


    @SerializedName("off_code")
    String coupon_Code;

    @SerializedName("off_price")
    String offer_Price;

    public ApplyCouponDTO(String user_ID, String coupon_Code, String coupon_ID, String offer_Price) {
        this.user_ID = user_ID;
        this.coupon_Code = coupon_Code;
        this.coupon_ID = coupon_ID;
        this.offer_Price = offer_Price;
    }

    public String getOffer_Price() {
        return offer_Price;
    }

    public void setOffer_Price(String offer_Price) {
        this.offer_Price = offer_Price;
    }

    public void setUser_ID(String user_ID) {
        this.user_ID = user_ID;
    }

    public void setCoupon_Code(String coupon_Code) {
        this.coupon_Code = coupon_Code;
    }

    public void setCoupon_ID(String coupon_ID) {
        this.coupon_ID = coupon_ID;
    }

    public String getUser_ID() {
        return user_ID;
    }

    public String getCoupon_Code() {
        return coupon_Code;
    }

    public String getCoupon_ID() {
        return coupon_ID;
    }
}
