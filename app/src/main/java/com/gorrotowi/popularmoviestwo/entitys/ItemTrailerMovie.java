package com.gorrotowi.popularmoviestwo.entitys;

/**
 * Created by gorro on 10/08/15.
 */
public class ItemTrailerMovie {

    String title, youtubeId;

    public ItemTrailerMovie(String title, String youtubeId) {
        this.title = title;
        this.youtubeId = youtubeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYoutubeId() {
        return youtubeId;
    }

    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }
}
