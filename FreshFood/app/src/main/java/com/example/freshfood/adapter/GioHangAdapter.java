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
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> gioHangList;

    public GioHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // lấy giữ liệu tại vị trí pos
        GioHang gioHang = gioHangList.get(position);
        // gọi để ánh xạ
        holder.item_giohang_tensp.setText(gioHang.getTensp());
        holder.item_giohang_soluong.setText(gioHang.getSoluong()+"");
        if(gioHang.getHinhsp().contains("http")){
            Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        }else {
            Glide.with(context).load(Utils.BASE_URL + "images/" + gioHang.getHinhsp()).into(holder.item_giohang_image);
        }
//        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getGiasp())));
        long gia  = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                  if(b){
                      //set check trạng thái cho checkbox cho giỏ hàng
                      Utils.manggiohang.get(holder.getAdapterPosition()).setChecked(true);
                      // nếu chưa được thêm vào danh sách mua hàng thì thêm vào
                      if(!Utils.mangmuahang.contains(gioHang)){
                          Utils.mangmuahang.add(gioHang);
                      }
                    // gửi sự kiện tính tổng tiền
                      EventBus.getDefault().postSticky(new TinhTongEvent());
                  }else {
                      // nếu chưa được check
                      Utils.manggiohang.get(holder.getAdapterPosition()).setChecked(false);
                      // duyệt qua danh sách mua hàng để nếu có trong danh sách mua hàng thì ta loại bỏ khỏi danh sách
                      for(int i=0; i<Utils.mangmuahang.size();i++){
                          if(Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()){
                              Utils.mangmuahang.remove(i);
                              // gửi lại sự kiện tinh tổng
                              EventBus.getDefault().postSticky(new TinhTongEvent());
                          }
                      }
                  }
            }
        });

        holder.checkBox.setChecked(gioHang.isChecked());

        // set số lượng khi mình ấn vào cộng or trừ
        holder.setListener(new IImageClickListener() {
            @Override
            public void onImageCLick(View view, int pos, int giatri) {
                Log.d("TAG", "onImageCLick: " + pos + "..." + giatri);
                if (giatri == 1) {
                    if (gioHangList.get(pos).getSoluong() > 1) {
                        int soluongmoi = gioHangList.get(pos).getSoluong() - 1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+"");
                        long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        // gửi sự kiện sang bên giohangactivity
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                        // nếu số lượng sản phẩm là 1 thì gọi alert thông báo có muốn xóa ko
                    }else if(gioHangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn Có Muốn Xóa Sản Phẩm Này Khỏi Giỏ Hàng Không ");
                        builder.setPositiveButton("Đồng Ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Utils.mangmuahang.remove(gioHang);
                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else if (giatri == 2) {
                    if (gioHangList.get(pos).getSoluong() < 11) {
                        int soluongmoi = gioHangList.get(pos).getSoluong() + 1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong()+"");
                    long gia = gioHangList.get(pos).getSoluong() * gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhTongEvent());
                }
            }
            });

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
         ImageView item_giohang_image,imgtru, imgcong;
         TextView item_giohang_tensp, item_giohang_gia, item_giohang_soluong, item_giohang_giasp2;
         IImageClickListener listener;
         CheckBox checkBox;
         public MyViewHolder(@NonNull View itemView) {
             super(itemView);
             item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
             item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
             item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
             item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
             item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
             imgtru = itemView.findViewById(R.id.item_giohang_tru);
             imgcong = itemView.findViewById(R.id.item_giohang_cong);
             checkBox = itemView.findViewById(R.id.item_giohang_check);
             //event click công trừ số lượng
             imgcong.setOnClickListener(this);
             imgtru.setOnClickListener(this);
         }

         // sự kiện click cho 2 icon cộng và trừ
         public void setListener(IImageClickListener listener){
             this.listener = listener;
         }

        @Override
        public void onClick(View view) {
            if(view == imgtru){
                //1 là tru
                listener.onImageCLick(view , getAdapterPosition(), 1);
            }else if(view == imgcong){
                //2 là cong
                listener.onImageCLick(view, getAdapterPosition(),2);
            }
        }
    }
}
