package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> {
    private List<MusicList> list;
    private int playingPosition = 0;
    private final SongChangeListener songChangeListener;

    public MusicAdapter(List<MusicList> list, Context context) {
        this.list = list;
        this.songChangeListener = ((SongChangeListener) context);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public MusicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.music_adapter_layout,null));
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MusicList list2 = list.get(position);

        if(list2.isPlaying()){
            playingPosition = 0;
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_blue);
        }
        else{
            holder.rootLayout.setBackgroundResource(R.drawable.round_back_10);
        }

        String generateDuration = String.format(Locale.getDefault(),"%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration())),TimeUnit.MILLISECONDS.toSeconds(Long.parseLong(list2.getDuration()))-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(list2.getDuration()))));

        holder.title.setText(list2.getTitle());
        holder.artist.setText(list2.getArtist());
        holder.mDuration.setText(generateDuration);


        holder.rootLayout.setOnClickListener(view -> {
            list.get(playingPosition).setPlaying(false);
            list2.setPlaying(true);
            songChangeListener.onChanged(position);
            notifyDataSetChanged();
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<MusicList> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        private final RelativeLayout rootLayout;
        private final TextView title;
        private final TextView artist;
        private final TextView mDuration;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rootLayout = itemView.findViewById(R.id.rootLayout);
            title = itemView.findViewById(R.id.musicTitle);
            artist = itemView.findViewById(R.id.musicArtist);
            mDuration = itemView.findViewById(R.id.musicDuration);
        }
    }
}
