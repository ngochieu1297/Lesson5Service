package com.example.admin.lesson5service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        OnItemClickListener, MusicService.OnSyncActivityListerner, SeekBar.OnSeekBarChangeListener {
    public static final int NEXT_SONG = 1;
    public static final int PREVIOUS_SONG = -1;
    public static final int MESSAGE_DELAY = 500;
    public static List<Song> mSongs = new ArrayList<>();
    private RecyclerView mRecyclerMusic;
    private ImageButton mButtonPlay;
    private ImageButton mButtonPause;
    private ImageButton mButtonNext;
    private ImageButton mButtonPrev;
    private TextView mTextTitle;
    private TextView mCurrentSongTime;
    private TextView mDurationSongTime;
    private SeekBar mSeekBar;
    private MusicService mService;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentIndex = mService.getCurrentIndex();
            int currentPosition = mService.getCurrentPosition();
            mSeekBar.setProgress(currentPosition);
            mTextTitle.setText(mSongs.get(currentIndex).getName() + " - "
                    + mSongs.get(currentIndex).getArtist());
            mCurrentSongTime.setText(TimeUtils.convertMilisecondToFormatTime(currentPosition));
            mHandler.sendMessageDelayed(new Message(), MESSAGE_DELAY);
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) iBinder;
            mService = binder.getService();
            mService.setSyncSeekbarListerner(MainActivity.this);
            syncSeekbar(mService.getDuration());
            syncNotification(mService.isPlaying());
            mHandler.sendMessageDelayed(new Message(), MESSAGE_DELAY);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            unbindService(mConnection);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addData();
        initViews();
        addControl();
        Intent intent = new Intent(this, MusicService.class);
        if (mService == null) {
            startService(intent);
        }
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private void addData() {
        mSongs.clear();
        mSongs.add(new Song("Chuyện anh vẫn chưa kể", "Chi Dân", R.raw.chuyen_anh_van_chua_ke));
        mSongs.add(new Song("Chuyến đi của năm", "Soobin Hoàng Sơn", R.raw.chuyen_di_cua_nam));
        mSongs.add(new Song("Đừng xin lỗi nữa", "Erik x Min", R.raw.dung_xin_loi_nua));
        mSongs.add(new Song("Kém duyên", "Rum x Nit x Masew", R.raw.kem_duyen));
        mSongs.add(new Song("Người lạ ơi", "Karik x Orange", R.raw.nguoi_la_oi));
    }

    private void initViews() {
        mRecyclerMusic = findViewById(R.id.recycler_music);
        initRecyclerMusic();
        mButtonNext = findViewById(R.id.image_button_next);
        mButtonPrev = findViewById(R.id.image_button_prev);
        mButtonPlay = findViewById(R.id.image_button_play);
        mButtonPause = findViewById(R.id.image_button_pause);
        mTextTitle = findViewById(R.id.text_title);
        mCurrentSongTime = findViewById(R.id.text_time_start);
        mDurationSongTime = findViewById(R.id.text_time_end);
        mSeekBar = findViewById(R.id.seek_bar_song);
    }

    private void initRecyclerMusic() {
        mRecyclerMusic.setHasFixedSize(true);
        SongAdapter musicAdapter = new SongAdapter(mSongs, this, this);
        mRecyclerMusic.setAdapter(musicAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerMusic.setLayoutManager(layoutManager);
    }

    private void addControl() {
        mButtonPlay.setOnClickListener(this);
        mButtonPause.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        mButtonPrev.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_button_next:
                mService.changeSong(NEXT_SONG);
                mService.updateNotification();
                mButtonPause.setVisibility(View.VISIBLE);
                mButtonPlay.setVisibility(View.GONE);
                break;
            case R.id.image_button_prev:
                mService.changeSong(PREVIOUS_SONG);
                mService.updateNotification();
                mButtonPause.setVisibility(View.VISIBLE);
                mButtonPlay.setVisibility(View.GONE);
                break;
            case R.id.image_button_play:
                startMediaPlayer();
                mService.updateNotification();
                mButtonPlay.setVisibility(View.GONE);
                mButtonPause.setVisibility(View.VISIBLE);
                break;
            case R.id.image_button_pause:
                mService.pause();
                mService.updateNotification();
                mButtonPlay.setVisibility(View.VISIBLE);
                mButtonPause.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(int position) {
        mService.create(position);
        mService.start();
        mService.updateNotification();
        mButtonPause.setVisibility(View.VISIBLE);
        mButtonPlay.setVisibility(View.GONE);
    }

    @Override
    public void syncSeekbar(int max) {
        mSeekBar.setMax(max);
        mDurationSongTime.setText(TimeUtils.convertMilisecondToFormatTime(max));
    }

    @Override
    public void syncNotification(boolean isPlaying) {
        if (isPlaying) {
            mButtonPause.setVisibility(View.VISIBLE);
            mButtonPlay.setVisibility(View.GONE);
        } else {
            mButtonPause.setVisibility(View.GONE);
            mButtonPlay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
        if (fromUser) {
            mService.seek(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    private void startMediaPlayer() {
        if (mService.getMediaPlayer() == null) {
            mService.create(0);
            mService.start();
        } else {
            mService.start();
        }
    }
}
