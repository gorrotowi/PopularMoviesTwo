package com.gorrotowi.popularmoviestwo.entitys;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gorro on 10/08/15.
 */
public class Movie extends RealmObject {


    @PrimaryKey private String id;

    private String jsonMovie;
    private String jsonTrailer;
    private String jsonReviewl;
    private byte[] imgPoster;

    public byte[] getImgPoster() {
        return imgPoster;
    }

    public void setImgPoster(byte[] imgPoster) {
        this.imgPoster = imgPoster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonMovie() {
        return jsonMovie;
    }

    public void setJsonMovie(String jsonMovie) {
        this.jsonMovie = jsonMovie;
    }

    public String getJsonTrailer() {
        return jsonTrailer;
    }

    public void setJsonTrailer(String jsonTrailer) {
        this.jsonTrailer = jsonTrailer;
    }

    public String getJsonReviewl() {
        return jsonReviewl;
    }

    public void setJsonReviewl(String jsonReviewl) {
        this.jsonReviewl = jsonReviewl;
    }
}
