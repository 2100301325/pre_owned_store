package com.example.ggapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    private ImageView imageView;
    private MaterialButton dlButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        imageView=findViewById(R.id.myimg);
        String imagerUrl="https://static001.geekbang.org/infoq/50/5049bfc3c69e33978f077b54fdb95094.jpeg";
        Glide.with(this)
                .load(imagerUrl)
                .into(imageView);
dlButton=findViewById(R.id.btn_dl);
dlButton.setOnClickListener(v -> {
    Intent intent=  new Intent(this,MainActivity.class);
    startActivity(intent);
});




    }
}