package com.example.ggapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ggapplication.data.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailActivity extends AppCompatActivity {
    private String sid;
    private String sellid;
    private double price=0.0;
    private String con;
    private String pul;
    private ImageView imageView;
    private TextView textViewn;
    private TextView textViewp;
    private boolean isImage1 = true;
    private ImageView imageView2;
    private CardView cardView;
    private CardView cardView2;
    private  Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView=findViewById(R.id.spic);
        textViewn=findViewById(R.id.pro_name);
        textViewp=findViewById(R.id.pro_price);
        cardView=findViewById(R.id.myai);
        imageView2=findViewById(R.id.ai);
        cardView2=findViewById(R.id.buy);
        cardView.setOnClickListener(view -> {
            if (isImage1) {
                imageView2.setImageResource(R.drawable.loving); // 切换到其他图片
            } else {
                imageView2.setImageResource(R.drawable.love); // 切换回原始图片
            }

            isImage1 = !isImage1; // 切换标志变量的值
        });
        Intent intent = getIntent();
        if(intent!=null){
            sid=intent.getStringExtra("sid");
            System.out.println("这个商品的id是"+sid);
         sellid=intent.getStringExtra("sellid");
            //price= Double.parseDouble(intent.getStringExtra("price"));
            price = getIntent().getDoubleExtra("price", 0.0);
            con=intent.getStringExtra("con");
            pul=intent.getStringExtra("pul");

        }

        if(pul!=null) {
            Glide.with(this).load(pul).timeout(6000).
                    override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
        }
        textViewn.setText(con);
        textViewp.setText(String.valueOf(price));
        cardView2.setOnClickListener(view -> {

            post();

        });




    }
    private void post(){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/tran/trading";
            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("sellerId", sellid);
            bodyMap.put("goodsId", sid);
            bodyMap.put("price", price);
            bodyMap.put("buyerId", User.getId());
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
                       ResponseBody responseModel = gson.fromJson(body, ResponseBody.class);
                        System.out.println(responseModel.toString());
                        if(responseModel.getCode()==200){


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this, "购买成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            User.setMoney(User.getMoney()-(int)price);

                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                            startActivity(intent);

                        }
                        else {
                            final String errorMessage = "购买失败，不能购买自己发布的东西或者余额不足";

                            // 在主线程中显示 Toast
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(DetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }



                    }
                });
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
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
