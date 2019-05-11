package com.example.saravanamurali.farmersgen.models;

public class GetDataFromSqlLiteDTO {

    String count;
    String productCode;

    public GetDataFromSqlLiteDTO(String count, String productCode) {
        this.count = count;
        this.productCode = productCode;
    }

    public String getCount() {
        return count;
    }

    public String getProductCode() {
        return productCode;
    }
}
