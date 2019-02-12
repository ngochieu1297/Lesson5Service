package com.example.admin.lesson5service;

public class Music {
    private String mName;
    private String mArtist;
    private int mFile;

    public Music(String name, String artist, int file) {
        mName = name;
        mArtist = artist;
        mFile = file;
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
        return mFile;
    }

    public void setFile(int file) {
        mFile = file;
    }
}
