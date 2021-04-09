package com.example.xcargomobile.cargo;

public class Receiver {

    private String Name;
    private String LastName;
    private String Address;
    private String District;
    private String Province;

    public Receiver(){
    }
    public Receiver(String name, String lastName, String address, String district, String province) {
        Name = name;
        LastName = lastName;
        Address = address;
        District = district;
        Province = province;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }
}
