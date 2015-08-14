package com.gorrotowi.popularmoviestwo.entitys;

import android.graphics.Bitmap;

/**
 * Created by gorro on 13/08/15.
 */
public enum ImgMovieSingleton {
    SINGLETON;

    public static Bitmap imgMovie;

    public static Bitmap getImgMovie() {
        return imgMovie;
    }

    public static void setImgMovie(Bitmap imgMovie) {
        ImgMovieSingleton.imgMovie = imgMovie;
    }
}
