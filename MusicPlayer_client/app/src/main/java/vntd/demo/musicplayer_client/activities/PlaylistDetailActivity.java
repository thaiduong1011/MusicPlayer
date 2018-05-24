package vntd.demo.musicplayer_client.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.adapter.CommentAdapter;
import vntd.demo.musicplayer_client.adapter.SongAdapter;
import vntd.demo.musicplayer_client.asynctask.AsyncPlaylist_Detail;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.util.RecyclerItemTouchHelper;

public class PlaylistDetailActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView songRecyclerView;
    private SongAdapter songAdapter;
    private RecyclerView.LayoutManager songLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_detail);

        AnhXa();

        showList();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(songRecyclerView);

    }

    void AnhXa(){
        songRecyclerView = findViewById(R.id.my_recycler_view);
        songRecyclerView.setHasFixedSize(true); // tối ưu hóa, không bị ảnh hưởng nội dung trong adapter
        songLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        songRecyclerView.setLayoutManager(songLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        songRecyclerView.addItemDecoration(dividerItemDecoration);
        songRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    void showList(){
        new AsyncPlaylist_Detail(this, this, songRecyclerView, songAdapter).execute(Data.USER_ID +"", Data.PLAYLIST_ID +"");
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CommentAdapter.ViewHolder) {
            int pos = viewHolder.getAdapterPosition();
            Log.d("AA", Data.PLAYLIST_ID + "  " + Data.SONG_ID_SELECTED);
            //new AsyncRemoveSongOfPlaylist(this, pos, songAdapter).execute(Data.PLAYLIST_ID +"", Data.SONG_ID_SELECTED +"");

        }
    }
}
