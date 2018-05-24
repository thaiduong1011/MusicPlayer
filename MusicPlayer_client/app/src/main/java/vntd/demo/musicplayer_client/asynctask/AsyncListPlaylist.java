package vntd.demo.musicplayer_client.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vntd.demo.musicplayer_client.Config;
import vntd.demo.musicplayer_client.adapter.PlaylistAdapter;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.object.Playlist;
import vntd.demo.musicplayer_client.util.JsonParserPlayList;

public class AsyncListPlaylist extends AsyncTask<Void, Void, List<Playlist>> {
    Context context;
    RecyclerView playlistRecyclerView;
    PlaylistAdapter playlistAdapter;
    List<String> playlistName;

    public AsyncListPlaylist(Context context, RecyclerView playlistRecyclerView, PlaylistAdapter playlistAdapter) {
        this.context = context;
        this.playlistRecyclerView = playlistRecyclerView;
        this.playlistAdapter = playlistAdapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }



    @Override
    protected List<Playlist> doInBackground(Void... voids) {
        URL url;
        HttpURLConnection conn = null;
        try {
            String url_name = Config.IPADDRESS + "playlists?user_id=" + Data.USER_ID;

            Log.d("AA", url_name);

            url = new URL(url_name);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            int response_code = conn.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK) {
                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                //Log.d("AA", "OK ne 2 " + result.toString());

                return JsonParserPlayList.getPlaylist(result.toString());
            } else {
                return null;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("AA", "error 1 => " + e.getMessage().toString());
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                // conn.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Playlist> result) {
        if (result != null) {
            Data.playlistOfUser = result;

            playlistName = new ArrayList<>();

            for (int i = 0; i < Data.playlistOfUser.size(); i++) {
                playlistName.add(Data.playlistOfUser.get(i).getPlaylistName());
            }
            playlistRecyclerView.setAdapter(playlistAdapter);
        }
    }
}
