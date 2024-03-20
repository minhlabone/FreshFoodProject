package com.example.freshfood.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.freshfood.InterFace.IImageClickListener;
import com.example.freshfood.R;
import com.example.freshfood.model.EventBus.TinhTongEvent;
import com.example.freshfood.model.GioHang;
import com.example.freshfood.zalo.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public ThanhToanAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhtoan, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GioHang gioHang = gioHangList.get(position);
        holder.item_thanhtoan_tensp.setText(gioHang.getTensp());
        holder.item_thanhtoan_soluong.setText(gioHang.getSoluong()+"");
        if(gioHang.getHinhsp().contains("http")){
            Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_thanhtoan_image);
        }else {
            Glide.with(context).load(Utils.BASE_URL + "images/" + gioHang.getHinhsp()).into(holder.item_thanhtoan_image);
        }
//        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_thanhtoan_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.item_thanhtoan_gia.setText(decimalFormat.format((gioHang.getGiasp())));
        long gia  = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_thanhtoan_giasp2.setText(decimalFormat.format(gia));

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         ImageView item_thanhtoan_image,imgtru, imgcong;
         TextView item_thanhtoan_tensp, item_thanhtoan_gia, item_thanhtoan_soluong, item_thanhtoan_giasp2;
         IImageClickListener listener;
         CheckBox checkBox;
         public MyViewHolder(@NonNull View itemView) {
             super(itemView);
             item_thanhtoan_image = itemView.findViewById(R.id.item_thanhtoan_image);
             item_thanhtoan_tensp = itemView.findViewById(R.id.item_thanhtoan_tensp);
             item_thanhtoan_gia = itemView.findViewById(R.id.item_thanhtoan_gia);
             item_thanhtoan_soluong = itemView.findViewById(R.id.item_thanhtoan_soluong);
             item_thanhtoan_giasp2 = itemView.findViewById(R.id.item_thanhtoan_giasp2);
         }

        @Override
        public void onClick(View view) {
        }
    }
}
