package vntd.demo.musicplayer_client.object;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<Song> songListOfServer = new ArrayList<>();
    public static List<Playlist> playlistOfUser = new ArrayList<>();

    public static int USER_ID = -1;
    public static String USER_NAME = "ABCD";
    public static int PLAYLIST_ID = 0;

    public static List<Song> currentList = new ArrayList<>();

    public static int SONG_ID_SELECTED = -1;

    public static List<UserComment> commentList = new ArrayList<>();

}
