package com.tif.tesyant.mov.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tif.tesyant.mov.R;
import com.tif.tesyant.mov.model.Results;

import java.util.List;


/**
 * Created by tesyant on 9/21/17.
 */

public class MovieAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Activity activity;
    private List<Results> results;
    private LayoutInflater layoutInflater;

    public MovieAdapter(List<Results> res, Activity activity) {
        this.results = res;
        this.activity = activity;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int i) {
        return results.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        holder = new ViewHolder();
        view = layoutInflater.inflate(R.layout.activity_list_movie, null);
        holder.title = (TextView) view.findViewById(R.id.tv_title);
        holder.releasedate = (TextView) view.findViewById(R.id.tv_release_date);
        holder.image = (ImageView) view.findViewById(R.id.img_cover);
        holder.title.setText(results.get(position).getTitle());
        holder.releasedate.setText(results.get(position).getReleaseDate());
        Glide.with(activity).load("http://image.tmdb.org/t/p/w185" + results.get(position).getPosterPath()).fitCenter().into(holder.image);
        view.setTag(holder);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("hello", "fuckers");
        Toast.makeText(activity.getApplicationContext(), "Item Postition = " + i, Toast.LENGTH_SHORT).show();
    }

    private class ViewHolder {
        public TextView title, releasedate;
        public ImageView image;
    }
}