package com.example.freshfood.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.freshfood.R;

public class FragmentContact extends Fragment {
    View mview;
    ImageView imgfb, imgzalo, imginsta,imgtw;
    TextView txtbanner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_contact,container,false);
        initView();
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.flyleft);
        txtbanner.startAnimation(animation);
        imgfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent face = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ten.khongco.56211497"));
                startActivity(face);
            }
        });
        imgzalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent zalo = new Intent(Intent.ACTION_VIEW, Uri.parse("https://zalo.me/0962501346"));
                startActivity(zalo);
            }
        });
        imginsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/ngocdiep150502"));
                startActivity(intent);
            }
        });
        return mview;
    }

    private void initView() {
        imgfb = mview.findViewById(R.id.fb);
        imgzalo = mview.findViewById(R.id.zalo);
        imginsta = mview.findViewById(R.id.ins);
        txtbanner = mview.findViewById(R.id.tv1);
    }
}
