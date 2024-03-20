package com.example.freshfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.freshfood.InterFace.ItemClickListener;
import com.example.freshfood.R;
import com.example.freshfood.activity.ChiTietActivity;
import com.example.freshfood.model.GioHang;
import com.example.freshfood.model.SanPhamMoi;
import com.example.freshfood.zalo.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.MyViewHolder> {
      Context context;
      List<SanPhamMoi> array;
    public SanPhamMoiAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp_moi, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

         SanPhamMoi sanPhamMoi = array.get(position);
         holder.txtten.setText(sanPhamMoi.getTensp());
         DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
         holder.txtgia.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPhamMoi.getGiasp()))+"Đ");
         if(sanPhamMoi.getHinhanh().contains("http")){
              Glide.with(context).load(sanPhamMoi.getHinhanh()).into(holder.imghinhanh);
         }else {
             Glide.with(context).load(Utils.BASE_URL + "images/" + sanPhamMoi.getHinhanh()).into(holder.imghinhanh);
         }
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    //click
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    // gửi thông tin sang bên chi tiet activity
                    intent.putExtra("chitiet",sanPhamMoi);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
         holder.linearthem.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 themGioHang(sanPhamMoi);
             }
         });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    private  void themGioHang(SanPhamMoi sanPhamMoi){
        // kiểm tra xem giỏ hàng có chứa sản phẩm hay không
        if(Utils.manggiohang.size() > 0){
            boolean flag  = false;
            int soluonglinear = 1;
            for(int i=0; i<Utils.manggiohang.size();i++){
                // nếu spham trùng id thì cộng vào số lượng spham đó
                if(Utils.manggiohang.get(i).getIdsp()== sanPhamMoi.getId()){
                    Utils.manggiohang.get(i).setSoluong(soluonglinear + Utils.manggiohang.get(i).getSoluong());
                    flag = true;
                }
            }
            // neu không trùng id thì thêm 1 hàng sản phẩm mới
            if(flag == false){
                long gia = Long.parseLong(sanPhamMoi.getGiasp());
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soluonglinear);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setHinhsp(sanPhamMoi.getHinhanh());
                Utils.manggiohang.add(gioHang);
            }
        }else {
            int soluonglinear = 1;
            long gia = Long.parseLong(sanPhamMoi.getGiasp());
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soluonglinear);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhsp(sanPhamMoi.getHinhanh());
            Utils.manggiohang.add(gioHang);
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtgia, txtten;
        LinearLayout linearthem;
        ImageView imghinhanh;
        private  ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearthem = itemView.findViewById(R.id.linearthemgiohang);
            txtgia = itemView.findViewById(R.id.itemsp_gia);
            txtten = itemView.findViewById(R.id.itemsp_ten);
            imghinhanh = itemView.findViewById(R.id.itemsp_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(),false);
        }
    }
}
