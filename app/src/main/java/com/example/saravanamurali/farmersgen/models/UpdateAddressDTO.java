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
    @SerializedName("user_id")
    String user_id;


    public UpdateAddressDTO(String upateFlatNo, String streetName, String area, String city, String pincode, String address_id, String user_id) {
        this.upateFlatNo = upateFlatNo;
        this.streetName = streetName;
        this.area = area;
        this.city = city;
        this.pincode = pincode;
        this.address_id = address_id;
        this.user_id = user_id;
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
