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

public class AsyncRating extends AsyncTask<String, String, Integer> {

    Context context;

    public AsyncRating(Context context) {
        this.context = context;
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
            String url_name = Config.IPADDRESS + "rates";

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("user_id", params[0])
                    .appendQueryParameter("song_id", params[1])
                    .appendQueryParameter("rate", params[2]);
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
        Log.d("AA", result + "");
        Toast.makeText(context, "Thanks for your vote", Toast.LENGTH_SHORT).show();
    }
}
