package com.example.ggapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.ggapplication.data.SellResponseModel;
import com.example.ggapplication.data.User;
import com.example.ggapplication.data.YourResponseModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SellActivity extends AppCompatActivity {
    private ImageView imageView;

    private Button bc;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private String mycode;
    private Button fb;
    private EditText con;
    private EditText pri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 如果没有权限，请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
        }
        imageView = findViewById(R.id.imageView);
        bc=findViewById(R.id.baoc);
        fb=findViewById(R.id.fabu);
        con=findViewById(R.id.cont);
        pri=findViewById(R.id.pri);
        imageView.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);

        });
        fb.setOnClickListener(view -> {
            new Thread(() -> {
                Gson gson =new Gson();
                // url路径
                String url = "http://47.107.52.7:88/member/tran/goods/add";
                MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
                Map<String, Object> bodyMap = new HashMap<>();
                bodyMap.put("price", pri.getText().toString());
                bodyMap.put("imageCode", mycode);
                bodyMap.put("typeName", "手机");
                bodyMap.put("typeId", 1);
                bodyMap.put("addr", "桂林市灵川县灵田镇桂电");
                bodyMap.put("userId", User.getId());
                bodyMap.put("content", con.getText().toString());
                // 将Map转换为字符串类型加入请求体中
                String body = gson.toJson(bodyMap);

                //请求组合创建
                Request request = new Request.Builder()
                        .url(url)
                        // 将请求头加至请求中
                        .addHeader("appId","231203e144a84d3197e91c792d6ed81b")
                        .addHeader("appSecret", "10557616300ac1c6d465191061d82db65f838")
                        .addHeader("Accept", "application/json, text/plain, */*")
                        .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                        .build();
                try {

                    OkHttpClient client = new OkHttpClient();
                    //发起请求，传入callback进行回调
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            System.out.println("失败");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            Gson gson=new Gson();

                            // Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
                            // 获取响应体的json串
                            String body = response.body().string();
                            Log.d("info", body);
                            ResponseBody responseModel = gson.fromJson(body,ResponseBody.class);
                            if(responseModel.getCode()==200){

                                Intent intent=new Intent(SellActivity.this,MainActivity.class);
                                startActivity(intent);


                            }








                        }
                    });
                }catch (NetworkOnMainThreadException ex){
                    ex.printStackTrace();
                }
            }).start();












        });

        bc.setOnClickListener(v -> {

            new Thread(() -> {
                // 服务器接口 URL
                String url = "http://47.107.52.7:88/member/tran/image/upload";

                // 请求头
                Headers headers = new Headers.Builder()
                        .add("Accept", "application/json, text/plain, */*")
                        .add("appId", "231203e144a84d3197e91c792d6ed81b")
                        .add("appSecret", "10557616300ac1c6d465191061d82db65f838")
                        .build();

                // 创建请求体
                MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");
                File imageFile = new File(getRealPathFromURI(selectedImageUri)); // 获取真实路径
                File[] fileArray = new File[]{imageFile};

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("fileList", imageFile.getName(), RequestBody.create(MEDIA_TYPE_IMAGE, imageFile))
                        .build();


                // 创建请求
                Request request = new Request.Builder()
                        .url(url)
                        .headers(headers)
                        .post(requestBody)
                        .build();

                try {
                    OkHttpClient client = new OkHttpClient();
                    // 发起请求，传入回调进行处理
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            e.printStackTrace();
                            System.out.println("上传图片失败，错误信息：" + e.getMessage());
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            Gson gson=new Gson();
                            String body = response.body().string();
                            System.out.println("成功图片"+body);
                            SellResponseModel responseModel = gson.fromJson(body, SellResponseModel.class);
                           mycode= responseModel.getData().getImageCode();
                            System.out.println("我的code"+mycode);
                        }
                    });
                } catch (NetworkOnMainThreadException ex) {
                    ex.printStackTrace();
                }
            }).start();










        });

    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }

    // 处理选择图片后的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                // 将选择的图片显示在ImageView中
                Glide.with(this).load(selectedImageUri).into(imageView);
                System.out.println("这个选择的图片是"+selectedImageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static class ResponseBody <T> {

        /**
         * 业务响应码
         */
        private int code;
        /**
         * 响应提示信息
         */
        private String msg;
        /**
         * 响应数据
         */
        private T data;

        public ResponseBody(){}

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public T getData() {
            return data;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseBody{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }


}
