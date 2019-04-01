package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class MenuBannerDTO {

    @SerializedName("")
    private String bannerImages;

    @SerializedName("")
    private String bannerCode;

    public String getBannerImages() {
        return bannerImages;
    }

    public String getBannerCode() {
        return bannerCode;
    }
}
