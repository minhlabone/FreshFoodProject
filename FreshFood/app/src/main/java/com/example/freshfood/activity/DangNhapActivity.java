package com.example.freshfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freshfood.R;
import com.example.freshfood.retrofit.ApiBanhang;
import com.example.freshfood.retrofit.RetrofitClient;
import com.example.freshfood.zalo.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangNhapActivity extends AppCompatActivity {
    TextView txtdangki,txtresetpass;
    EditText email,pass;
    AppCompatButton btndangnhap, btnguest;
    ApiBanhang apiBanHang;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ImageView fly;
    boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        initView();
        initControll();
    }

    private void initControll() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fly);
        fly.startAnimation(animation);
        txtdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKiActivity.class);
                startActivity(intent);
            }
        });

        txtresetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Intent intent = new Intent(getApplicationContext(), ResetPassActivity.class);
                  startActivity(intent);
            }
        });
        btnguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.user_current.setId(0);
                Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lấy ra email và pass từ 2 edittext
                  String str_email = email.getText().toString().trim();
                  String str_pass = pass.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Email!", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(str_pass)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập Password!", Toast.LENGTH_SHORT).show();
                }else {
                    // ghi nhớ tài khoản đăng nhập
                    Paper.book().write("email",str_email);
                    Paper.book().write("pass",str_pass);
                    if(user != null){
                        // user đã có đăng nhập firebase ( đã đăng nhập trước đó rồi )
                        dangNhap(str_email,str_pass);
                    }else {
                        //user đã đăng xuất
                        firebaseAuth.signInWithEmailAndPassword(str_email,str_pass)
                                .addOnCompleteListener(DangNhapActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            dangNhap(str_email,str_pass);
                                        }else {
                                            Toast.makeText(DangNhapActivity.this, "Tài khoản hoặc mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    private void initView() {
        Paper.init(this);
        fly = findViewById(R.id.fly_animation);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanhang.class);
        txtdangki = findViewById(R.id.txtdangki);
        txtresetpass = findViewById(R.id.txtresetpass);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        btndangnhap = findViewById(R.id.btndangnhap);
        btnguest = findViewById(R.id.btnguest);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        //đọc dữ liệu đã lưu đăng nhập
        if(Paper.book().read("email") != null && Paper.book().read("pass") !=null){
            // set email pass bằng giữ liệu đã lưu
            email.setText(Paper.book().read("email"));
            pass.setText(Paper.book().read("pass"));
            if(Paper.book().read("islogin")!=null){
                boolean flag = Paper.book().read("islogin");
                if(flag){
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             dangNhap(Paper.book().read("email"),Paper.book().read("pass"));
                         }
                     },1000000);
                }
            }
        }
    }

    private void dangNhap(String email, String pass) {
        Log.d("logg", "dang nhap");
        compositeDisposable.add(apiBanHang.dangNhap(email,pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()) {
                                isLogin = true;
                                Paper.book().write("islogin",isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                Intent intent = new Intent(DangNhapActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        throwable -> {
                            Toast.makeText(DangNhapActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null){
            email.setText(Utils.user_current.getEmail());
            pass.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}