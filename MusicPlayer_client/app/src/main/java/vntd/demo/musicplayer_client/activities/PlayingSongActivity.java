package vntd.demo.musicplayer_client.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.adapter.CommentAdapter;
import vntd.demo.musicplayer_client.asynctask.AsyncRating;
import vntd.demo.musicplayer_client.asynctask.AsyncShowComment;
import vntd.demo.musicplayer_client.asynctask.AsyncTurnNum;
import vntd.demo.musicplayer_client.object.Data;

public class PlayingSongActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtSongName, txtSingerName, txtTimeStart, txtTimeEnd;
    ImageButton btnNext, btnPrev, btnPlay, btnComment, btnRating;
    SeekBar seekBar;
    ImageView imgBg;
    RatingBar ratingBar, userRatingBar;

    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private RecyclerView.LayoutManager commentLayoutManager;

    int position = 0;
    static MediaPlayer mediaPlayer = new MediaPlayer();

    boolean isTheFirstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_song);

        AnhXa();

        if (mediaPlayer.isPlaying()) {
            Log.d("AA", "dang play");
            mediaPlayer.stop();
        }

        initMediaPlayer();
        if (isTheFirstStart == true) {
            // mediaPlayer.start();
            btnPlay.setImageResource(R.drawable.pause);

            setTimeEnd();
            updateTimeStart();
            isTheFirstStart = false;
        }

        btnComment.setOnClickListener(this);
        btnRating.setOnClickListener(this);

        btnPlay.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrev.setOnClickListener(this);
        userRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                new AsyncRating(getApplicationContext()).execute(Data.USER_ID +"", Data.currentList.get(position).getSongId() +"", rating +"");
                userRatingBar.setVisibility(View.GONE);
                btnRating.setVisibility(View.INVISIBLE);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    void AnhXa() {
        Intent intent = getIntent();
        position = intent.getIntExtra("id", 0);
        Log.d("AA", position + " vi tri");

        txtSongName = findViewById(R.id.txtSongName);
        txtSingerName = findViewById(R.id.txtSingerName);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        btnPlay = findViewById(R.id.btnPlay);
        btnComment = findViewById(R.id.btnComment);
        btnRating = findViewById(R.id.btnRating);
        userRatingBar = findViewById(R.id.SetRatingBar);

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setMax(5);
        ratingBar.setRating((float) Data.currentList.get(position).getRating());

        commentRecyclerView = findViewById(R.id.comment_recycler_view);
        commentRecyclerView.setHasFixedSize(true); // tối ưu hóa, không bị ảnh hưởng nội dung trong adapter
        commentLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        commentRecyclerView.setLayoutManager(commentLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        commentRecyclerView.addItemDecoration(dividerItemDecoration);
        commentRecyclerView.setItemAnimator(new DefaultItemAnimator());


        txtTimeStart = findViewById(R.id.timeStart);
        txtTimeEnd = findViewById(R.id.timeEnd);

        seekBar = findViewById(R.id.seekbar);

        imgBg = findViewById(R.id.imgBg);
    }

    void showList() {
        new AsyncShowComment(this, this, commentRecyclerView, commentAdapter).execute(Data.currentList.get(position).getSongId() +"");
    }


    void playSongPath(String url) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);

            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void initMediaPlayer() {

        //playSongPath(MainActivity.songList.get(position).getSongPath());
        Log.d("AA", Data.currentList.size() + " size");
        //Uri uri = Uri.parse(Data.currentList.get(position).getSongPath());
        //mediaPlayer = MediaPlayer.create(PlayingSongActivity.this, uri);
        playSongPath(Data.currentList.get(position).getSongPath());
        txtSongName.setText(Data.currentList.get(position).getSongName());
        txtSingerName.setText(Data.currentList.get(position).getSingerName());

        if (!Data.currentList.get(position).getSongImage().equals("null"))
            Picasso.get().load(Data.currentList.get(position).getSongImage()).into(imgBg);
        else if (!Data.currentList.get(position).getSingerAvatar().equals("null"))
            Picasso.get().load(Data.currentList.get(position).getSingerAvatar()).into(imgBg);

        new AsyncTurnNum().execute(Data.currentList.get(position).getSongId() + "");
        showList();
        btnRating.setVisibility(View.VISIBLE);
    }

    void setTimeEnd() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        txtTimeEnd.setText(dateFormat.format(mediaPlayer.getDuration()));

        //gan max seekbar
        seekBar.setMax(mediaPlayer.getDuration());
    }

    void updateTimeStart() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
                txtTimeStart.setText(dateFormat.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        position++;

                        if (position > Data.songListOfServer.size() - 1) {
                            position = 0;
                        }

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                        }

                        setTimeEnd();
                        updateTimeStart();
                    }
                });

                handler.postDelayed(this, 500);
            }
        }, 100);
    }


    @Override
    public void onClick(View view) {
        //bi che mất icon
        if (view == btnComment) {
            if (commentRecyclerView.getVisibility() == View.GONE) {
                commentRecyclerView.setVisibility(View.VISIBLE);
            }else
                commentRecyclerView.setVisibility(View.GONE);
        } else if (view == btnRating){
            if (userRatingBar.getVisibility() == View.GONE){
                userRatingBar.setVisibility(View.VISIBLE);
                //bla bla
            }else
                userRatingBar.setVisibility(View.GONE);
        }

            if (view == btnPlay) {

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
            } else if (view == btnNext) {
                position++;

                if (position > Data.currentList.size() - 1) {
                    position = 0;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                initMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);

            } else if (view == btnPrev) {
                position--;

                if (position < 0) {
                    position = Data.currentList.size() - 1;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                initMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
            }

        setTimeEnd();
        updateTimeStart();
    }
}
