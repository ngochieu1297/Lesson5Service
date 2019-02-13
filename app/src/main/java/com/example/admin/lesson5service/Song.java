package com.example.admin.lesson5service;

public class Song {
    private String mName;
    private String mArtist;
    private int mResId;

    public Song(String name, String artist, int resId) {
        mName = name;
        mArtist = artist;
        mResId = resId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getArtist() {
        return mArtist;
    }

    public int getResId() {
        return mResId;
    }
}
