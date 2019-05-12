package com.example.saravanamurali.farmersgen.models;


import com.google.gson.annotations.SerializedName;

public class GetDataFromSqlLiteDTO {
    @SerializedName("product_code")
    String product_code;

    @SerializedName("count")
    String count;
    @SerializedName("total_price")
    String total_price;
    @SerializedName("device_id")
    String device_ID;


    String productCode;



    public GetDataFromSqlLiteDTO(String productCode,String count,String total_price, String device_ID) {
        this.count = count;
        this.productCode = productCode;
        this.total_price = total_price;

        this.device_ID = device_ID;

    }

    public String getCount() {
        return count;
    }

    public String getProductCode() {
        return productCode;
    }




      public String getTotal_price() {
        return total_price;
    }

    public String getDevice_ID() {
        return device_ID;
    }


    public void setcount(String count) {
        this.count = count;
    }
    public void setproduct_code(String product_code) {
        this.product_code = product_code;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
    public void setDevice_ID(String device_ID) {
        this.device_ID = device_ID;
    }

}
