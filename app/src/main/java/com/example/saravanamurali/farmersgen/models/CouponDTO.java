package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class CouponDTO {

    @SerializedName("coupon_id")
    String couponId;
    @SerializedName("coupon_code")
    String couponCode;
    @SerializedName("coupon_description1")
    String desc1;
    @SerializedName("coupon_description2")
    String desc2;
    @SerializedName("coupon_image")
    String couponImage;
    @SerializedName("offer_price")
    String coupon_OffPrice;


    public CouponDTO(String couponId, String couponCode, String desc1, String desc2, String couponImage) {
        this.couponId = couponId;
        this.couponCode = couponCode;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.couponImage = couponImage;
    }

    public String getCoupon_OffPrice() {
        return coupon_OffPrice;
    }

    public String getCouponId() {
        return couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public String getDesc1() {
        return desc1;
    }

    public String getDesc2() {
        return desc2;
    }

    public String getCouponImage() {
        return couponImage;
    }
}
