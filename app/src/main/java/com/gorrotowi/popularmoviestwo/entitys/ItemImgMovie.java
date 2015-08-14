package com.gorrotowi.popularmoviestwo.entitys;

import org.json.JSONObject;

/**
 * Created by gorro on 19/07/15.
 */
public class ItemImgMovie {

    String imgpath;
    byte[] imgBAr;
    JSONObject jsonMovie;

    public ItemImgMovie(String imgpath, JSONObject jsonMovie) {
        this.imgpath = imgpath;
        this.jsonMovie = jsonMovie;
    }

    public ItemImgMovie(byte[] imgBAr, JSONObject jsonMovie) {
        this.imgBAr = imgBAr;
        this.jsonMovie = jsonMovie;
    }

    public byte[] getImgBAr() {
        return imgBAr;
    }

    public void setImgBAr(byte[] imgBAr) {
        this.imgBAr = imgBAr;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public JSONObject getJsonMovie() {
        return jsonMovie;
    }

    public void setJsonMovie(JSONObject jsonMovie) {
        this.jsonMovie = jsonMovie;
    }
}
