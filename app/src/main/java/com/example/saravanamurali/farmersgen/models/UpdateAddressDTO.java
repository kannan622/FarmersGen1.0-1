package com.example.saravanamurali.farmersgen.models;

import com.google.gson.annotations.SerializedName;

public class UpdateAddressDTO {

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
    @SerializedName("landmark")
    String landMark;
    @SerializedName("alternative_mobile_no")
    String alternateMobileNumber;
    @SerializedName("user_id")
    String user_id;


    public UpdateAddressDTO(String upateFlatNo, String streetName, String area, String city, String pincode, String address_id,String landmark,String alternateMobile_Number, String user_id) {
        this.upateFlatNo = upateFlatNo;
        this.streetName = streetName;
        this.area = area;
        this.city = city;
        this.pincode = pincode;
        this.address_id = address_id;
        this.landMark=landmark;
        this.alternateMobileNumber=alternateMobile_Number;
        this.user_id = user_id;
    }


    public String getLandMark() {
        return landMark;
    }

    public String getAlternateMobileNumber() {
        return alternateMobileNumber;
    }

    public String getUpateFlatNo() {
        return upateFlatNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getAddress_id() {
        return address_id;
    }

    public String getUser_id() {
        return user_id;
    }
}
