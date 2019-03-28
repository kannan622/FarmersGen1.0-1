package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ViewCartDTO {

    @SerializedName("id")
    String id;
    @SerializedName("brand_id")
    String brand_ID;
    @SerializedName("brand_name")
    String brand_Name;
    @SerializedName("product_code")
    String product_Code;
    @SerializedName("product_name")
    String Product_Name;
    @SerializedName("product_image")
    String product_Image;
    @SerializedName("price")
    String price;
    @SerializedName("count")
    String count;
    @SerializedName("total_price")
    String total_price;
    @SerializedName("device_id")
    String device_ID;

    public void setId(String id) {
        this.id = id;
    }

    public void setBrand_ID(String brand_ID) {
        this.brand_ID = brand_ID;
    }

    public void setBrand_Name(String brand_Name) {
        this.brand_Name = brand_Name;
    }

    public void setProduct_Code(String product_Code) {
        this.product_Code = product_Code;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public void setDevice_ID(String device_ID) {
        this.device_ID = device_ID;
    }

    public String getId() {
        return id;
    }

    public String getBrand_ID() {
        return brand_ID;
    }

    public String getBrand_Name() {
        return brand_Name;
    }

    public String getProduct_Code() {
        return product_Code;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public String getProduct_Image() {
        return product_Image;
    }

    public String getPrice() {
        return price;
    }

    public String getCount() {
        return count;
    }

    public String getTotal_price() {
        return total_price;
    }

    public String getDevice_ID() {
        return device_ID;
    }
}
