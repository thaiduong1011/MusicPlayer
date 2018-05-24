package vntd.demo.musicplayer_client.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import vntd.demo.musicplayer_client.R;

public class AddSongToPlaylist extends FragmentActivity implements View.OnClickListener {

    Button btnComment;
    ListPlaylistFrag playlistFrag;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_song_to_playlist);

        AnhXa();
        btnComment.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    void AnhXa(){
        btnComment = findViewById(R.id.btnComment);
        btnClose = findViewById(R.id.btnClose);
        playlistFrag = new ListPlaylistFrag();
    }

    @Override
    public void onClick(View v) {
        if (v == btnComment){
            Toast.makeText(this, "cmt", Toast.LENGTH_SHORT).show(); //chua xu ly gi o day
        }else if(v == btnClose){
            finish();
        }
    }

}
