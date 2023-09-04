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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity {

    private MaterialButton zccButton;
    private Boolean bPwdSwitch = false;
    private Boolean cbPwdSwitch = false;
    private MaterialButton fhButton;
    private EditText etSignupPassword;
    private EditText etSignupConfirmPassword;
    private EditText etSingupAccount;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinup);
        etSingupAccount=findViewById(R.id.et_signup_account);
        etSignupPassword = findViewById(R.id.et_signup_password);
        etSignupConfirmPassword = findViewById(R.id.et_signup_confirm_password);
        final ImageView ivPwdSwitch = findViewById(R.id.iv_signup_pwd_switch);
        final ImageView civPwdSwitch = findViewById(R.id.iv_signup_conpwd_switch);
        zccButton = findViewById(R.id.btn_zcc);
        fhButton = findViewById(R.id.btn_fh);
        ivPwdSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                bPwdSwitch = !bPwdSwitch;
                if (bPwdSwitch) {
                    ivPwdSwitch.setImageResource(
                            R.drawable.baseline_visibility_24);
                    etSignupPassword.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    ivPwdSwitch.setImageResource(
                            R.drawable.baseline_visibility_off_24);
                    etSignupPassword.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    etSignupPassword.setTypeface(Typeface.DEFAULT);
                }





            }

        });
        civPwdSwitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                cbPwdSwitch = !cbPwdSwitch;
                if (cbPwdSwitch) {
                    civPwdSwitch.setImageResource(
                            R.drawable.baseline_visibility_24);
                    etSignupConfirmPassword.setInputType(
                            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    civPwdSwitch.setImageResource(
                            R.drawable.baseline_visibility_off_24);
                    etSignupConfirmPassword.setInputType(
                            InputType.TYPE_TEXT_VARIATION_PASSWORD |
                                    InputType.TYPE_CLASS_TEXT);
                    etSignupConfirmPassword.setTypeface(Typeface.DEFAULT);
                }





            }

        });
        fhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 创建一个Intent，将当前活动（注册页面）与登录页面的活动关联
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                // 启动登录页面的活动
                startActivity(intent);

                // 结束当前的注册页面活动
                finish();
            }
        });
        zccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String password = etSignupPassword.getText().toString();
                String confirmPassword = etSignupConfirmPassword.getText().toString();

                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                    // 提示密码不能为空
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    // 提示密码不匹配
                    Toast.makeText(RegisterActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 注册成功后，可以跳转到登录页面或其他页面
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish(); // 结束当前注册页面，避免返回时再次看到注册页面
                post();


            }

        });
    }





    private void post(){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/tran/user/register";
            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("password",etSignupPassword.getText().toString());
            bodyMap.put("username", etSingupAccount.getText().toString());
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
                        System.out.println("失败"+ e.getMessage());
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
                            final String successMessage = "注册成功！";

                            // 在主线程中显示 Toast
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, successMessage, Toast.LENGTH_SHORT).show();
                                }
                            });

                            // 创建一个 Intent 返回到登录页面
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            final String errorMessage = " 注册失败，请检查用户名和密码";

                            // 在主线程中显示 Toast
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
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

