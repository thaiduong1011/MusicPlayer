package vntd.demo.musicplayer_client.asynctask;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
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
import vntd.demo.musicplayer_client.adapter.CommentAdapter;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.object.UserComment;
import vntd.demo.musicplayer_client.util.JsonParserComment;

public class AsyncShowComment extends AsyncTask<String, Void, List<UserComment>> {
    Context context;
    Activity activity;
    RecyclerView commentRecyclerView;
    CommentAdapter commentAdapter;

    public AsyncShowComment(Context context, Activity activity, RecyclerView commentRecyclerView, CommentAdapter commentAdapter) {
        this.context = context;
        this.activity = activity;
        this.commentRecyclerView = commentRecyclerView;
        this.commentAdapter = commentAdapter;
    }

    @Override
    protected List<UserComment> doInBackground(String... params) {
        URL url;
        HttpURLConnection conn = null;
        try {
            String url_name = Config.IPADDRESS + "songs";

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("song_id", params[0]);
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

                return JsonParserComment.getListComment(result.toString());
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
    protected void onPostExecute(List<UserComment> comments) {
        super.onPostExecute(comments);
        Log.d("AA", comments.size() + " size");
        if (comments != null) {
            Data.commentList = comments;
            commentAdapter = new CommentAdapter(activity, context, R.layout.row_comment); // sua layout
            commentRecyclerView.setAdapter(commentAdapter);
        }else {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}