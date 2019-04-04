package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class BannerDTO {

    @SerializedName("banner_id")
    String bannerID;
    @SerializedName("banner_image")
    String banner_image;
    @SerializedName("banner_name")
    String banner_name;

    public BannerDTO(String bannerID, String banner_image, String banner_name) {
        this.bannerID = bannerID;
        this.banner_image = banner_image;
        this.banner_name = banner_name;
    }

    public String getBannerID() {
        return bannerID;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public String getBanner_name() {
        return banner_name;
    }
}
