package vntd.demo.musicplayer_client.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.activities.PlaylistDetailActivity;
import vntd.demo.musicplayer_client.asynctask.AsyncAddSongToPlaylist;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.object.Playlist;
import vntd.demo.musicplayer_client.object.Song;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    Context context;
    int layout;
    static Activity activity;

    public PlaylistAdapter(Activity activity, Context context, int layout) {
        this.activity = activity;
        this.context = context;
        this.layout = layout;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView playlistName;
        public RelativeLayout viewBackground, viewForeground;

        public ViewHolder(final View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.txtPlaylistName);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data.PLAYLIST_ID = Data.playlistOfUser.get(getAdapterPosition()).getPlaylistId();

                    if(Data.SONG_ID_SELECTED == -1) {
                        Intent intent = new Intent(itemView.getContext(), PlaylistDetailActivity.class);
                        intent.putExtra("pos", getAdapterPosition());
                        itemView.getContext().startActivity(intent);
                    }else{
                        String songName = "";
                        for (Song song : Data.songListOfServer){
                            if (song.getSongId() == Data.SONG_ID_SELECTED){
                                songName = song.getSongName();
                                break;
                            }
                        }
                        new AsyncAddSongToPlaylist(itemView.getContext(),songName ,
                                Data.playlistOfUser.get(getAdapterPosition()).getPlaylistName())
                                .execute(Data.PLAYLIST_ID +"", Data.SONG_ID_SELECTED + "");
                    }

                }
            });
        }

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.playlistName.setText(Data.playlistOfUser.get(position).getPlaylistName());
    }

    @Override
    public int getItemCount() {
        return Data.playlistOfUser.size();
    }

    public void removeAt(int pos) {
        Data.playlistOfUser.remove(pos);
        notifyItemRemoved(pos);
    }
    public void addItem(Playlist playlist, int position) {
        Data.playlistOfUser.add(position, playlist);
        notifyItemInserted(position);
    }

}
