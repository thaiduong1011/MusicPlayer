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

public class AsyncRemovePlaylist extends AsyncTask<String, Void, Integer> {
    Context context;
    PlaylistAdapter playlistAdapter;
    int pos;

    public AsyncRemovePlaylist(Context context, int pos, PlaylistAdapter playlistAdapter) {
        this.context = context;
        this.playlistAdapter = playlistAdapter;
        this.pos = pos;
    }

    @Override
    protected Integer doInBackground(String... params) {
        URL url;
        HttpURLConnection conn = null;
        try {
            String url_name = Config.IPADDRESS + "playlists";

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("playlist_id", params[0]);
            String query = builder.build().getEncodedQuery();
            url_name += "?" + query;

            Log.d("AA", url_name);

            url = new URL(url_name);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("DELETE");

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

                return 1;
            } else {
                return -1;
            }

        } catch (MalformedURLException e) {
            Log.d("AA", "error 1 => " + e.getMessage().toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("AA", "error 2 => " + e.getMessage().toString());
            e.printStackTrace();
        } finally {
            if (conn != null) {
                // conn.disconnect();
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        Log.d("AA", result + " xoa");
        if (result == 1) {
            Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
            playlistAdapter.removeAt(pos);
        }else{
            Toast.makeText(context, "Failed, try again", Toast.LENGTH_SHORT).show();
        }


    }
}
