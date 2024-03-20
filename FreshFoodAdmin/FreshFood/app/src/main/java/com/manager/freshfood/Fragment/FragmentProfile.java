package com.manager.freshfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.manager.freshfood.R;
import com.manager.freshfood.activity.DangNhapActivity;
import com.manager.freshfood.utils.Utils;

public class FragmentProfile extends Fragment {
    TextView txtname,txtemail,txtphone,txtdangxuat;

    View mview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_profile,container,false);
        initView();
        initControll();
        getDataProfile();
        return mview;
    }

    private void getDataProfile() {
        txtname.setText(Utils.user_current.getUsername());
        txtemail.setText(Utils.user_current.getEmail());
        txtphone.setText(Utils.user_current.getMobile());
    }

    private void initControll() {
        txtdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
//        txtname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), " ", Toast.LENGTH_SHORT).show();
//            }
//        });
//        txtemail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), " ", Toast.LENGTH_SHORT).show();
//            }
//        });
//        txtphone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), " ", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void initView() {
        txtdangxuat = mview.findViewById(R.id.dangxuat);
        txtname = mview.findViewById(R.id.nameuser);
        txtemail = mview.findViewById(R.id.emailuser);
        txtphone = mview.findViewById(R.id.phoneuser);
    }
}
