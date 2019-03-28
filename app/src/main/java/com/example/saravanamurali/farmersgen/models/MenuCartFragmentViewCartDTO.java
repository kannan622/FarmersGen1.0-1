package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class MenuCartFragmentViewCartDTO {

    @SerializedName("id")
    String id;
    @SerializedName("brand_id")
    String mCart_Brand_ID;
    @SerializedName("brand_name")
    String mCart_Brand_Name;
    @SerializedName("product_code")
    String mCart_Product_Code;
    @SerializedName("product_name")
    String mCart_Product_Name;
    @SerializedName("product_image")
    String mCart_Product_Image;
    @SerializedName("price")
    String mCart_Price;
    @SerializedName("count")
    String mCart_Count;
    @SerializedName("total_price")
    String mCart_Total_price;
    @SerializedName("device_id")
    String mCart_Device_ID;

    public void setId(String id) {
        this.id = id;
    }

    public void setmCart_Brand_ID(String mCart_Brand_ID) {
        this.mCart_Brand_ID = mCart_Brand_ID;
    }

    public void setmCart_Brand_Name(String mCart_Brand_Name) {
        this.mCart_Brand_Name = mCart_Brand_Name;
    }

    public void setmCart_Product_Code(String mCart_Product_Code) {
        this.mCart_Product_Code = mCart_Product_Code;
    }

    public void setmCart_Product_Name(String mCart_Product_Name) {
        this.mCart_Product_Name = mCart_Product_Name;
    }

    public void setmCart_Product_Image(String mCart_Product_Image) {
        this.mCart_Product_Image = mCart_Product_Image;
    }

    public void setmCart_Price(String mCart_Price) {
        this.mCart_Price = mCart_Price;
    }

    public void setmCart_Count(String mCart_Count) {
        this.mCart_Count = mCart_Count;
    }

    public void setmCart_Total_price(String mCart_Total_price) {
        this.mCart_Total_price = mCart_Total_price;
    }

    public void setmCart_Device_ID(String mCart_Device_ID) {
        this.mCart_Device_ID = mCart_Device_ID;
    }

    public String getId() {
        return id;
    }

    public String getmCart_Brand_ID() {
        return mCart_Brand_ID;
    }

    public String getmCart_Brand_Name() {
        return mCart_Brand_Name;
    }

    public String getmCart_Product_Code() {
        return mCart_Product_Code;
    }

    public String getmCart_Product_Name() {
        return mCart_Product_Name;
    }

    public String getmCart_Product_Image() {
        return mCart_Product_Image;
    }

    public String getmCart_Price() {
        return mCart_Price;
    }

    public String getmCart_Count() {
        return mCart_Count;
    }

    public String getmCart_Total_price() {
        return mCart_Total_price;
    }

    public String getmCart_Device_ID() {
        return mCart_Device_ID;
    }
}
