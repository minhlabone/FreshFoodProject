package com.manager.freshfood.model;

public class ThongKe {
    private  String tensp;
    private  int tong;
    private String tongtienthang;
    private String thang;
    private int soLuong;
    private  int trangthai;

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTongtienthang() {
        return tongtienthang;
    }

    public void setTongtienthang(String tongtienthang) {
        this.tongtienthang = tongtienthang;
    }

    public String getThang() {
        return thang;
    }

    public void setThang(String thang) {
        this.thang = thang;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }
}
