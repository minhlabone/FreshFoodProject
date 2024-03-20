package com.manager.freshfood.utils;

import com.manager.freshfood.model.GioHang;
import com.manager.freshfood.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL="http://192.168.139.1/banhang1/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static User user_current = new User();
    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String DATETIME = "datetime";
    public static final String MESS = "message";
    public static final String PATH_CHAT = "chat";
}
