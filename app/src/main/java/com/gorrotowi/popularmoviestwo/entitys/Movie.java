package com.gorrotowi.popularmoviestwo.entitys;

import io.realm.RealmObject;

/**
 * Created by gorro on 10/08/15.
 */
public class Movie extends RealmObject {


    private String id;
    private String jsonMovie;
    private String jsonTrailer;
    private String jsonReviewl;

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
