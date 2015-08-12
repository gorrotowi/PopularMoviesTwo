package com.gorrotowi.popularmoviestwo;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MoviesFragment fragment = new MoviesFragment();
            MovieDetailActivityFragment fragmentDetail = new MovieDetailActivityFragment();
            Bundle arguments = new Bundle();
            arguments.putBoolean("moviesAlone", isMoviesAlone());
            fragment.setArguments(arguments);
            transaction.replace(R.id.recycler_content_fragment, fragment);
            if (!isMoviesAlone()) {
                transaction.replace(R.id.movie_content_fragment, fragmentDetail);
            }
            transaction.commit();
        }

    }

    private boolean isMoviesAlone() {
        if (findViewById(R.id.movie_content_fragment) == null) {
            return true;
        } else {
            return false;
        }
    }
}
