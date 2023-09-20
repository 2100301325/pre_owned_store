package com.example.ggapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ggapplication.data.Data;
import com.example.ggapplication.data.Record;
import com.example.ggapplication.data.User;
import com.example.ggapplication.data.YourResponseModel;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrderDetailsActivity extends AppCompatActivity  {

    private List<Record> records;
    private Data data;
    private OrderAdapter adapter;
    private ListView listView;
    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        listView=findViewById(R.id.lv_news_list);
       records = new ArrayList<>();
        adapter = new OrderAdapter(OrderDetailsActivity.this,
                R.layout.list_item, records);
        listView.setAdapter(adapter);


        get();
    }

    private void get() {
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/tran/trading/buy?&&userId=" + User.getId();


            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .addHeader("appId", "231203e144a84d3197e91c792d6ed81b")
                    .addHeader("appSecret", "10557616300ac1c6d465191061d82db65f838")
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .get()
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
                        Gson gson = new Gson();

                        // Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
                        // 获取响应体的json串
                        String body = response.body().string();
                        Log.d("info", body);
                        YourResponseModel responseModel = gson.fromJson(body, YourResponseModel.class);

                        records.addAll(responseModel.getData().getRecords());
                        handler.post(new Runnable() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void run() {

                                // 设置新数据
                                adapter.notifyDataSetChanged(); // 通知适配器数据变化
                            }
                        });


//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                adapter.notifyDataSetChanged();
//                                // 通知适配器数据变化
//                            }
//                        });

                    }
                });
            } catch (NetworkOnMainThreadException ex) {
                ex.printStackTrace();
            }
        }).start();
    }


    /**
     * http响应体的封装协议
     *
     * @param <T> 泛型
     */
    public static class ResponseBody<T> {

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

        public ResponseBody() {
        }

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

