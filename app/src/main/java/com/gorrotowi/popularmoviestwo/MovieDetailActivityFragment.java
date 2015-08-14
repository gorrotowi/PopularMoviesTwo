package com.gorrotowi.popularmoviestwo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gorrotowi.popularmoviestwo.adapters.AdapterReviewMovie;
import com.gorrotowi.popularmoviestwo.adapters.AdapterTrailerMovie;
import com.gorrotowi.popularmoviestwo.entitys.ImgMovieSingleton;
import com.gorrotowi.popularmoviestwo.entitys.ItemReviewMovie;
import com.gorrotowi.popularmoviestwo.entitys.ItemTrailerMovie;
import com.gorrotowi.popularmoviestwo.entitys.Movie;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    JSONObject jsonMovie;
    TextView txtTitle, txtDate, txtRate, txtDesc, txtIcon, txtFavMovie;
    ScrollView scrollViewContent;
    RelativeLayout relativeLayoutContainerTitle;
    ImageView imgMovie;
    RequestQueue rq;
    JsonObjectRequest jsonObjectRequest;
    AdapterTrailerMovie adapterTrailerMovie;
    AdapterReviewMovie adapterReviewMovie;
    ListView listViewTrailer, listViewReviews;
    JSONObject jsonTrailer, jsonReview;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        imgMovie = (ImageView) viewRoot.findViewById(R.id.img_movie_poster);
        txtTitle = (TextView) viewRoot.findViewById(R.id.txtTitle);
        txtDate = (TextView) viewRoot.findViewById(R.id.txtReleaseDate);
        txtRate = (TextView) viewRoot.findViewById(R.id.txtRate);
        txtDesc = (TextView) viewRoot.findViewById(R.id.txtDescr);
        txtIcon = (TextView) viewRoot.findViewById(R.id.icon_movie);
        txtFavMovie = (TextView) viewRoot.findViewById(R.id.txtFavMovie);
        scrollViewContent = (ScrollView) viewRoot.findViewById(R.id.content_detail);
        listViewTrailer = (ListView) viewRoot.findViewById(R.id.listTrailer);
        listViewReviews = (ListView) viewRoot.findViewById(R.id.listReviews);
        relativeLayoutContainerTitle = (RelativeLayout) viewRoot.findViewById(R.id.relativeLayoutContainerTitle);

        rq = Volley.newRequestQueue(getActivity());

        if (getArguments() != null) {
            scrollViewContent.setVisibility(View.VISIBLE);
            relativeLayoutContainerTitle.setVisibility(View.VISIBLE);
            txtIcon.setVisibility(View.GONE);
            if (getArguments().getString("jsontrailer") == null) {
                parseMovieDetail(getArguments().getString("jsondata"));
                try {
                    getDataMovie(jsonMovie.getString("id"), getString(R.string.endopoint_video));
                    getDataMovie(jsonMovie.getString("id"), getString(R.string.endopoint_review));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                parseMovieDetail(getArguments().getString("jsondata"));
                try {
                    parseTrailer(new JSONObject(getArguments().getString("jsontrailer")));
                    parseReviews(new JSONObject(getArguments().getString("jsonreview")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        txtFavMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgMovie.setDrawingCacheEnabled(true);
                imgMovie.buildDrawingCache();
                Bitmap bitmap = imgMovie.getDrawingCache();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                try {
                    Realm realm = Realm.getInstance(getActivity());
                    RealmResults<Movie> movies = realm.where(Movie.class).equalTo("id", jsonMovie.getString("id")).findAll();
                    if (movies.size() == 0) {
                        realm.beginTransaction();
                        Movie movie = realm.createObject(Movie.class);
                        movie.setJsonMovie(getArguments().getString("jsondata"));
                        movie.setJsonTrailer(jsonTrailer.toString());
                        movie.setJsonReviewl(jsonReview.toString());
                        movie.setId(jsonMovie.getString("id"));
                        movie.setImgPoster(stream.toByteArray());
                        realm.commitTransaction();
                        Toast.makeText(getActivity(), "Add to Favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "This film has already been added to Favorites", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return viewRoot;
    }

    private void parseMovieDetail(String json) {
        try {
            jsonMovie = new JSONObject(json);
            if (ImgMovieSingleton.getImgMovie() == null) {
                Log.e("bitmap url", ImgMovieSingleton.getImgMovie() + "");
                Picasso.with(getActivity()).load(getString(R.string.base_img_url) + jsonMovie.getString("poster_path")).into(imgMovie);
            } else {
                Log.e("bitmap", ImgMovieSingleton.getImgMovie() + "");
                imgMovie.setImageBitmap(ImgMovieSingleton.getImgMovie());
                ImgMovieSingleton.setImgMovie(null);
            }
            txtTitle.setText(jsonMovie.getString("title"));
            txtDate.setText(jsonMovie.getString("release_date"));
            txtRate.setText(jsonMovie.getString("vote_average") + "/10");
            txtDesc.setText(jsonMovie.getString("overview"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getDataMovie(String id, final String query) {
        String url = getString(R.string.base_url_api_movie) + id + query + getString(R.string.api_key);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, "", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (query.equals(getString(R.string.endopoint_video))) {
                    jsonTrailer = response;
                    parseTrailer(response);
                } else {
                    jsonReview = response;
                    parseReviews(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Check your internet connection " + query, Toast.LENGTH_SHORT).show();
            }
        });
        rq.add(jsonObjectRequest);
    }

    private void parseReviews(JSONObject response) {
        try {
            JSONArray results = response.getJSONArray("results");
            ArrayList<ItemReviewMovie> item = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                item.add(new ItemReviewMovie(results.getJSONObject(i).getString("author"), results.getJSONObject(i).getString("content")));
            }
            adapterReviewMovie = new AdapterReviewMovie(getActivity(), item);
            listViewReviews.setAdapter(adapterReviewMovie);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseTrailer(JSONObject response) {
        try {
            JSONArray results = response.getJSONArray("results");
            ArrayList<ItemTrailerMovie> item = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                item.add(new ItemTrailerMovie(results.getJSONObject(i).getString("name"), getString(R.string.base_url_youtube) + results.getJSONObject(i).getString("key")));
            }
            adapterTrailerMovie = new AdapterTrailerMovie(getActivity(), item);
            listViewTrailer.setAdapter(adapterTrailerMovie);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
