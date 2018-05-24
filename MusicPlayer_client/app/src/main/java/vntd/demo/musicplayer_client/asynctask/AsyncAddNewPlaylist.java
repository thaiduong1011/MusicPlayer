package vntd.demo.musicplayer_client.asynctask;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import vntd.demo.musicplayer_client.Config;
import vntd.demo.musicplayer_client.adapter.PlaylistAdapter;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.object.Playlist;

public class AsyncAddNewPlaylist extends AsyncTask<String, String, Integer> {
    Context context;
    String newPlaylistName;
    PlaylistAdapter adapter;

    public AsyncAddNewPlaylist(Context context,PlaylistAdapter adapter, String newPlaylistName) {
        this.context = context;
        this.newPlaylistName = newPlaylistName;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        URL url;
        HttpURLConnection conn = null;
        try {
            String url_name = Config.IPADDRESS + "playlists";

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("user_id", params[0])
                    .appendQueryParameter("playlist_name", params[1]);
            String query = builder.build().getEncodedQuery();
            url_name += "?" + query;
            Log.d("AA", url_name);

            url = new URL(url_name);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

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

                return 1;
            } else {
                return -1;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("AA", "1 " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("AA", "2 " + e.getMessage());
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return -1;
    }

    @Override
    protected void onPostExecute(Integer result) {

        if (result == 1){
            Toast.makeText(context,"You just created " + newPlaylistName,Toast.LENGTH_SHORT).show();
            if (adapter != null)
                adapter.addItem(new Playlist(result, newPlaylistName), Data.playlistOfUser.size());

        }else {
            Toast.makeText(context,"Failed to create new playlist",Toast.LENGTH_SHORT).show();
        }
    }
}
