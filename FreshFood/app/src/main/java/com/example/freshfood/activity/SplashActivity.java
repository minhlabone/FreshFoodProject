package com.example.freshfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.freshfood.R;
import com.example.freshfood.zalo.utils.Utils;

public class SplashActivity extends AppCompatActivity {
    Button btngetinstand, btngetdangnhap, btngetdangki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btngetinstand = findViewById(R.id.btngetinstand);
        btngetdangnhap = findViewById(R.id.btngetdangnhap);
        btngetdangki = findViewById(R.id.btngetdangky);
        btngetinstand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.user_current.setId(0);
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btngetdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
        btngetdangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangKiActivity.class);
                startActivity(intent);
            }
        });
    }
}