package com.example.ggapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ggapplication.data.User;


public class MineFragment extends Fragment {
    private TextView textView;
     private CardView cardView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        textView=rootView.findViewById(R.id.my_money);
        cardView = rootView.findViewById(R.id.my_order);
        if (cardView != null) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 获取当前 Fragment 所在的 Activity
                    Activity activity = getActivity();

                    if (activity != null) {
                        // 创建一个 Intent，将当前 Activity 与订单详情 Activity 关联
                        Intent intent = new Intent(activity, OrderDetailsActivity.class);

                        // 启动订单详情 Activity
                        activity.startActivity(intent);
                    }
                }
            });
        }


        textView.setText(String.valueOf(User.getMoney()));
        return rootView;



    }
}