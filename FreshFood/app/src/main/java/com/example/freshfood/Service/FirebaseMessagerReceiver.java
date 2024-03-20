package com.example.freshfood.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.freshfood.R;
import com.example.freshfood.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FirebaseMessagerReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        if(message.getNotification() != null){
            showNotification(message.getNotification().getTitle(),message.getNotification().getBody());
        }

    }

    private void showNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String channelId = "noti";
        //để mở activity khi người dùng nhấn vào thông báo.
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        // tạo 1 đối tượng để xây dựng thông báo.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelId)
                .setSmallIcon(R.drawable.baseline_cloud_24)
                .setAutoCancel(true) // set thông báo biến mất khi ấn
                .setVibrate(new long[]{1000,1000,1000,1000}) // chuỗi rung khi thông báo hiện lên
                .setOnlyAlertOnce(true) // âm thanh 1 lần
                .setContentIntent(pendingIntent); //pending khởi chạy khi ấn vào thông báo
        // Thiết lập nội dung thông báo
        builder = builder.setContent(customView(title, body));
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // kiemer tra phiên bản ( api >26)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(channelId,"web_app",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        //hiển thị thông báo
        notificationManager.notify(0,builder.build());
    }
// tạo giao diện thông báo
    private RemoteViews customView(String title, String body){
        //Tạo một đối tượng RemoteViews từ một bố cục xml
        RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.notification);
        //set title cho thông báo
        remoteViews.setTextViewText(R.id.title_noti,title);
        //set noi dung
        remoteViews.setTextViewText(R.id.body_noti,body);
        //set hinh anh
        remoteViews.setImageViewResource(R.id.imgnoti,R.drawable.baseline_cloud_24);
        return remoteViews;
    }
}
