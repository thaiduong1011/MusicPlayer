package vntd.demo.musicplayer_client.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
import vntd.demo.musicplayer_client.object.Song;
import vntd.demo.musicplayer_client.util.JsonParserSong;

public class AsyncListSong extends AsyncTask<Void, Void, List<Song>> {
    Context context;
    RecyclerView songRecyclerView;
    RecyclerView.Adapter songAdapter;
    Activity activity;

    public AsyncListSong(Activity activity,Context context, RecyclerView songRecyclerView, RecyclerView.Adapter songAdapter) {
        this.activity = activity;
        this.songRecyclerView = songRecyclerView;
        this.songAdapter = songAdapter;
        this.context = context;
    }


    @Override
    protected List<Song> doInBackground(Void... voids) {
        URL url;
        HttpURLConnection conn = null;
        try {
            String url_name = Config.IPADDRESS + "songs";

            Log.d("AA", url_name);

            url = new URL(url_name);
            conn = (HttpURLConnection) url.openConnection();
            Log.d("AA", "Da toi dong 53");

            conn.setRequestMethod("GET");

            int response_code = conn.getResponseCode();
            Log.d("AA", response_code + "");

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
            Data.songListOfServer = songs;
            songAdapter = new SongAdapter(activity, Data.songListOfServer, context, R.layout.row_song);
            songRecyclerView.setAdapter(songAdapter);
        }else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}
