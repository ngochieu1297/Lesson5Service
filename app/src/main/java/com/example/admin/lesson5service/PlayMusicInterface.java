package com.example.admin.lesson5service;

public interface PlayMusicInterface {
    void create(int index);

    void start();

    void pause();

    int getDuration();

    int getCurrentPosition();

    boolean isPlaying();

    void seek(int position);

    void loop(boolean isLoop);

    int getSong();

    void stopService();

    void changeSong(int i);
}
