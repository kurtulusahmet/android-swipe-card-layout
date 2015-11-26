package com.kurtulusdev.androidswipecardlayout.library;

import android.graphics.drawable.Drawable;

/**
 * Created by kurtulusahmet on 25.11.2015.
 */
public class CardObject {
    private String title;
    private String image;
    private Drawable imageDrawable;
    private int imageResourceId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Drawable getImageDrawable() {
        return imageDrawable;
    }

    public void setImageDrawable(Drawable imageDrawable) {
        this.imageDrawable = imageDrawable;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
