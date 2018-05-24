package vntd.demo.musicplayer_client.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vntd.demo.musicplayer_client.object.Song;

public class JsonParserSong {
    public static final String PARAM_SONG_ID = "song_id";
    public static final String PARAM_SONG_NAME = "song_name";
    public static final String PARAM_SINGER_NAME ="artist_name";
    public static final String PARAM_TURN_NUM = "turn_num";
    public static final String PARAM_SHORT_DECRIPTION = "short_description";
    public static final String PARAM_CONTENT= "content";
    public static final String PARAM_SONG_PATH = "song_path";
    public static final String PARAM_SONG_IMAGE= "song_image";
    public static final String PARAM_SINGER_AVATAR = "artist_avatar";
    public static final String PARAM_RATING = "rating";


    public static List<Song> getListSong(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        List<Song> list = new ArrayList<>();
        JSONObject jsonObject;
        JSONArray arrayArtist;
        JSONArray arrayArtistAvatar;
        double rate = 0;
        for(int i=0; i<array.length(); i++){
            jsonObject = array.getJSONObject(i);
            arrayArtist = jsonObject.getJSONArray(PARAM_SINGER_NAME);
            arrayArtistAvatar = jsonObject.getJSONArray(PARAM_SINGER_AVATAR);
            if (jsonObject.getString(PARAM_RATING) == "null")
                rate = 0;
            else
                rate = jsonObject.getDouble(PARAM_RATING);
            list.add(new Song(jsonObject.getInt(PARAM_SONG_ID),
                    jsonObject.getString(PARAM_SONG_NAME),
                    arrayArtist.getString(0),
                    jsonObject.getInt(PARAM_TURN_NUM),
                    jsonObject.getString(PARAM_SONG_PATH),
                    jsonObject.getString(PARAM_SHORT_DECRIPTION),
                    jsonObject.getString(PARAM_CONTENT),
                    jsonObject.getString(PARAM_SONG_IMAGE),
                    arrayArtistAvatar.getString(0),
                    rate
            ));
        }
        return list;
    }
}
