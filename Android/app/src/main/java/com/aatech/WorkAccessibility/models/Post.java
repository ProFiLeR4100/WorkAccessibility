package com.aatech.WorkAccessibility.models;

import android.support.annotation.NonNull;

import org.androidannotations.annotations.EBean;

public class Post {
    public String title;
    public String date;
    public String shortText;
    public String imageURL;

    public Post(@NonNull String title, @NonNull String date, @NonNull String shortText, String imageURL) {
        this.title = title;
        this.date = date;
        this.shortText = shortText;
        this.imageURL = imageURL;
    }
}
