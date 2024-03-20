package com.example.freshfood.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.freshfood.R;
import com.example.freshfood.adapter.GioHangAdapter;
import com.example.freshfood.model.EventBus.TinhTongEvent;
import com.example.freshfood.model.GioHang;
import com.example.freshfood.zalo.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
     TextView giohangtrong,tongtien;
     Toolbar toolbar;
     RecyclerView recyclerView;
     Button muahang;
     GioHangAdapter adapter;
     ImageView vetrangchu;
     long tongtiensp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        initView();
        initControl();
        // nếu khác null thì xóa data để khi tính tổng tiền nó cộng lại
        if(Utils.mangmuahang != null){
            Utils.mangmuahang.clear();
        }
        tinhTongTien();
    }

    private void tinhTongTien() {
         tongtiensp =0;
        for(int i=0; i<Utils.mangmuahang.size();i++){
            tongtiensp = tongtiensp + (Utils.mangmuahang.get(i).getGiasp()*Utils.mangmuahang.get(i).getSoluong());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongtiensp));
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(Utils.manggiohang.size() == 0){
            giohangtrong.setVisibility(View.VISIBLE);
        }else{
            adapter = new GioHangAdapter(getApplicationContext(),Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }
        // chuyển sang thanh toán
        muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // người dùng free cần đăng nhập
                if (Utils.user_current.getId() ==0){
                    showDialogNoti();
                }else {
                    Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                    intent.putExtra("tongtien",tongtiensp);
                    Utils.mangthanhtoan = new ArrayList<>();
                    // add giữ liệu cho mảng thanh toán để hiển thị các sản phẩm được chọn qua bên thanh toán
                    for (GioHang giohang:
                            Utils.manggiohang ) {
                        if(giohang.isChecked()){
                            Utils.mangthanhtoan.add(giohang);
                        }
                    }
//                Utils.manggiohang.clear();
                    startActivity(intent);
                }
            }
        });
        vetrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        vetrangchu = findViewById(R.id.homegiohang);
        giohangtrong = findViewById(R.id.txtgiohangtrong);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleviewgiohang);
        tongtien = findViewById(R.id.txttongtien);
        muahang = findViewById(R.id.btnmuahang);
    }


    public void  showDialogNoti(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
        builder.setTitle("Để dùng chức năng này bạn cần đăng nhập");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent dn = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(dn);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
    // tinh toán cập nhập tổng tiền trong giohang
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event){
        // nếu có sự kiện click thì ta chạy lại hàm tinhtongtien để cập nhật tổng tiền mới
        if(event!=null){
            tinhTongTien();
        }
    }
}