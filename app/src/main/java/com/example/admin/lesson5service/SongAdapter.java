package com.example.admin.lesson5service;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    private List<Song> mSongs;
    private static OnItemClickListener mListener;

    public SongAdapter(List<Song> songs, OnItemClickListener listener) {
        mSongs = songs;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_music, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(mSongs.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mSongs.size() > 0 ? mSongs.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextArtist, mTextSong;

        public ViewHolder(final View itemView) {
            super(itemView);
            mTextArtist = itemView.findViewById(R.id.text_name_artist);
            mTextSong = itemView.findViewById(R.id.text_name_song);
            itemView.setOnClickListener(this);
        }

        public void setData(final Song song, final OnItemClickListener listener) {
            mTextArtist.setText(song.getArtist());
            mTextSong.setText(song.getName());
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(view, this.getLayoutPosition());
        }
    }
}
