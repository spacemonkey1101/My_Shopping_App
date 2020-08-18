package com.example.MyShoppingApp.model;

public class LocationPOJO {
    private double latitude;

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    private double longitude;
    private String current_address;

    public LocationPOJO(double latitude, double longitude,String current_address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.current_address = current_address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
