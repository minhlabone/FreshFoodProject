package com.example.freshfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.freshfood.R;
import com.example.freshfood.retrofit.ApiBanhang;
import com.example.freshfood.retrofit.RetrofitClient;
import com.example.freshfood.zalo.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ResetPassActivity extends AppCompatActivity {
    EditText email;
    AppCompatButton btnreset;
    ApiBanhang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initView();
        initControll();
    }

    private void initControll() {
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(ResetPassActivity.this, "Bạn chưa nhập Email ", Toast.LENGTH_SHORT).show();
                }else{
                    progressBar.setVisibility(View.VISIBLE);
//                   compositeDisposable.add(apiBanHang.resetPass(str_email)
//                           .subscribeOn(Schedulers.io())
//                           .observeOn(AndroidSchedulers.mainThread())
//                           .subscribe(
//                                   userModel -> {
//                                        if(userModel.isSuccess()){
//                                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
//                                            Intent intent = new Intent(getApplicationContext(),DangNhapActivity.class);
//                                            startActivity(intent);
//                                            finish();
//                                        }else {
//                                            Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    progressBar.setVisibility(View.INVISIBLE);
//                                   },
//                                   throwable -> {
//                                       Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                                       progressBar.setVisibility(View.INVISIBLE);
//                                   }
//
//                           )
//                   );

                    //resetpasss tren firebase
                    FirebaseAuth.getInstance().sendPasswordResetEmail(str_email)
                            .addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Kiểm Tra Email của bạn", Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            });

                }
            }
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanhang.class);
        email = findViewById(R.id.edtresetpass);
        btnreset = findViewById(R.id.btnresetpass);
        progressBar = findViewById(R.id.progressbar);
    }
}