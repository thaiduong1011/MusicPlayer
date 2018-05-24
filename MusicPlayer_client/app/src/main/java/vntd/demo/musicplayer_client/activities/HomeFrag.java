package vntd.demo.musicplayer_client.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.asynctask.AsyncListSong;

public class HomeFrag extends android.support.v4.app.Fragment {

    private RecyclerView songRecyclerView;
    private RecyclerView.Adapter songAdapter;
    private RecyclerView.LayoutManager songLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        AnhXa(view);

        showList();

        return view;
    }

    void AnhXa(View v) {
        songRecyclerView = v.findViewById(R.id.my_recycler_view);
        songRecyclerView.setHasFixedSize(true); // tối ưu hóa, không bị ảnh hưởng nội dung trong adapter
        songLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        songRecyclerView.setLayoutManager(songLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        songRecyclerView.addItemDecoration(dividerItemDecoration);
        songRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    void showList() {
        new AsyncListSong(getActivity(), getContext(), songRecyclerView, songAdapter).execute();
    }

}
