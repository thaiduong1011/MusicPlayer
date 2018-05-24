package vntd.demo.musicplayer_client.object;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private int playlistId;
    private String playlistName;
    private List<Song> songsOfPlaylist =  new ArrayList();

    public Playlist(String namePlaylist){
        playlistId = 0;
        playlistName = namePlaylist;
    }

    public List<Song> getSongsOfPlaylist() {
        return songsOfPlaylist;
    }

    public void setSongsOfPlaylist(List<Song> songsOfPlaylist) {
        this.songsOfPlaylist = songsOfPlaylist;
    }

    public void add(Song song){
        songsOfPlaylist.add(song);
    }


    public Playlist() {
    }

    public Playlist(int playlistId, String playlistName) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }
}
