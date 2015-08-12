package com.gorrotowi.popularmoviestwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        MovieDetailActivityFragment fragment = new MovieDetailActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("jsondata", getIntent().getExtras().getString("jsondata"));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_movie_detail_container, fragment).commit();

    }

}
