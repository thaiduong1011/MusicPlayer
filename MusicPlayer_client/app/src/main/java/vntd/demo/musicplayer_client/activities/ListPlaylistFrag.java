package vntd.demo.musicplayer_client.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.adapter.PlaylistAdapter;
import vntd.demo.musicplayer_client.asynctask.AsyncListPlaylist;
import vntd.demo.musicplayer_client.asynctask.AsyncRemovePlaylist;
import vntd.demo.musicplayer_client.dialog.DialogAddPlaylist;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.util.RecyclerItemTouchHelper;

public class ListPlaylistFrag extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView playlistRecyclerView;
    private PlaylistAdapter playlistAdapter;
    private RecyclerView.LayoutManager playlistLayoutManager;

    Button btnCreateNewPl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listplaylist, container, false);

        AnhXa(view);

        btnCreateNewPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogAddPlaylist dialogAddPlaylist = new DialogAddPlaylist(getContext(), playlistAdapter);
                dialogAddPlaylist.showDialog(getActivity());
            }
        });


        return view;
    }

    void AnhXa(View v){
        btnCreateNewPl = v.findViewById(R.id.btnCreateNewPlaylist);

        playlistAdapter = new PlaylistAdapter(getActivity(), getContext(), R.layout.row_playlist);

        playlistRecyclerView = v.findViewById(R.id.playlist_recycler_view);
        playlistRecyclerView.setHasFixedSize(true); // tối ưu hóa, không bị ảnh hưởng nội dung trong adapter
        playlistLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        playlistRecyclerView.setLayoutManager(playlistLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        playlistRecyclerView.addItemDecoration(dividerItemDecoration);
        playlistRecyclerView.setItemAnimator(new DefaultItemAnimator());

        showPlaylist();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(playlistRecyclerView);
    }

    void showPlaylist(){
        new AsyncListPlaylist(getContext(), playlistRecyclerView, playlistAdapter).execute();
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof PlaylistAdapter.ViewHolder) {
            int pos = viewHolder.getAdapterPosition();
            new AsyncRemovePlaylist(getContext(), pos, playlistAdapter).execute(Data.playlistOfUser.get(pos).getPlaylistId() +"");

        }
    }
}
