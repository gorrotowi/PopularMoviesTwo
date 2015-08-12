package com.gorrotowi.popularmoviestwo.entitys;

/**
 * Created by gorro on 10/08/15.
 */
public class ItemReviewMovie {

    String name, review;

    public ItemReviewMovie(String name, String review) {
        this.name = name;
        this.review = review;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
