package com.tif.tesyant.mov.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tif.tesyant.mov.R;
import com.tif.tesyant.mov.model.Results;

import java.util.List;

/**
 * Created by tesyant on 9/29/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private List<Results> result;
    private Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, releasedate;
        public ImageView cover;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.tv_title);
            releasedate = (TextView)view.findViewById(R.id.tv_release_date);
            cover = (ImageView)view.findViewById(R.id.img_cover);
        }
    }

    public CustomAdapter(List<Results> result) {
        this.result = result;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Results search = result.get(position);
        holder.title.setText(search.getTitle());
        holder.releasedate.setText(search.getReleaseDate());
        Glide.with(activity).load("http://image.tmdb.org/t/p/w185" + result.get(position).getPosterPath())
                .fitCenter().into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }
}
