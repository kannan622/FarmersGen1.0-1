package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseForDeleteCartDTO {

    @SerializedName("responsecode")
    int deleteResponseCode;
    @SerializedName("message")
    String message;
    @SerializedName("grand_total")
    String grandTotal;

    public int getDeleteResponseCode() {
        return deleteResponseCode;
    }

    public String getMessage() {
        return message;
    }

    public String getGrandTotal() {
        return grandTotal;
    }
}
