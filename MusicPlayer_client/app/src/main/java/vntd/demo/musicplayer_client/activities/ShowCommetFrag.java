package vntd.demo.musicplayer_client.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.adapter.CommentAdapter;
import vntd.demo.musicplayer_client.asynctask.AsyncShowComment;

public class ShowCommetFrag extends android.support.v4.app.Fragment {

    private RecyclerView commentRecyclerView;
    private RecyclerView.LayoutManager commentLayoutManager;
    private CommentAdapter commentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_comment, container, false);

        AnhXa(view);

        return view;
    }

    void AnhXa(View v) {
        commentAdapter = new CommentAdapter(getActivity(), getContext(), R.layout.row_comment);
        commentRecyclerView = v.findViewById(R.id.show_comment_recycler_view);
        commentRecyclerView.setHasFixedSize(true); // tối ưu hóa, không bị ảnh hưởng nội dung trong adapter
        commentLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        commentRecyclerView.setLayoutManager(commentLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        commentRecyclerView.addItemDecoration(dividerItemDecoration);
        commentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.d("AA", "no van null ne may me oi");
    }

   public void showListComment(int song_id) {
        new AsyncShowComment(getContext(),getActivity(), commentRecyclerView, commentAdapter).execute(song_id + "");
    }

}
