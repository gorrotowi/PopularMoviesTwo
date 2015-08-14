package com.gorrotowi.popularmoviestwo;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gorrotowi.popularmoviestwo.adapters.AdapterMovies;
import com.gorrotowi.popularmoviestwo.entitys.ItemImgMovie;
import com.gorrotowi.popularmoviestwo.entitys.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class MoviesFragment extends Fragment {

    RecyclerView rcMovies;
    AdapterMovies adapterMovies;
    RequestQueue rq;
    JsonObjectRequest jsonObjectRequest;
    int lastreq = 0;
    boolean isAlone;
    List<ItemImgMovie> item;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        rq = Volley.newRequestQueue(getActivity());

        setHasOptionsMenu(true);

        rcMovies = (RecyclerView) rootView.findViewById(R.id.recyclerMovies);
        isAlone = getArguments().getBoolean("moviesAlone");
        if (getResources().getConfiguration().orientation == 1) {
            rcMovies.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        } else {
            if (isAlone) {
                rcMovies.setLayoutManager(new GridLayoutManager(getActivity(), 5));
            } else {
                rcMovies.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            }
        }

        rcMovies.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = 0;
                outRect.top = 0;
                outRect.left = 0;
                outRect.right = 0;
            }
        });

        getMovies(getString(R.string.query_popularity));

        return rootView;
    }

    private void getMovies(final String queryMovie) {

        String url = getString(R.string.base_url_api) + queryMovie + "&" + getString(R.string.api_key);

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    item = new ArrayList<>();
                    JSONArray arrayMovies = response.getJSONArray("results");
                    for (int i = 0; i < arrayMovies.length(); i++) {
                        item.add(new ItemImgMovie(arrayMovies.getJSONObject(i).getString("poster_path"), arrayMovies.getJSONObject(i)));
                    }
                    adapterMovies = new AdapterMovies(item, getActivity(), isAlone, getFragmentManager());
                    rcMovies.setAdapter(adapterMovies);
                    rcMovies.setItemAnimator(new DefaultItemAnimator());

                    if (queryMovie.equals(getString(R.string.query_popularity))) {
                        lastreq = 0;
                    } else {
                        lastreq = 1;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(jsonObjectRequest);

    }

    private void retriveStorageMovies() {
        Realm realm = Realm.getInstance(getActivity());
        RealmResults<Movie> movies = realm.where(Movie.class).findAll();
        item.clear();
        for (int i = 0; i < movies.size(); i++) {
            try {
                JSONObject jsonMovie = new JSONObject(movies.get(i).getJsonMovie());
                item.add(new ItemImgMovie(movies.get(i).getImgPoster(), jsonMovie));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        rcMovies.removeAllViews();
        adapterMovies = new AdapterMovies(item, getActivity(), isAlone, getFragmentManager());
        rcMovies.setAdapter(adapterMovies);
        rcMovies.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_most_popular:
                if (lastreq == 1) {
                    getMovies(getString(R.string.query_popularity));
                }
                return true;
            case R.id.action_most_voted:
                if (lastreq == 0) {
                    getMovies(getString(R.string.query_mostVoted));
                }
                return true;
            case R.id.action_favorites:
//                    getMovies(getString(R.string.query_mostVoted));
                retriveStorageMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
