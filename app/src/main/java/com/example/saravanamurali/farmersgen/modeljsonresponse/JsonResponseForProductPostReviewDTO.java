package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JsonResponseForProductPostReviewDTO {
    @SerializedName("status")
    int statusCode;

    @SerializedName("message")
    String message;

    public JsonResponseForProductPostReviewDTO(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
