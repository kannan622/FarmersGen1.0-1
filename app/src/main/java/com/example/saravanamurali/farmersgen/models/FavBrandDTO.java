package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class FavBrandDTO {

    @SerializedName("favorite_status")
    String fav_status;
    @SerializedName("user_id")
    String user_id;
    @SerializedName("brand_id")
    String fav_brandId;
    @SerializedName("brand_logo")
    String fav_productImage;
    @SerializedName("brand_name")
    String fav_productName;
    @SerializedName("short_description")
    String fav_productDesc;
    @SerializedName("rating")
    String fav_productRating;

    public FavBrandDTO(String fav_status, String user_id, String fav_brandId, String fav_productImage, String fav_productName, String fav_productDesc, String fav_productRating) {
        this.fav_status = fav_status;
        this.user_id = user_id;
        this.fav_brandId = fav_brandId;
        this.fav_productImage = fav_productImage;
        this.fav_productName = fav_productName;
        this.fav_productDesc = fav_productDesc;
        this.fav_productRating = fav_productRating;
    }

    public String getFav_status() {
        return fav_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getFav_brandId() {
        return fav_brandId;
    }

    public String getFav_productImage() {
        return fav_productImage;
    }

    public String getFav_productName() {
        return fav_productName;
    }

    public String getFav_productDesc() {
        return fav_productDesc;
    }

    public String getFav_productRating() {
        return fav_productRating;
    }
}


