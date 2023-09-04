package com.example.ggapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ggapplication.data.Data;
import com.example.ggapplication.data.Record;
import com.example.ggapplication.data.YourResponseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

private List<Record> records;
private Data data;
    private RecordAdapter adapter;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void get(){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/tran/goods/all?userId=70&&size=10";


            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .addHeader("appId","231203e144a84d3197e91c792d6ed81b")
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
                        Gson gson=new Gson();

                       // Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
                        // 获取响应体的json串
                        String body = response.body().string();
                        Log.d("info", body);
                        YourResponseModel responseModel = gson.fromJson(body, YourResponseModel.class);
                         records.addAll(responseModel.getData().getRecords());
                        handler.post(new Runnable() {
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
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Record record= (Record) adapter.getItem(position);
        Intent intent=new Intent(getActivity(),DetailActivity.class);
        intent.putExtra("sid",record.getId());
        intent.putExtra("sellid",record.getTuserId());
        intent.putExtra("price",record.getPrice());
        intent.putExtra("con",record.getContent());
        if(record.getImageUrlList() != null && !record.getImageUrlList().isEmpty()) {
            intent.putExtra("pul",record.getImageUrlList().get(0));
        }





        startActivity(intent);
    }


    /**
     * http响应体的封装协议
     * @param <T> 泛型
     */
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        GridView gridView = rootView.findViewById(R.id.mygrid);
        records=new ArrayList<>();
       adapter = new RecordAdapter(getContext(), records);
       gridView.setAdapter(adapter);
        get();
        gridView.setOnItemClickListener(this::onItemClick);












        return rootView;
    }
}