package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class DeleteCountInCartDTO {

    @SerializedName("product_code")
    String deleteProductCode;

    @SerializedName("device_id")
    String delete_deviceID;

    public DeleteCountInCartDTO(String delete_deviceID,String deleteProductCode) {
        this.delete_deviceID=delete_deviceID;
        this.deleteProductCode=deleteProductCode;
         }

    public String getDeleteProductCode() {
        return deleteProductCode;
    }

    public String getDelete_deviceID() {
        return delete_deviceID;
    }
}
