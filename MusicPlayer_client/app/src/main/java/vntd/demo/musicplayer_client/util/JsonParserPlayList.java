package vntd.demo.musicplayer_client.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vntd.demo.musicplayer_client.object.Playlist;

public class JsonParserPlayList {

    public static final String PARAM_PLAYLIST_ID = "playlist_id";
    public static final String PARAM_PLAYLIST_NAME ="playlist_name";


    public static List<Playlist> getPlaylist(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        List<Playlist> list = new ArrayList<>();
        JSONObject jsonObject;
        for(int i=0; i<array.length(); i++){
            jsonObject = array.getJSONObject(i);
            list.add(new Playlist(jsonObject.getInt(PARAM_PLAYLIST_ID),
                    jsonObject.getString(PARAM_PLAYLIST_NAME)
            ));
        }
        return list;
    }
}
