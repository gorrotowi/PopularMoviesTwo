package com.gorrotowi.popularmoviestwo.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gorrotowi.popularmoviestwo.MovieDetailActivity;
import com.gorrotowi.popularmoviestwo.MovieDetailActivityFragment;
import com.gorrotowi.popularmoviestwo.R;
import com.gorrotowi.popularmoviestwo.entitys.ItemImgMovie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by gorro on 19/07/15.
 */
public class AdapterMovies extends RecyclerView.Adapter<AdapterMovies.MoviewViewHolder> {

    List<ItemImgMovie> itemImgMovies;
    Context context;
    boolean detailFragment;
    FragmentManager fragmentManager;

    public AdapterMovies(List<ItemImgMovie> item, FragmentActivity activity, boolean isAlone, FragmentManager fragmentManager) {
        this.itemImgMovies = item;
        this.context = activity;
        this.detailFragment = isAlone;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MoviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MoviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MoviewViewHolder holder, final int position) {
        Picasso.with(context)
                .load(context.getString(R.string.base_img_url) + itemImgMovies.get(position).getImgpath())
//                .placeholder(R.drawable.abc_btn_check_to_on_mtrl_015)
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailFragment) {
                    context.startActivity(new Intent(context, MovieDetailActivity.class).putExtra("jsondata", itemImgMovies.get(position).getJsonMovie().toString()));
                } else {
                    MovieDetailActivityFragment fragment = new MovieDetailActivityFragment();
                    Bundle arguments = new Bundle();
                    arguments.putString("jsondata", itemImgMovies.get(position).getJsonMovie().toString());
                    fragment.setArguments(arguments);
                    fragmentManager.beginTransaction().replace(R.id.movie_content_fragment, fragment).commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemImgMovies.size();
    }

    public static class MoviewViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MoviewViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_movie);
        }
    }

}
