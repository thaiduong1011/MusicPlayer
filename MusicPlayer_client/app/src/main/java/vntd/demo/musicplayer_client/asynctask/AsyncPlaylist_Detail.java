package vntd.demo.musicplayer_client.asynctask;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
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
import java.util.List;

import vntd.demo.musicplayer_client.Config;
import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.adapter.SongAdapter;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.object.Playlist;
import vntd.demo.musicplayer_client.object.Song;
import vntd.demo.musicplayer_client.util.JsonParserSong;

public class AsyncPlaylist_Detail extends AsyncTask<String, Void, List<Song>> {
    Context context;
    RecyclerView songRecyclerView;
    RecyclerView.Adapter songAdapter;
    Activity activity;

    public AsyncPlaylist_Detail(Activity activity, Context context, RecyclerView songRecyclerView, RecyclerView.Adapter songAdapter) {
        this.songRecyclerView = songRecyclerView;
        this.songAdapter = songAdapter;
        this.context = context;
        this.activity = activity;
    }


    @Override
    protected List<Song> doInBackground(String... params) {
        URL url;
        HttpURLConnection conn = null;
        try {
            String url_name = Config.IPADDRESS + "songs";

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("user_id", params[0])
                    .appendQueryParameter("playlist_id", params[1]);
            String query = builder.build().getEncodedQuery();
            url_name += "?" + query;

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

                return JsonParserSong.getListSong(result.toString());
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
    protected void onPostExecute(List<Song> songs) {
        super.onPostExecute(songs);

        if (songs != null) {
            for (Playlist playlist : Data.playlistOfUser) {
                if (playlist.getPlaylistId() == Data.PLAYLIST_ID) {
                    playlist.setSongsOfPlaylist(songs);
                    songAdapter = new SongAdapter(activity, playlist.getSongsOfPlaylist(), context, R.layout.row_song);
                    songRecyclerView.setAdapter(songAdapter);
                    return;
                }
            }
        }

    }
}

