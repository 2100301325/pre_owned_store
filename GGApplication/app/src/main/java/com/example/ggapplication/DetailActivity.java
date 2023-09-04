package com.example.ggapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

public class DetailActivity extends AppCompatActivity {
    private String sid;
    private String sellid;
    private double price=0.0;
    private String con;
    private String pul;
    private ImageView imageView;
    private TextView textViewn;
    private TextView textViewp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView=findViewById(R.id.spic);
        textViewn=findViewById(R.id.pro_name);
        textViewp=findViewById(R.id.pro_price);

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




    }
}
