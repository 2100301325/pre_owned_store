package com.example.ggapplication;

import android.os.Bundle;

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




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        textView=rootView.findViewById(R.id.my_money);
        textView.setText(String.valueOf(User.getMoney()));
        return rootView;
    }
}