package com.example.e_commerce.my_addresses;

public class MyAddressesModel {

    private String name;
    private String mobileNo;
    private String address;
    private String pincode;
    private boolean selected;



    public MyAddressesModel(String name, String address, String pincode, boolean selected, String mobileNo) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.selected=selected;
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
