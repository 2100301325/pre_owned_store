package com.example.ggapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ggapplication.data.Record;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<Record> {

    private List<Record> mData;
    private Context mContext;
    private int resourceId;

    public OrderAdapter(Context context,
                       int resourceId, List<Record> data) {
        super(context, resourceId, data);
        this.mContext = context;
        this.mData = data;
        this.resourceId = resourceId;
    }

    @Override
    public View getView(int position,
                        View convertView, ViewGroup parent) {
        Record record = getItem(position);
        View view ;

        final ViewHolder vh;

        if (convertView == null) {
            view = LayoutInflater.from(getContext())
                    .inflate(resourceId, parent, false);

            vh = new ViewHolder();
//            vh.tvDescription = view.findViewById(R.id.tv_description);

            vh.tvTitle  = view.findViewById(R.id.tv_title);
            vh.ivImage = view.findViewById(R.id.imageorder);
            vh.tvPrice= view.findViewById(R.id.priceo);


            view.setTag(vh);
        } else {
            view = convertView;
            vh = (ViewHolder) view.getTag();
        }

            vh.tvTitle.setText(record.getGoodsDescription());
            vh.tvPrice.setText("￥"+String.valueOf(record.getPrice()));
        if(record.getImageUrlList() != null && !record.getImageUrlList().isEmpty()) {
            Glide.with(mContext).load(record.getImageUrlList().get(0))
                    .into(vh.ivImage);

        }
        else {
            Glide.with(mContext).load(R.drawable.mkong)
                    .into(vh.ivImage);
        }




        return view;
    }

    class ViewHolder {

        TextView tvTitle;
        TextView tvPrice;
        ImageView ivImage;

    }



}

