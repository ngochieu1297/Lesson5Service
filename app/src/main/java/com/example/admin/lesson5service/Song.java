package com.example.admin.lesson5service;

public class Song {
    private String mName;
    private String mArtist;
    private int mResIđ;

    public Song(String name, String artist, int resIđ) {
        mName = name;
        mArtist = artist;
        mResIđ = resIđ;
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

    public int getFile() {
        return mResIđ;
    }
}
