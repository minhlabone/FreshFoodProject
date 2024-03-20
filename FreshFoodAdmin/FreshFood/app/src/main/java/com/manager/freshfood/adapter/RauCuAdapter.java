package com.manager.freshfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manager.freshfood.InterFace.ItemClickListener;
import com.manager.freshfood.R;
import com.manager.freshfood.activity.ChiTietActivity;
import com.manager.freshfood.model.SanPhamMoi;
import com.manager.freshfood.utils.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class RauCuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SanPhamMoi> array;
    private  static final  int VIEW_TYPE_DATA =0;
    private  static final  int VIEW_TYPE_LOADING =1;
    public RauCuAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_raucu, parent, false);
            return new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading,parent,false);
            return  new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if(holder instanceof  MyViewHolder){
           MyViewHolder myViewHolder = (MyViewHolder) holder;
           SanPhamMoi sanPham = array.get(position);
           myViewHolder.tensp.setText(sanPham.getTensp());
           DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
           myViewHolder.giasp.setText("Giá: "+decimalFormat.format(Double.parseDouble(sanPham.getGiasp()))+"Đ");
           myViewHolder.mota.setText(sanPham.getMota());
//           Glide.with(context).load(sanPham.getHinhanh()).into(myViewHolder.hinhanh);
           if(sanPham.getHinhanh().contains("http")){
               Glide.with(context).load(sanPham.getHinhanh()).into(myViewHolder.hinhanh);
           }else{
               String hinh = Utils.BASE_URL+"images/"+sanPham.getHinhanh();
               Glide.with(context).load(hinh).into(myViewHolder.hinhanh);
           }
           myViewHolder.setItemClickListener(new ItemClickListener() {
               @Override
               public void onClick(View view, int pos, boolean isLongClick) {
                   if(!isLongClick){
                       //click
                       Intent intent = new Intent(context, ChiTietActivity.class);
                       intent.putExtra("chitiet",sanPham);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       context.startActivity(intent);
                   }
               }
           });
       }else {
           LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
           try {
               loadingViewHolder.progressBar.setIndeterminate(true);
           }
           catch (Exception e){
               Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
           }
       }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) ==null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }




    public class LoadingViewHolder extends RecyclerView.ViewHolder{
      ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp, giasp,mota;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;
       public  MyViewHolder(@NonNull View itemView) {
           super(itemView);
           tensp = itemView.findViewById(R.id.itemraucu_ten);
           giasp = itemView.findViewById(R.id.itemraucu_gia);
           mota = itemView.findViewById(R.id.itemraucu_mota);
           hinhanh = itemView.findViewById(R.id.itemraucu_image);
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
