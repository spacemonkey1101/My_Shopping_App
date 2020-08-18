package com.example.MyShoppingApp.model;

public class OtpPOJO {
    private String message;
    private String refresh;
    private String access;
    private int otp;

    public OtpPOJO(int otp) {
        this.otp = otp;
    }

    public String getMesage() {
        return message;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setMesage(String mesage) {
        this.message = mesage;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getAccess() {
        return access;
    }
}
