package com.example.ggapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.Headers;
import com.example.ggapplication.data.Record;
import com.example.ggapplication.data.YourResponseModel;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

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

public class LoginActivity extends AppCompatActivity {
    private EditText etPwd;
    private EditText etAccount;
    private CheckBox cbRememberPwd;
    private Boolean bPwdSwitch = false;
    private MaterialButton dlButton;
    private MaterialButton zcButton;
    private final Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final ImageView ivPwdSwitch = findViewById(R.id.iv_pwd_switch);
        etPwd = findViewById(R.id.et_pwd);
        etAccount = findViewById(R.id.et_account);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;
                if (bPwdSwitch) {
                    ivPwdSwitch.setImageResource(
                            R.drawable.baseline_visibility_24);
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivPwdSwitch.setImageResource(
                            R.drawable.baseline_visibility_off_24);
                    etPwd.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    etPwd.setTypeface(Typeface.DEFAULT);
                }


                String spFileName = getResources()
                        .getString(R.string.shared_preferences_file_name);
                String accountKey = getResources()
                        .getString(R.string.login_account_name);
                String passwordKey =  getResources()
                        .getString(R.string.login_password);
                String rememberPasswordKey = getResources()
                        .getString(R.string.login_remember_password);

                SharedPreferences spFile = getSharedPreferences(
                        spFileName,
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = spFile.edit();

                if (cbRememberPwd.isChecked()) {
                    String password = etPwd.getText().toString();
                    String account = etAccount.getText().toString();

                    editor.putString(accountKey, account);
                    editor.putString(passwordKey, password);
                    editor.putBoolean(rememberPasswordKey, true);
                    editor.apply();
                } else {
                    editor.remove(accountKey);
                    editor.remove(passwordKey);
                    editor.remove(rememberPasswordKey);
                    editor.apply();
                }


            }

        });
        String spFileName = getResources()
                .getString(R.string.shared_preferences_file_name);
        String accountKey = getResources()
                .getString(R.string.login_account_name);
        String passwordKey =  getResources()
                .getString(R.string.login_password);
        String rememberPasswordKey = getResources()
                .getString(R.string.login_remember_password);

        SharedPreferences spFile = getSharedPreferences(
                spFileName,
                MODE_PRIVATE);
        String account = spFile.getString(accountKey, null);
        String password = spFile.getString(passwordKey, null);
        Boolean rememberPassword = spFile.getBoolean(
                rememberPasswordKey,
                false);

        if (account != null && !TextUtils.isEmpty(account)) {
            etAccount.setText(account);
        }

        if (password != null && !TextUtils.isEmpty(password)) {
            etPwd.setText(password);
        }

        cbRememberPwd.setChecked(rememberPassword);

dlButton=findViewById(R.id.btn_dl);
zcButton=findViewById(R.id.btn_zc);
zcButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        // 创建一个Intent，将当前活动（登录页面）与注册页面的活动关联
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        // 启动注册页面的活动
        startActivity(intent);
    }});
dlButton.setOnClickListener(v -> {

    post();
//    Intent intent=  new Intent(this,MainActivity.class);
//    startActivity(intent);
});




    }
    private void post(){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/tran/user/login";
            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("password",etPwd.getText().toString());
            bodyMap.put("username", etAccount.getText().toString());
            // 将Map转换为字符串类型加入请求体中
            String body = gson.toJson(bodyMap);

            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .addHeader("appId","f6ae524bd7054994ae26685c3ff1045e")
                    .addHeader("appSecret", "51378b6ce0077fa3244d08bda02d921dff3fb")
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
                  if(responseModel.getCode()==200){
                      Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                          startActivity(intent);

                  }
                  else {
                      final String errorMessage = "登录失败，请检查用户名和密码";

                      // 在主线程中显示 Toast
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
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

}

