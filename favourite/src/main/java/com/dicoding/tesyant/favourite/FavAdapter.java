package com.dicoding.tesyant.favourite;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.tesyant.favourite.model.CustomItemClickListener;
import com.dicoding.tesyant.favourite.model.search.Results;

import java.util.List;

/**
 * Created by tesyant on 10/14/17.
 */

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.MyViewHolder> {

    private List<Results> results;
    private Activity activity;

    CustomItemClickListener customItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView cover;
        public TextView title, releaseDate;

        public MyViewHolder(View view) {
            super(view);
            cover = (ImageView) view.findViewById(R.id.img_cover);
            title = (TextView) view.findViewById(R.id.tv_title);
            releaseDate = (TextView) view.findViewById(R.id.tv_release_date);
        }
    }

    public FavAdapter(List<Results> results, Activity activity, CustomItemClickListener listener) {
        this.results = results;
        this.activity = activity;
        this.customItemClickListener = listener;
    }

    @Override
    public FavAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favourite, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customItemClickListener.onItemClick(view, myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(FavAdapter.MyViewHolder holder, int position) {
        holder.title.setText("" + results.get(position).getTitle());
        holder.releaseDate.setText("" + results.get(position).getReleaseDate());
        Glide.with(activity).load("http://image.tmdb.org/t/p/w185" + results.get(position).getPosterPath())
                .fitCenter().into(holder.cover);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
