package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseMenuCartFragUpdateDTO {

    @SerializedName("success")
    success menuCartUpdate_Success;

    @SerializedName("product_code")
    String mCartUpdate_ProductCode;

    @SerializedName("count")
    String mCartUpdate_Count;

    @SerializedName("total_price")
    String mCartUpdate_TotalPrice;

    @SerializedName("device_id")
    String mCartUpdate_Device_ID;

    @SerializedName("grand_total")
    String mCartUpdate_GrandTotal;

    public success getMenuCartUpdate_Success() {
        return menuCartUpdate_Success;
    }

    public String getmCartUpdate_ProductCode() {
        return mCartUpdate_ProductCode;
    }

    public String getmCartUpdate_Count() {
        return mCartUpdate_Count;
    }

    public String getmCartUpdate_TotalPrice() {
        return mCartUpdate_TotalPrice;
    }

    public String getmCartUpdate_Device_ID() {
        return mCartUpdate_Device_ID;
    }

    public String getmCartUpdate_GrandTotal() {
        return mCartUpdate_GrandTotal;
    }

    public class success {

        @SerializedName("responsecode")
        String mCartUpdateResponseCode;

        @SerializedName("message")
        String mCartUpdateMessage;

        public String getmCartUpdateResponseCode() {
            return mCartUpdateResponseCode;
        }

        public String getmCartUpdateMessage() {
            return mCartUpdateMessage;
        }
    }




}









