package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseDeleteCartDTO {
    @SerializedName("success")
    success deleteSuccess;

    @SerializedName("grand_total")
    String deleteGrandTotal;

    public success getDeleteSuccess() {
        return deleteSuccess;
    }

    public String getDeleteGrandTotal() {
        return deleteGrandTotal;
    }

    public class success {

        @SerializedName("responsecode")
        String responseCode;

        @SerializedName("message")
        String message;

        public String getResponseCode() {
            return responseCode;
        }

        public String getMessage() {
            return message;
        }
    }

}
