package com.example.ggapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    private MaterialButton dlButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
dlButton=findViewById(R.id.btn_dl);
dlButton.setOnClickListener(v -> {
    Intent intent=  new Intent(this,MainActivity.class);
    startActivity(intent);
});




    }
}