package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class HomeProductDTO {

    @SerializedName("brand_id")
    String brandId;

    @SerializedName("brand_logo")
    String productImage;
    @SerializedName("brand_name")
    String productName;
    @SerializedName("short_description")
    String productDesc;
    @SerializedName("rating")
    String productRating;

    @SerializedName("banner_image")
    String bannerImage;

    String productMinOrder;


    int recyclerVPosition;


    public HomeProductDTO(String id, String productImage, String productName, String productDesc, String productRating, String minOrder) {
        this.brandId = id;
        this.productImage = productImage;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productRating = productRating;
        this.productMinOrder=minOrder;
    }

    public String getBannerImage() {
        return bannerImage;
    }


    public void setRecyclerVPosition(int recyclerVPosition) {
        this.recyclerVPosition = recyclerVPosition;
    }

    public int getRecyclerVPosition() {
        return recyclerVPosition;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public String getProductMinOrder() {
        return productMinOrder;
    }

    public String getProductRating() {
        return productRating;
    }
}
