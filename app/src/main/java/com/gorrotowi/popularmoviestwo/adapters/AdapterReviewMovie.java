package com.gorrotowi.popularmoviestwo.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gorrotowi.popularmoviestwo.R;
import com.gorrotowi.popularmoviestwo.entitys.ItemReviewMovie;

import java.util.ArrayList;

/**
 * Created by gorro on 10/08/15.
 */
public class AdapterReviewMovie extends BaseAdapter {

    private Context context;
    private ArrayList<ItemReviewMovie> data = new ArrayList<>();

    public AdapterReviewMovie(Context context, ArrayList<ItemReviewMovie> data) {
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
        TextView txtName, txtReview;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fila = convertView;
        HolderView holder;
        if (fila == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            fila = inflater.inflate(R.layout.recycler_review_item, parent, false);
            holder = new HolderView();
            holder.txtName = (TextView) fila.findViewById(R.id.txtItemName);
            holder.txtReview = (TextView) fila.findViewById(R.id.txtItemReview);
            fila.setTag(holder);
        } else {
            holder = (HolderView) fila.getTag();
        }

        final ItemReviewMovie item = data.get(position);
        holder.txtName.setText(item.getName());
        holder.txtReview.setText(item.getReview());

        return fila;
    }
}
