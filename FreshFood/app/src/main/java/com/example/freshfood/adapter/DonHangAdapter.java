package com.example.freshfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshfood.R;
import com.example.freshfood.model.DonHang;
import com.example.freshfood.zalo.utils.Utils;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private  RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listdonhang;

    public DonHangAdapter(Context context, List<DonHang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // lấy đơn hàng tại vị trí position trong cái lis đơn hàng
            DonHang donHang = listdonhang.get(position);
            holder.txtdonhang.setText("Đơn Hàng: "+donHang.getId());
            holder.txttrangthai.setText(Utils.statusOrder(donHang.getTrangthai()));
            holder.txttongtien.setText(donHang.getTongtien());
            // tạo 1 linnear để quản lí hướng hiển thị của Recycleview
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
            // quá trình nạp trước các mục dữ liệu để tối ưu hóa hiển thị dữ liệu khi người dùng cuộn
         layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
         // hiển thị adapter chi tiet ( chi tiết đơn hàng đã đặt)
        ChitietAdapter chitietAdapter = new ChitietAdapter(context,donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chitietAdapter);
        // giúp tái sử dụng và quản lý lại các view đã tồn tại để tối ưu hóa hiệu suất và tiết kiệm bộ nhớ khi cuộn
        holder.reChitiet.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }
 // Tham chiếu đến các thành phần giao diện
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtdonhang,txttongtien,txttrangthai;
        RecyclerView reChitiet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            txttongtien = itemView.findViewById(R.id.txttongtien);
            txttrangthai = itemView.findViewById(R.id.trangthaidon);
            reChitiet = itemView.findViewById(R.id.recycleview_chitiet);
        }
    }
}
