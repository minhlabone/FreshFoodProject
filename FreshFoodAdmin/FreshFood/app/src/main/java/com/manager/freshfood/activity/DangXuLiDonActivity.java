package com.manager.freshfood.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

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

public class DangXuLiDonActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanhang apiBanHang;
    CompositeDisposable compositeDisposable  = new CompositeDisposable();
    int page =1;
    int trangthai;
    int tinhtrang;
    DonHang donHang;
    AlertDialog dialog;
    DonHangAdapter adapter;
    List<DonHang> donHangList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_xu_li_don);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanhang.class);
        trangthai = getIntent().getIntExtra("trangthai",0);
        AnhXa();
        ActionToolBar();
        getData();
        ActionBar a = getSupportActionBar();
        if(trangthai == 1){
            a.setTitle("Đơn hàng đã chấp nhận");
        }
        if(trangthai == 2){
            a.setTitle("Đơn hàng đã giao cho đơn vị vận chuyển");
        }
        if(trangthai == 3){
            a.setTitle("Đơn hàng giao Thành Công");
        }
        if(trangthai == 4){
            a.setTitle("Đơn hàng đã hủy");
        }
    }
 // phân loại tình trạng đơn hàng
    private void getData() {
        compositeDisposable.add(apiBanHang.getDonhang(0,page,trangthai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                              if(donHangModel.isSuccess()){
                                  donHangList = donHangModel.getResult();
                                  adapter = new DonHangAdapter(getApplicationContext(),donHangList);
                                  recyclerView.setAdapter(adapter);
                              }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Khong ket noi", Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }


    private void AnhXa() {
           toolbar = findViewById(R.id.toolbar);
           recyclerView = findViewById(R.id.recycleview_donhangxuli);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        donHangList = new ArrayList<>();
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    public void evenDonHang(DonHangEvent event){
        if(event != null){
            donHang = event.getDonHang();
            showCustumDialog();
        }
    }

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
                            getData();
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