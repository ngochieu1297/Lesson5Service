package com.example.admin.lesson5service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<Song> mSongs;
    private OnItemClickListener mListener;
    private LayoutInflater mLayoutInflater;

    public SongAdapter(List<Song> songs, OnItemClickListener listener, Context context) {
        mSongs = songs;
        mListener = listener;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_music, parent, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mSongs.get(position));
    }

    @Override
    public int getItemCount() {
        return mSongs.size() > 0 ? mSongs.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextArtist;
        private TextView mTextSong;
        private OnItemClickListener mListener;

        public ViewHolder(final View itemView, final OnItemClickListener listener) {
            super(itemView);
            mTextArtist = itemView.findViewById(R.id.text_name_artist);
            mTextSong = itemView.findViewById(R.id.text_name_song);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        public void bindData(Song song) {
            mTextSong.setText(song.getName());
            mTextArtist.setText(song.getArtist());
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(this.getLayoutPosition());
        }
    }
}
