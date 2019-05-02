package com.example.saravanamurali.farmersgen.modeljsonresponse;

import com.google.gson.annotations.SerializedName;

public class JSONResponseForUpdateAddress {

    @SerializedName("flat_no")
    String upateFlatNo;
    @SerializedName("street_name")
    String streetName;
    @SerializedName("area")
    String area;
    @SerializedName("city")
    String city;
    @SerializedName("pincode")
    String pincode;
    @SerializedName("address_id")
    String address_id;
    @SerializedName("user_id")
    String user_id;

    @SerializedName("success")
            success success;



   /* {
        "success": {
        "response code": 200,
                "status": 1,
                "message": "Success"
    },
        "flat_no": "23A",
            "street_name": "V.O.C Street",
            "area": "Porur",
            "city": "Chennai",
            "pincode": "600055",
            "address_id": "ADDR1",
            "user_id": "USR1"
    }*/

   class success{
       @SerializedName("response Code")
       String status;

       public String getStatus() {
           return status;
       }
   }

}
