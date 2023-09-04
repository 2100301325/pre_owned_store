package com.example.ggapplication.data;


public class SellResponseModel {
    private int code;
    private String msg;

    public SellData getData() {
        return data;
    }

    public void setData(SellData data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private SellData data;

    // Getter and Setter methods for code, msg, and data
}
