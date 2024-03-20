package com.example.freshfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.content.DialogInterface;
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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.freshfood.Fragment.FragmentContact;
import com.example.freshfood.Fragment.FragmentProfile;
import com.example.freshfood.R;
import com.example.freshfood.adapter.LoaiSpAdapter;
import com.example.freshfood.adapter.SanPhamMoiAdapter;
import com.example.freshfood.model.LoaiSp;
import com.example.freshfood.model.SanPhamMoi;
import com.example.freshfood.retrofit.ApiBanhang;
import com.example.freshfood.retrofit.RetrofitClient;
import com.example.freshfood.zalo.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
//import kotlinx.coroutines.debug.internal.AgentInstallationType;

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
    ImageView imguser,imglive,imgcontact,imageMess;
    ImageView imgsearch;
    RoundedImageView imgavt;
    LinearLayout linearLayoutview;
    FloatingActionButton floatingActionButton;
    TextView txttitlelienhe,txttitleprofile,txttitlehome,txthello,txttitle;
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
            getUser();
        }else {
            Toast.makeText(getApplicationContext(), "Không Có Internet, Vui Lòng Kết Nối Internet", Toast.LENGTH_SHORT).show();
        }
    }
    // hiển thị lời chào và avt user
    private void getUser(){
        if (Utils.user_current.getId() ==0){
            txthello.setText("Xin chào khách hàng");
            txttitle.setText("Đăng nhập để trải nghiệm");
        }else {
           txttitle.setText("Hãy trải nghiệm và mua sắm");
           txthello.setText("Xin chào "+Utils.user_current.getUsername());
            if (Utils.user_current.getImageprofile() == null){

            }else {
                Log.d("loogggg", "onActivityResult: co");
                String randomParameter = "?random=" + System.currentTimeMillis();
                Glide.with(getApplicationContext()).load(Utils.BASE_URL+"images/"+Utils.user_current.getImageprofile()+randomParameter).into(imgavt);
            }
        }
    }
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
        // lấy id status của admin để gửi tin nhắn
        compositeDisposable.add(apiBanhang.gettoken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                             if(userModel.isSuccess()){
                                 // lưu vào Utils
                                Utils.ID_RECEIVED = String.valueOf(userModel.getResult().get(0).getId());
                             }
                        },
                        throwable -> {

                        }
                )
        );
    }
    private void getEventClick() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                imguser.setImageResource(R.drawable.user_profile);
                txttitlehome.setTextColor(Color.rgb(15,187,0));
                txttitlelienhe.setTextColor(Color.rgb(103,103,103));
                txttitleprofile.setTextColor(Color.rgb(103,103,103));
                imgcontact.setImageResource(R.drawable.contact);
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
               switch (item.getItemId()) {
                   case R.id.nav_home:
                       Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                       startActivity(trangchu);
                       break;
                   case R.id.nav_rau:
                       Intent raucu = new Intent(getApplicationContext(), RauCuActivity.class);
                       raucu.putExtra("loai", 1);
                       startActivity(raucu);
                       break;
                   case R.id.nav_thit:
                       Intent thitcatrung = new Intent(getApplicationContext(), RauCuActivity.class);
                       thitcatrung.putExtra("loai", 2);
                       startActivity(thitcatrung);
                       break;
                   case R.id.nav_qua:
                       Intent traicay = new Intent(getApplicationContext(), RauCuActivity.class);
                       traicay.putExtra("loai", 3);
                       startActivity(traicay);
                       break;
                   case R.id.nav_donhang:
                       if (Utils.user_current.getId() ==0){
                           showDialogNoti();
                       }else {
                           Intent donhang = new Intent(getApplicationContext(), XemDonActivity.class);
                           startActivity(donhang);
                       }
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
                if (Utils.user_current.getId() == 0){
                    showDialogNoti();
                }else {
                    linearLayoutview.setVisibility(View.GONE);
                    replaceFragment(new FragmentProfile());
                    imguser.setImageResource(R.drawable.user_click);
                    txttitleprofile.setTextColor(Color.rgb(15,187,0));
                    txttitlehome.setTextColor(Color.rgb(103,103,103));
                    txttitlelienhe.setTextColor(Color.rgb(103,103,103));
                    imgcontact.setImageResource(R.drawable.contact);
                }



            }
        });
        imgcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 linearLayoutview.setVisibility(View.GONE);
                replaceFragment(new FragmentContact());
                imgcontact.setImageResource(R.drawable.contact_click);
                txttitlelienhe.setTextColor(Color.rgb(15,187,0));
                txttitlehome.setTextColor(Color.rgb(103,103,103));
                txttitleprofile.setTextColor(Color.rgb(103,103,103));
                imguser.setImageResource(R.drawable.user_profile);
            }
        });
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentlayout,fragment);
        fragmentTransaction.commit();
    }


    public void  showDialogNoti(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
    // get data sản phẩm mới đưa vào list mangspmoi
    private void getSpMoi() {
        compositeDisposable.add(apiBanhang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if(sanPhamMoiModel.isSuccess()){
                                mangSpMoi = sanPhamMoiModel.getResult();// lấy được giữ liệu
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
        mangquangcao.add("https://scontent.fhan5-11.fna.fbcdn.net/v/t1.15752-9/368063786_668008931978450_4936093028816487609_n.png?_nc_cat=111&ccb=1-7&_nc_sid=8cd0a2&_nc_eui2=AeFs_TL2AOBPcYUQlO1ry324L-mjfyVR_N0v6aN_JVH83SZbRogCm2v6-oIebECitZ-1bVMIIKO4II3tT0dQAufH&_nc_ohc=AiZTQXVHfUYAX93TK90&_nc_ht=scontent.fhan5-11.fna&oh=03_AdR0TVTlSffqr-hyX4QTZ2n-sHvfzvuWsruwLXalrN-eKw&oe=65B46903");
        mangquangcao.add("https://ihappy.vn/public/upload/cover.png?fbclid=IwAR1138CJZgXNt78K7ZcoggKmjxjLL3PVpUNdh3VtlzNbAxRkFBXM_KdL4ms");
        mangquangcao.add("https://img.freepik.com/free-vector/flat-design-organic-food-sale-banner-template_23-2149112289.jpg?fbclid=IwAR1obx85N1X5uP9uli0xDHoKOgrdx-llax0sShK02VZA7DPR1HauH5KH_Bg");
        for(int i=0;  i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
            Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
            Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
            viewFlipper.setAnimation(slide_in);
            viewFlipper.setOutAnimation(slide_out);
        }
//        viewFlipper.setFlipInterval(3000);
//        viewFlipper.setAutoStart(true);
//        Animation slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
//        Animation slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
//        viewFlipper.setAnimation(slide_in);
//        viewFlipper.setOutAnimation(slide_out);
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
        imageMess = findViewById(R.id.imageviewchat);
        txttitlehome = findViewById(R.id.textviewhome1);
        txttitleprofile = findViewById(R.id.textviewuser);
        txttitlelienhe =findViewById(R.id.textviewlienhe);
        txthello = findViewById(R.id.textViewhello);
        txttitle = findViewById(R.id.textviewTItle);
        imgavt = findViewById(R.id.avatar);
        linearLayoutview = findViewById(R.id.linearlayoutview);
        //anh xa
        floatingActionButton = findViewById(R.id.trangchu);
        imglive = findViewById(R.id.imageviewlive);
        imgcontact = findViewById(R.id.imageviewlienhe);
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
        // kiểm tra manggiohang có null khong ..
        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else{
            // hiển thị số lượng lên badge
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
        imageMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getApplicationContext(),ChatActivity.class);
                startActivity(chat);
            }
        });
    }

    @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();
//        int totalItem =0 ;
//        for(int i =0; i<Utils.manggiohang.size();i++){
//            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
//        }
//        badge.setText(String.valueOf(totalItem));
        if(Utils.manggiohang !=null ){
            int totalItem = 0;
            for(int i =0; i<Utils.manggiohang.size();i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
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