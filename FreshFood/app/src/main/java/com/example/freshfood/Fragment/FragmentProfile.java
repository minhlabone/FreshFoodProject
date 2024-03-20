package com.example.freshfood.Fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.freshfood.R;
import com.example.freshfood.activity.DangNhapActivity;
import com.example.freshfood.model.MessageModel;
import com.example.freshfood.retrofit.ApiBanhang;
import com.example.freshfood.retrofit.RetrofitClient;
import com.example.freshfood.zalo.utils.Utils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProfile extends Fragment {
    TextView txtname,txtemail,txtphone,txtpassword;
    AppCompatButton txtdangxuat;
    RoundedImageView imageProfile;
    String mediaPath;
    ApiBanhang apiBanhang;
    LinearLayout layname;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    View mview;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_profile,container,false);
        apiBanhang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanhang.class);

        initView();
        initControll();
        getDataProfile();
        return mview;
    }
   // hiển thị data thông tin người dùng
    private void getDataProfile() {

        txtname.setText(Utils.user_current.getUsername());
        txtemail.setText(Utils.user_current.getEmail());
        txtphone.setText(Utils.user_current.getMobile());
        txtpassword.setText(Utils.user_current.getPass());
        if (Utils.user_current.getImageprofile() == null){

        }else {
            Log.d("loogggg", "onActivityResult: co");
            String randomParameter = "?random=" + System.currentTimeMillis();
            Glide.with(getActivity()).load(Utils.BASE_URL+"images/"+Utils.user_current.getImageprofile()+randomParameter).into(imageProfile);
        }



    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initControll() {
        // thay tên người dùng
        layname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogName();
            }
        });


        txtdangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DangNhapActivity.class);
                startActivity(intent);
            }
        });
      // thay đổi ảnh đại diện
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("loogggg", "onActivityResult: "+mediaPath);
                ImagePicker.with(getActivity())
                        .crop() // cắt ảnh
                        .compress(800) // nén ảnh giảm kick thước
                        .maxResultSize(700, 700) // kích thước tối đa của ảnh
                        .createIntent(
                                intent -> {
                                    // sử dụng lauch để mở acitivy ảnh và đặt kết quả trả về
                                    startForProfileImageResult.launch(intent);
                                    return null;
                                }
                        );


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

    private void showDialogName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_name, null);
        EditText edtname = dialogView.findViewById(R.id.edtname);

        edtname.setText(Utils.user_current.getUsername());

        builder.setView(dialogView);
        builder.setTitle("Change your name");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (edtname.getText().toString().length()>0){
                    updateUsername(edtname.getText().toString());
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }
 // cập nhật username mới vào database
    private void updateUsername(String s) {
        compositeDisposable.add(apiBanhang.updateProfile(Utils.user_current.getId(),s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess()){
                                Utils.user_current.setUsername(s);
                                getDataProfile();
                            }
                        },
                        throwable -> {

                        }
                ));

    }

    private ActivityResultLauncher<Intent> startForProfileImageResult = registerForActivityResult(
          new ActivityResultContracts.StartActivityForResult(),result -> {
              int resultcode = result.getResultCode();
              Intent data = result.getData();
              if (resultcode == Activity.RESULT_OK){
                  // lấy đường dẫn dưới dạng chuỗi rồi gọi upload để hiển thị ảnh lên imageview
                  Uri fileUri = data.getData();
                  mediaPath = data.getDataString();
                  uploadMultipleFiles();
                  imageProfile.setImageURI(fileUri);
              }
            }
    );



 // sử lí ảnh tải lên
    private void uploadMultipleFiles() {
        // Chuyển đổi đường dẫn của file đã chọn thành Uri
        Uri uri = Uri.parse(mediaPath);
        // Tạo đối tượng File từ Uri
        File file = new File(getPath(uri));
        // Tạo RequestBody từ file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
        // Tạo RequestBody để chứa ID của người dùng
        RequestBody idrequest = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(Utils.user_current.getId()));
        // Gọi API để tải lên file
        Call<MessageModel> call = apiBanhang.uploadFile(fileToUpload1, idrequest);
        call.enqueue(new Callback< MessageModel >() {
            @Override
            public void onResponse(Call < MessageModel > call, Response< MessageModel > response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    // Nếu tải lên thành công, cập nhật đường dẫn hình ảnh trong đối tượng người dùng
                    if (serverResponse.isSuccess()) {
                        Utils.user_current.setImageprofile(serverResponse.getName());
                    } else {
                        Toast.makeText(getActivity(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.v("Response", serverResponse.toString());
                }

            }
            @Override
            public void onFailure(Call< MessageModel > call, Throwable t) {
                Log.d("log", t.getMessage());
            }
        });


    }

    private String getPath(Uri uri) {
        String result;
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            // Nếu cursor là null, sử dụng đường dẫn của Uri
            result = uri.getPath();
        } else {
            // Di chuyển con trỏ đến hàng đầu tiên của cursor
            cursor.moveToFirst();
            // Lấy chỉ số của cột chứa đường dẫn của file
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            // Lấy đường dẫn từ cột
            result = cursor.getString(index);
            // Đóng cursor để giải phóng tài nguyên
            cursor.close();
        }
        return result;
    }


    private void initView() {
        txtpassword = mview.findViewById(R.id.passuser);
        txtdangxuat = mview.findViewById(R.id.dangxuat);
        txtname = mview.findViewById(R.id.nameuser);
        txtemail = mview.findViewById(R.id.emailuser);
        txtphone = mview.findViewById(R.id.phoneuser);
        imageProfile = mview.findViewById(R.id.imageprofile);
        layname = mview.findViewById(R.id.layname);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
