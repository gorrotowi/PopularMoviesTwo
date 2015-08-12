package com.gorrotowi.popularmoviestwo.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gorrotowi.popularmoviestwo.R;
import com.gorrotowi.popularmoviestwo.entitys.ItemTrailerMovie;

import java.util.ArrayList;

/**
 * Created by gorro on 10/08/15.
 */
public class AdapterTrailerMovie extends BaseAdapter {

    private Context context;
    private ArrayList<ItemTrailerMovie> data = new ArrayList<>();

    public AdapterTrailerMovie(Context context, ArrayList<ItemTrailerMovie> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class HolderView {
        TextView txtTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fila = convertView;
        HolderView holder;
        if (fila == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            fila = inflater.inflate(R.layout.recycler_video_item, parent, false);
            holder = new HolderView();
            holder.txtTitle = (TextView) fila.findViewById(R.id.txtVideoItem);
            fila.setTag(holder);
        } else {
            holder = (HolderView) fila.getTag();
        }

        final ItemTrailerMovie item = data.get(position);
        holder.txtTitle.setText(item.getTitle());

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.getYoutubeId())));
            }
        });

        return fila;
    }
}
