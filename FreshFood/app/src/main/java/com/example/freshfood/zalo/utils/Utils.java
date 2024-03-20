package com.example.freshfood.zalo.utils;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.example.freshfood.R;
import com.example.freshfood.model.GioHang;
import com.example.freshfood.model.User;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL="http://192.168.139.1/banhang1/";
    public static List<GioHang> manggiohang;
    public static List<GioHang> mangmuahang = new ArrayList<>();
    public static  List<GioHang> mangthanhtoan;

    public static User user_current = new User();
    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String DATETIME = "datetime";
    public static final String MESS = "message";
    public static final String PATH_CHAT = "chat";
    public static String statusOrder(int status){
        String result = "";
        switch (status){
            case 0:
                result = "Đơn hàng đang xử lí";
                break;
            case 1:
                result = "Được tiếp nhận";
                break;
            case 2:
                result = "Đã Giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Giao Thành công";
                break;
            case 4:
                result = "Đơn hàng đã Hủy";
                break;
            default:
                result ="....";
        }
        return result;
    }

}
