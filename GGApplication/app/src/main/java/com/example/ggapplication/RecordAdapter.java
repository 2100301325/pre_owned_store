package com.example.ggapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.ggapplication.data.Record;

import java.util.List;

public class RecordAdapter extends BaseAdapter {
    private Context context;
    private List<Record> records;

    public RecordAdapter(Context context, List<Record> records) {
        this.context = context;
        this.records = records;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setData(List<Record> newData) {
        records.clear();
        records.addAll(newData);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false);
        }

        Record record = records.get(position);

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);
        TextView priceTextView = convertView.findViewById(R.id.priceTextView);
        if(record.getImageUrlList()!=null&&!record.getImageUrlList().isEmpty()) {
            Glide.with(context).load(record.getImageUrlList().get(0)).timeout(6000).
                    override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
        }

        descriptionTextView.setText(record.getContent().substring(0, Math.min(20, record.getContent().length())));

        priceTextView.setText("￥"+ record.getPrice());

        return convertView;
    }
}

