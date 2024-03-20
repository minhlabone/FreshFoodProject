package com.manager.freshfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.manager.freshfood.Fragment.FragmentContact;
import com.manager.freshfood.Fragment.FragmentProfile;
import com.manager.freshfood.R;
import com.manager.freshfood.adapter.LoaiSpAdapter;
import com.manager.freshfood.adapter.SanPhamMoiAdapter;
import com.manager.freshfood.model.LoaiSp;
import com.manager.freshfood.model.SanPhamMoi;
import com.manager.freshfood.retrofit.ApiBanhang;
import com.manager.freshfood.retrofit.RetrofitClient;
import com.manager.freshfood.utils.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    NavigationView navigationView;
    RecyclerView recyclerViewmanhinhchinh;
    ListView listviewmanhinhchinh;
    DrawerLayout drawerLayout;
    LoaiSpAdapter loaiSpAdapter;
    List<LoaiSp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanhang apiBanhang;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;
    ImageView imguser,imglive,imgcontact,imgchat;
    ImageView imgsearch;
    LinearLayout linearLayoutview;
    FloatingActionButton floatingActionButton;
    TextView txttitlelienhe,txttitlehome,txthello,txttitle;
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiBanhang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanhang.class);
        getToken();
        AnhXa();
        ActionBar();
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.noel);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        if(isConnected(this)){
            ActionViewFlipper();
//            getLoaiSanPham();
            getSpMoi();
            getEventClick();
        }else {
            Toast.makeText(getApplicationContext(), "Không Có Internet, Vui Lòng Kết Nối Internet", Toast.LENGTH_SHORT).show();
        }
    }
    // lấy token để gửi thông báo
    private  void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if(!TextUtils.isEmpty(s)){
                             compositeDisposable.add(apiBanhang.updateToken(Utils.user_current.getId(),s)
                                     .subscribeOn(Schedulers.io())
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .subscribe(
                                            messageModel -> {

                                            },
                                             throwable -> {
                                                 Log.d("log",throwable.getMessage());
                                             }
                                     )
                             );
                        }
                    }
                });
    }

    private void getEventClick() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                txttitlehome.setTextColor(Color.rgb(15,187,0));
                txttitlelienhe.setTextColor(Color.rgb(103,103,103));
                startActivity(intent);
            }
        });
//        listviewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i){
//                    case 0:
//                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(trangchu);
//                        break;
//                    case 1:
//                        Intent raucu = new Intent(getApplicationContext(), RauCuActivity.class);
//                        raucu.putExtra("loai",1);
//                        startActivity(raucu);
//                        break;
//                    case 2:
//                        Intent thitcatrung = new Intent(getApplicationContext(), RauCuActivity.class);
//                        thitcatrung.putExtra("loai",2);
//                        startActivity(thitcatrung);
//                        break;
//                    case 3:
//                        Intent traicay = new Intent(getApplicationContext(), RauCuActivity.class);
//                        traicay.putExtra("loai",3);
//                        startActivity(traicay);
//                        break;
//                    case 4:
//                        Intent donhang = new Intent(getApplicationContext(), XemDonActivity.class);
//                        startActivity(donhang);
//                        break;
//                }
//            }
//        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                       break;
                    case R.id.nav_rau:
                        Intent raucu = new Intent(getApplicationContext(), RauCuActivity.class);
                        raucu.putExtra("loai",1);
                        startActivity(raucu);
                        break;
                    case R.id.nav_thit:
                        Intent thitcatrung = new Intent(getApplicationContext(), RauCuActivity.class);
                        thitcatrung.putExtra("loai",2);
                        startActivity(thitcatrung);
                        break;
                    case R.id.nav_qua:
                        Intent traicay = new Intent(getApplicationContext(), RauCuActivity.class);
                        traicay.putExtra("loai",3);
                        startActivity(traicay);
                        break;
                    case R.id.nav_donhang:
                        Intent donhang = new Intent(getApplicationContext(), XemDonActivity.class);
                        startActivity(donhang);
                        break;
                    case  R.id.nav_quanli:
                        Intent quanli = new Intent(getApplicationContext(),QuanLiActivity.class);
                        startActivity(quanli);
                        break;
                    case R.id.nav_thongke:
                        Intent thongke = new Intent(getApplicationContext(),ThongKeActivity.class);
                        startActivity(thongke);
                        break;
                    case R.id.nav_dangxuat:
                        FirebaseAuth.getInstance().signOut();
                        Intent dangnhap = new Intent(getApplicationContext(),DangNhapActivity.class);
                        startActivity(dangnhap);
                        break;
                }
                return true;
            }
        });
        navigationView.setItemIconTintList(null);
        imguser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                linearLayoutview.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), XemDonActivity.class);
                startActivity(intent);
            }
        });
        imgcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutview.setVisibility(View.GONE);
                txttitlelienhe.setTextColor(Color.rgb(15,187,0));
                txttitlehome.setTextColor(Color.rgb(103,103,103));
                replaceFragment(new FragmentContact());
            }
        });
//        imglive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 linearLayoutview.setVisibility(View.GONE);
//                replaceFragment(new FragmentContact());
//            }
//        });
        imgchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getApplicationContext(),UserActivity.class);
                startActivity(chat);
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentlayout,fragment);
        fragmentTransaction.commit();
    }
    private void getSpMoi() {
        compositeDisposable.add(apiBanhang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                mangSpMoi = sanPhamMoiModel.getResult();
                                spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpMoi);
                                recyclerViewmanhinhchinh.setAdapter(spAdapter);

                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Không kết nối được với server"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

//    private void getLoaiSanPham() {
//        compositeDisposable.add(apiBanhang.getLoaiSp()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        loaiSpModel -> {
//                            if(loaiSpModel.isSuccess()){
//                                mangloaisp = loaiSpModel.getResult();
//                                loaiSpAdapter = new LoaiSpAdapter(getApplicationContext(), mangloaisp);
//                                listviewmanhinhchinh.setAdapter(loaiSpAdapter);
//                            }
//                        }
//                )
//        );
//    }

    private void ActionViewFlipper() {
        List<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://topmeal.com.vn/wp-content/uploads/2018/01/banner-dt-896-x-448-px.png?fbclid=IwAR0V-YVDYwvt-Texe7CVL_YSDywgM33mO36HCcelIADFTvpdTUtPFen7C3M");
        mangquangcao.add("https://sofatoanquoc.com/images/companies/1/untitled%20folder/sofa-banner-mua-thu-sofatoanquoc.jpg?1604309104295&fbclid=IwAR0lXg8HvOEr2WppxG0uuey3K8Y-ZrISrwU4C1oEjwpw1Odcp2xD84Tyhl8");
        mangquangcao.add("https://ihappy.vn/public/upload/cover.png?fbclid=IwAR1138CJZgXNt78K7ZcoggKmjxjLL3PVpUNdh3VtlzNbAxRkFBXM_KdL4ms");
        for(int i=0;  i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setAnimation(slide_in);
        viewFlipper.setOutAnimation(slide_out);
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

    private  void AnhXa(){
        videoView = findViewById(R.id.videoview);
        txthello = findViewById(R.id.textViewhello);
        txttitle = findViewById(R.id.textviewTItle);
        imgchat = findViewById(R.id.imageviewchat);
        linearLayoutview = findViewById(R.id.linearlayoutview);
        //anh xa
        floatingActionButton = findViewById(R.id.trangchu);
        imglive = findViewById(R.id.imageviewlive);
        imgcontact = findViewById(R.id.imageviewlienhe);
        txttitlelienhe = findViewById(R.id.textviewlienhe);
        txttitlehome = findViewById(R.id.textviewhometrangchu);
        imgsearch = findViewById(R.id.imgsearch);
        imguser = findViewById(R.id.imageviewuser);
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper =  findViewById(R.id.viewflipper);
        navigationView =  findViewById(R.id.navigationview);
        recyclerViewmanhinhchinh =  findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewmanhinhchinh.setLayoutManager(layoutManager);
        recyclerViewmanhinhchinh.setHasFixedSize(true);
//        listviewmanhinhchinh =  findViewById(R.id.listviewmanhinhchinh);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        //khoi tao list
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else{
            int totalItem = 0;
            for(int i =0; i<Utils.manggiohang.size();i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
      frameLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent giohang = new Intent(getApplicationContext(),GioHangActivity.class);
              startActivity(giohang);
          }
      });
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
              startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();
        int totalItem = 0;
        for(int i =0; i<Utils.manggiohang.size();i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }

    // kiểm tra kết nói internet
    private  boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(wifi !=null && wifi.isConnected() || (mobile !=null && mobile.isConnected())){
            return  true;
        }else {
            return  false;
        }
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        videoView.start();
        super.onRestart();
    }
}