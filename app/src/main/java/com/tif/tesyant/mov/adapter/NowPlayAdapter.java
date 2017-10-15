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
import com.tif.tesyant.mov.model.nowPlaying.Result;

import java.util.List;


/**
 * Created by tesyant on 9/21/17.
 */

public class NowPlayAdapter extends RecyclerView.Adapter<NowPlayAdapter.MyViewHolder> {

    private List<Result> result;
    private Activity activity;

    CustomItemClickListener listener;

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

    public NowPlayAdapter(List<Result> result, Activity activity, CustomItemClickListener listener) {
        this.result = result;
        this.activity=activity;
        this.listener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_movie, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, myViewHolder.getAdapterPosition() );
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        Results search = result.get(position);
        holder.title.setText("" + result.get(position).getTitle());
        holder.releasedate.setText("" + result.get(position).getReleaseDate());
        Glide.with(activity).load("http://image.tmdb.org/t/p/w185" + result.get(position).getPosterPath())
                .fitCenter().into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }
}
