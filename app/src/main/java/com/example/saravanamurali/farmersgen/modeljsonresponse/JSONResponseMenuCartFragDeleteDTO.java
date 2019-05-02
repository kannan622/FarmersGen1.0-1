package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseMenuCartFragDeleteDTO {
    @SerializedName("success")
    success deleteMenuCartSuccess;

    @SerializedName("grand_total")
    String deleteMenuCartGrandTotal;

    public success getDeleteMenuCartSuccess() {
        return deleteMenuCartSuccess;
    }

    public String getDeleteMenuCartGrandTotal() {
        return deleteMenuCartGrandTotal;
    }

    public class success {

        @SerializedName("responsecode")
        String responseCodeMenuCart;

        @SerializedName("message")
        String messageMenuCart;

        public String getResponseCodeMenuCart() {
            return responseCodeMenuCart;
        }

        public String getMessageMenuCart() {
            return messageMenuCart;
        }
    }

}
