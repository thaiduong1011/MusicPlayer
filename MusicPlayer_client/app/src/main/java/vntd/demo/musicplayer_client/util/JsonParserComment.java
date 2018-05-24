package vntd.demo.musicplayer_client.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vntd.demo.musicplayer_client.object.UserComment;

public class JsonParserComment {

    public static final String PARAM_USER_NAME = "user_name";
    public static final String PARAM_COMMENT = "comment";


    public static List<UserComment> getListComment(String json) throws JSONException {
        JSONArray array = new JSONArray(json);
        List<UserComment> list = new ArrayList<>();
        JSONObject jsonObject;
        String comment;
        for(int i=0; i<array.length(); i++){
            jsonObject = array.getJSONObject(i);
            comment = jsonObject.getString(PARAM_COMMENT);
            if (comment == "null")
                continue;

            list.add(new UserComment(jsonObject.getString(PARAM_USER_NAME),
                    comment
            ));
        }
        return list;
    }
}
