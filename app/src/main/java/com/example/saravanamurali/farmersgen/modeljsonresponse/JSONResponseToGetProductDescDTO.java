package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class JSONResponseToGetProductDescDTO {


    @SerializedName("product_name")
    private String productName;
    @SerializedName("brand_name")
    private String brandName;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("youtube_link")
    private String youtubeLink;
    @SerializedName("product_price")
    private String productPrice;
    @SerializedName("product_quantity")
    private String productQuantity;
    @SerializedName("actual_price")
    private String productActualPrice;
    @SerializedName("rating")
    private String proudctRating;

    @SerializedName("manufacturing_date")
    private String productManuDate;

    @SerializedName("expiry_date")
    private String productExpairyDate;

    @SerializedName("product_and_packaging")
    private String productAndPackagingText;
    @SerializedName("ingredients")
    private String ingredientUsed;

    @SerializedName("usage_and_benefits")
    private String usage_benefits;

    @SerializedName("facebook")
    private String fbLink;
    @SerializedName("instagram")
    private String instaLink;

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

    public String getProductManuDate() {
        return productManuDate;
    }

    public String getProductExpairyDate() {
        return productExpairyDate;
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
