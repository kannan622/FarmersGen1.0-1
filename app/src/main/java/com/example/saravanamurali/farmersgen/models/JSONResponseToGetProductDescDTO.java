package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class JSONResponseToGetProductDescDTO {

    @SerializedName("product_image")
    private String productImage;
    @SerializedName("brand_name")
    private String brandName;
    @SerializedName("product_name")
    private String productName;

    @SerializedName("productquantity")
    private String productQuantity;

    @SerializedName("actualprice")
    private String productActualPrice;

    @SerializedName("offerprice")
    private String productPrice;

    @SerializedName("rating")
    private String proudctRating;
    @SerializedName("productandpack")
    private String productAndPackagingText;
    @SerializedName("ingredients_used")
    private String ingredientUsed;
    @SerializedName("usage_benefits")
    private String usage_benefits;
    @SerializedName("fbLink")
    private String fbLink;
    @SerializedName("instaLink")
    private String instaLink;
    @SerializedName("youtubeLink")
    private String youtubeLink;

    public JSONResponseToGetProductDescDTO(String productImage, String brandName, String productName, String productQuantity, String productActualPrice, String productPrice, String proudctRating, String productAndPackagingText, String ingredientUsed, String usage_benefits, String fbLink, String instaLink, String youtubeLink) {
        this.productImage = productImage;
        this.brandName = brandName;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productActualPrice = productActualPrice;
        this.productPrice = productPrice;
        this.proudctRating = proudctRating;
        this.productAndPackagingText = productAndPackagingText;
        this.ingredientUsed = ingredientUsed;
        this.usage_benefits = usage_benefits;
        this.fbLink = fbLink;
        this.instaLink = instaLink;
        this.youtubeLink = youtubeLink;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public String getProductActualPrice() {
        return productActualPrice;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProudctRating() {
        return proudctRating;
    }

    public String getProductAndPackagingText() {
        return productAndPackagingText;
    }

    public String getIngredientUsed() {
        return ingredientUsed;
    }

    public String getUsage_benefits() {
        return usage_benefits;
    }

    public String getFbLink() {
        return fbLink;
    }

    public String getInstaLink() {
        return instaLink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }
}
