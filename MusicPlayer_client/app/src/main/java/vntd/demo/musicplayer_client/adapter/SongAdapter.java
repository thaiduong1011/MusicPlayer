package vntd.demo.musicplayer_client.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.activities.AddSongToPlaylist;
import vntd.demo.musicplayer_client.activities.PlayingSongActivity;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.object.Playlist;
import vntd.demo.musicplayer_client.object.Song;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    static Activity activity;
    List<Song> songs;
    Context context;
    int layout;

    public SongAdapter(Activity activity, List<Song> songs, Context context, int layout) {
        this.activity = activity;
        this.songs = songs;
        this.context = context;
        this.layout = layout;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView songname;
        public TextView singer;
        public TextView turnNum;
        public ImageButton btnMore;
        public ImageView imgSong;

        public ViewHolder(final View itemView) {
            super(itemView);

            songname = itemView.findViewById(R.id.SongName);
            singer = itemView.findViewById(R.id.Singer);
            turnNum = itemView.findViewById(R.id.TurnNum);
            btnMore = itemView.findViewById(R.id.btnMore);
            imgSong = itemView.findViewById(R.id.imgSong);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    Data.currentList = null;
                    if(activity.getLocalClassName().equals("activities.MainActivity")){
                        Data.currentList = Data.songListOfServer;
                    }else if (activity.getLocalClassName().equals("activities.PlaylistDetailActivity")){
                        for (Playlist playlist : Data.playlistOfUser) {
                            if (playlist.getPlaylistId() == Data.PLAYLIST_ID) {
                                Data.currentList = playlist.getSongsOfPlaylist();
                               // Data.SONG_ID_SELECTED = Data.currentList.get(getAdapterPosition()).getSongId();
                                break;
                            }
                        }
                    }

                    intent = new Intent(itemView.getContext(), PlayingSongActivity.class);
                    intent.putExtra("id", getAdapterPosition());
                    itemView.getContext().startActivity(intent);
                }
            });

            btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data.SONG_ID_SELECTED = Data.songListOfServer.get(getAdapterPosition()).getSongId();
                    Intent intent = new Intent(itemView.getContext(), AddSongToPlaylist.class);
                    itemView.getContext().startActivity(intent);
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
        holder.songname.setText(songs.get(position).getSongName());
        holder.singer.setText(songs.get(position).getSingerName());
        holder.turnNum.setText(songs.get(position).getTurnNum() + "");
        if (!songs.get(position).getSongImage().equals("null"))
            Picasso.get().load(songs.get(position).getSongImage()).into(holder.imgSong);
        else if (!songs.get(position).getSingerAvatar().equals("null"))
            Picasso.get().load(songs.get(position).getSingerAvatar()).into(holder.imgSong);
        else
            holder.imgSong.setImageResource(R.drawable.songicon);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public void removeAt(int pos) {
        Data.currentList.remove(pos);
        notifyItemRemoved(pos);
    }
}
