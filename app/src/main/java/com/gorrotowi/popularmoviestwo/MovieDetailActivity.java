package com.gorrotowi.popularmoviestwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (savedInstanceState == null) {
            MovieDetailActivityFragment fragment = new MovieDetailActivityFragment();
            Bundle bundle = new Bundle();
            bundle.putString("jsondata", getIntent().getExtras().getString("jsondata"));
            if (getIntent().getExtras().getString("jsontrailer")!=null){
                bundle.putString("jsontrailer", getIntent().getExtras().getString("jsontrailer"));
                bundle.putString("jsonreview", getIntent().getExtras().getString("jsonreview"));
            }
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_movie_detail_container, fragment).commit();
        }

    }

}
