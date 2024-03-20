package com.example.freshfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.example.freshfood.R;
import com.example.freshfood.adapter.RauCuAdapter;
import com.example.freshfood.model.SanPhamMoi;
import com.example.freshfood.retrofit.ApiBanhang;
import com.example.freshfood.retrofit.RetrofitClient;
import com.example.freshfood.zalo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RauCuActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiBanhang apiBanhang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page =1;
    int loai;
    RauCuAdapter adapterRc;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rau_cu);
        apiBanhang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanhang.class);
        loai = getIntent().getIntExtra("loai",1);
        AnhXa();
        ActionToolBar();
        getData(page);
        addEventLoad();

        ActionBar a = getSupportActionBar();
        if(loai == 2){
            a.setTitle("Thịt Cá Trứng");
        }
        if(loai == 3){
            a.setTitle("Trái Cây");
        }
    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isLoading == false){
                    if(linearLayoutManager.findLastCompletelyVisibleItemPosition()== sanPhamMoiList.size()-1){
                        isLoading =true;
                        loadMore();
                    }
                }
            }
        });
    }
     private  void loadMore(){
        handler.post(new Runnable() {
            @Override
            public void run() {
             sanPhamMoiList.add(null);
             adapterRc.notifyItemInserted(sanPhamMoiList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                adapterRc.notifyItemRemoved(sanPhamMoiList.size());
                page = page+1;
                getData(page);
                adapterRc.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
     }
    private void getData(int page) {
        compositeDisposable.add(apiBanhang.getSanPham(page,loai)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                          if(sanPhamMoiModel.isSuccess()){
                              if(adapterRc == null){
                                  sanPhamMoiList = sanPhamMoiModel.getResult();
                                  adapterRc  = new RauCuAdapter(getApplicationContext(),sanPhamMoiList);
                                  recyclerView.setAdapter(adapterRc);
                              }else {
                                  int vitri = sanPhamMoiList.size()-1;
                                  int soluongadd = sanPhamMoiModel.getResult().size();
                                  for(int i=0; i<soluongadd;i++){
                                      sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                                  }
                                  adapterRc.notifyItemRangeInserted(vitri,soluongadd);
                              }
                          }else {
                              Toast.makeText(getApplicationContext(), "Hết Dữ liệu rồi", Toast.LENGTH_SHORT).show();
                              isLoading = true;
                          }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối server", Toast.LENGTH_SHORT).show();
                        }
                )
        );
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

    private void AnhXa() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycleviewraucu);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }
}