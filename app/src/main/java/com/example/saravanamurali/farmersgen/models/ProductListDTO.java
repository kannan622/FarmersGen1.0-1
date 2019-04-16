package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class ProductListDTO {

    @SerializedName("product_name")
    private String productName;
    @SerializedName("product_image")
    private String productImage;
    @SerializedName("product_price")
    private String productPrice;
    @SerializedName("product_code")
    private String productCode;
    @SerializedName("count")
    private String count;

    @SerializedName("product_quantity")
    private String productQuantity;

    @SerializedName("actual_price")
    private String acutalPrice;


    int countFromHomeMenuFragment;

    //ViewCart

    public ProductListDTO(String productCode, String count, String productPrice, String productName) {

        this.productCode = productCode;
        this.count = count;
        this.productPrice = productPrice;
        this.productName = productName;

    }


    public ProductListDTO(String productCode, String count,  String productName, String productImage, String productPrice,String productQuantity,String auctalPrice) {
        this.productCode = productCode;
        this.count=count;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productQuantity=productQuantity;
        this.acutalPrice=auctalPrice;
    }

    public String getProductCode() {
        return productCode;
    }



    public void setCountFromHomeMenuFragment(int countFromHomeMenuFragment) {
        this.countFromHomeMenuFragment = countFromHomeMenuFragment;
    }

    public int getCountFromHomeMenuFragment() {
        return countFromHomeMenuFragment;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCount() {
        return count;
    }


    public String getProductName() {
        return productName;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public String getAcutalPrice() {
        return acutalPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public String  getProductPrice() {
        return productPrice;
    }
}
