package com.manager.freshfood.retrofit;



import com.manager.freshfood.model.NotiResponse;
import com.manager.freshfood.model.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNofication {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAy3mMhxg:APA91bGdWVxpEns9Zl355iWPUWOUKUO6VDwPLPbPCnVMHqH0CH9zMLkgV4H024UV_Jtjm9Uw4a8QhlNkZa3AgF6EXSojiXxOV6moYomjX6mT8FscyUNjftWx7bd51BomKlu5dOV0NEfS"

            }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNotification(@Body NotiSendData data);
}
