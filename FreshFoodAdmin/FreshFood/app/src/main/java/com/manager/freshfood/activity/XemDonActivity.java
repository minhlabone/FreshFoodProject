package com.manager.freshfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.manager.freshfood.R;
import com.manager.freshfood.adapter.DonHangAdapter;
import com.manager.freshfood.model.DonHang;
import com.manager.freshfood.model.EventBus.DonHangEvent;
import com.manager.freshfood.model.NotiSendData;
import com.manager.freshfood.retrofit.ApiBanhang;
import com.manager.freshfood.retrofit.ApiPushNofication;
import com.manager.freshfood.retrofit.RetrofitClient;
import com.manager.freshfood.retrofit.RetrofitClientNoti;
import com.manager.freshfood.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemDonActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanhang apiBanHang;
    RecyclerView redonhang;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    DonHang donHang;
    int tinhtrang;
    AlertDialog dialog;
    ImageView home;
//    int page = 1;
//    int trangthai =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don);
//        trangthai = getIntent().getIntExtra("trangthai",0);
        initView();
        initToolbar();
        getOrder();
        getEventClick();
        ActionBar();
    }

    private void getEventClick() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_trangchudonhang:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case R.id.dangxuli:
                        Intent dangxuli = new Intent(getApplicationContext(), DangXuLiDonActivity.class);
                        // gửi qua dangxulidon để phân loại
                        dangxuli.putExtra("trangthai",0);
                        startActivity(dangxuli);
                        break;
                    case R.id.chapnhan:
                        Intent chapnhan = new Intent(getApplicationContext(), DangXuLiDonActivity.class);
                        chapnhan.putExtra("trangthai",1);
                        startActivity(chapnhan);
                        break;
                    case R.id.vanchuyen:
                        Intent vanchuyen = new Intent(getApplicationContext(), DangXuLiDonActivity.class);
                        vanchuyen.putExtra("trangthai",2);
                        startActivity(vanchuyen);
                        break;
                    case R.id.thanhcong:
                        Intent thanhcong = new Intent(getApplicationContext(), DangXuLiDonActivity.class);
                        thanhcong.putExtra("trangthai",3);
                        startActivity(thanhcong);
                        break;
                    case  R.id.dahuy:
                        Intent dahuy = new Intent(getApplicationContext(),DangXuLiDonActivity.class);
                        dahuy.putExtra("trangthai",4);
                        startActivity(dahuy);
                        break;
//                    case R.id.nav_dangxuat:
//                        FirebaseAuth.getInstance().signOut();
//                        Intent dangnhap = new Intent(getApplicationContext(),DangNhapActivity.class);
//                        startActivity(dangnhap);
//                        break;
                }
                return true;
            }
        });
        navigationView.setItemIconTintList(null);
    }

    private void getOrder() {
        compositeDisposable.add(apiBanHang.xemDonHang(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            DonHangAdapter adapter = new DonHangAdapter(getApplicationContext(),donHangModel.getResult());
                            redonhang.setAdapter(adapter);
                        },
                        throwable -> {

                        }
                )
        );

    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanhang.class);
        redonhang = findViewById(R.id.recycleview_donhang);
        home = findViewById(R.id.homexemdon);
        navigationView = findViewById(R.id.navigationviewdonhang);
        drawerLayout = findViewById(R.id.drawerlayoutdonhang);
        toolbar = findViewById(R.id.toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        redonhang.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
   @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
   public void evenDonHang(DonHangEvent event){
        if(event != null){
            //chạy lại đơn hàng
         donHang = event.getDonHang();
         showCustumDialog();
        }
   }
  // hiện spinner list các tình trạng để chọn
    private void showCustumDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_donhang, null);
        Spinner spinner = view.findViewById(R.id.spinner_dialog);
        AppCompatButton btndongy = view.findViewById(R.id.dongy_dialog);
        List<String> list = new ArrayList<>();
        list.add("Đơn hàng đang được xử lí");
        list.add("Đơn hàng đã chấp nhận");
        list.add("Đã giao cho đơn vị vận chuyển");
        list.add("Thành Công");
        list.add("Đơn hàng đã hủy");
        // tạo adpter hiển thị danh sách chuỗi trong spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner.setSelection(donHang.getTrangthai());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              tinhtrang =i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capNhapDonHang();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void capNhapDonHang() {
        // gửi tình trạng lên database
        compositeDisposable.add(apiBanHang.updateOrder(donHang.getId(),tinhtrang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                             getOrder();
                             dialog.dismiss();
                            pushNotiToUser();
                        },
                        throwable -> {

                        }
                )
        );
    }

    private void pushNotiToUser() {
        //gettokenn
        compositeDisposable.add(apiBanHang.gettoken(0,donHang.getIduser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                for(int i=0; i<userModel.getResult().size();i++){
                                    Map<String, String> data = new HashMap<>();
                                    data.put("title", "Thong bao");
                                    data.put("body", trangThaiDon(tinhtrang));
                                    NotiSendData notiSendData = new NotiSendData(userModel.getResult().get(i).getToken(), data);
                                    ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNofication.class);
                                    compositeDisposable.add(apiPushNofication.sendNotification(notiSendData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    notiResponse -> {
                                                    },
                                                    throwable -> {
                                                        Log.d("logg",throwable.getMessage());
                                                    }
                                            )
                                    );
                                }

                            }
                        },
                        throwable -> {
                            Log.d("loggg",throwable.getMessage());
                        }
                )
        );
    }
    private  String trangThaiDon(int status){
        String result="";
        switch(status){
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Thành Công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }
        return  result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}