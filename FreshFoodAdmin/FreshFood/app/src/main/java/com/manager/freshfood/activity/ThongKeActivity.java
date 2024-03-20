package com.manager.freshfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.manager.freshfood.R;
import com.manager.freshfood.retrofit.ApiBanhang;
import com.manager.freshfood.retrofit.RetrofitClient;
import com.manager.freshfood.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    PieChart pieChart;
    BarChart barChart,barChartgroup;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanhang apiBanHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanhang.class);
        initView();
        ActionToolBar();
//        getdataChart();
        settingBarchart();
        getTkThang();
    }

    private void getTkketqua() {
        barChartgroup.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);
        compositeDisposable.add(apiBanHang.getthongkeTinhtrang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if(thongKeModel.isSuccess()){
                                List<BarEntry> listdata1 = new ArrayList<>();
                                List<BarEntry> listdata2 = new ArrayList<>();
                                for(int i=0; i<thongKeModel.getResult().size();i++){
                                    int trangthai = thongKeModel.getResult().get(i).getTrangthai();
                                    if(trangthai == 3) {
                                        int soluong = thongKeModel.getResult().get(i).getSoLuong();
                                        String thang = thongKeModel.getResult().get(i).getThang();
                                        listdata1.add(new BarEntry(Integer.parseInt(thang), soluong));
                                    }else {
                                        int soluong1 = thongKeModel.getResult().get(i).getSoLuong();
                                        String thang1 = thongKeModel.getResult().get(i).getThang();
                                        listdata2.add(new BarEntry(Integer.parseInt(thang1), soluong1));
                                    }
                                }
                                BarDataSet barDataSet = new BarDataSet(listdata1,"Giao Thành Công");
                                BarDataSet barDataSet1 = new BarDataSet(listdata2,"Giao thất bại");
                                barDataSet.setColors(Color.GREEN);
                                barDataSet1.setColors(Color.RED);

                                BarData data1 =  new BarData(barDataSet,barDataSet1);
                                barChartgroup.animateXY(2000,2000);
                                barChartgroup.setData(data1);
                                XAxis xAxis = barChartgroup.getXAxis();
//                                xAxis.setCenterAxisLabels(true);
//                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                                xAxis.setGranularity(1);
//                                xAxis.setGranularityEnabled(true);
                                YAxis yAxisright = barChartgroup.getAxisRight();
                                yAxisright.setAxisMinimum(0);
                                YAxis yAxisLeft = barChartgroup.getAxisLeft();
                                yAxisLeft.setAxisMinimum(0);
                                barChartgroup.setDragEnabled(true);
//                                barChartgroup.setVisibleXRangeMaximum(3);
                                barChartgroup.getDescription().setEnabled(false);
                                barChartgroup.setDrawValueAboveBar(false);
                                float barSpace = 0.08f;
                                float groupSpace = 0.44f;
                                data1.setBarWidth(0.40f);
                                barChartgroup.getXAxis().setAxisMinimum(1);
                                barChartgroup.getXAxis().setAxisMaximum(12);
                                barChartgroup.getAxisLeft().setAxisMinimum(0);
                                barChartgroup.groupBars(7,groupSpace,barSpace);
                                barChartgroup.invalidate();
                            }
                        },
                        throwable -> {
                            Log.d("loggg",throwable.getMessage());
                        }
                )
        );
    }

    private void settingBarchart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMaximum(12);
        xAxis.setAxisMinimum(1);
        YAxis yAxisright = barChart.getAxisRight();
        yAxisright.setAxisMinimum(0);
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_thongke,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ActionBar a = getSupportActionBar();
        int id = item.getItemId();
        switch (id){
            case R.id.tkthang:
                getTkThang();
                a.setTitle("Thống kê doanh thu Tháng");
                return true;
            case R.id.tkmathang:
                getdataChart();
                a.setTitle("Thống kê theo mặt Hàng");
                return true;
            case R.id.tktinhtrang:
                   getTkketqua();
                   a.setTitle("Thống kê theo tình trạng");
                   return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void getTkThang() {
        barChartgroup.setVisibility(View.GONE);
          barChart.setVisibility(View.VISIBLE);
          pieChart.setVisibility(View.GONE);
          compositeDisposable.add(apiBanHang.getthongkeThang()
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(
                          thongKeModel -> {
                            if(thongKeModel.isSuccess()){
                                List<BarEntry> listdata = new ArrayList<>();
                                for(int i=0; i<thongKeModel.getResult().size();i++){
                                    String tongtien = thongKeModel.getResult().get(i).getTongtienthang();
                                    String thang = thongKeModel.getResult().get(i).getThang();
                                    listdata.add(new BarEntry(Integer.parseInt(thang),Float.parseFloat(tongtien)));
                                }
                                BarDataSet barDataSet = new BarDataSet(listdata,"Thống Kê");
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                barDataSet.setValueTextSize(14f);
                                barDataSet.setValueTextColor(Color.RED);

                                BarData data =  new BarData(barDataSet);
                                barChart.animateXY(2000,2000);
                                barChart.setData(data);
                                barChart.invalidate();
                            }
                          },
                          throwable -> {
                              Log.d("loggg",throwable.getMessage());
                          }
                  )
          );
    }
// thống kê theo số sản phẩm bằng biểu đồ tròn
    private void getdataChart() {
        barChartgroup.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        List<PieEntry> listdata = new ArrayList<>();
        compositeDisposable.add(apiBanHang.getthongke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if(thongKeModel.isSuccess()){
                                for(int i=0; i<thongKeModel.getResult().size();i++){
                                    String tensp = thongKeModel.getResult().get(i).getTensp();
                                    int tong = thongKeModel.getResult().get(i).getTong();
                                    listdata.add(new PieEntry(tong, tensp));
                                }
                                PieDataSet pieDataSet = new PieDataSet(listdata,"Thống kê");
                                PieData data = new PieData();
                                data.setDataSet(pieDataSet);
                                data.setValueTextSize(12f);
                                data.setValueFormatter(new PercentFormatter(pieChart));
                                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                                pieChart.setData(data);
                                pieChart.animateXY(2000,2000);
                                pieChart.setUsePercentValues(true);
                                pieChart.getDescription().setEnabled(false);
                                pieChart.invalidate();
                            }

                        },
                        throwable -> {
                            Log.d("logg",throwable.getMessage());
                        }
                )
        );
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        pieChart = findViewById(R.id.piechart);
        barChart = findViewById(R.id.barchart);
        barChartgroup = findViewById(R.id.barcharttinhtrang);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}