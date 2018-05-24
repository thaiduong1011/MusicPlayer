package vntd.demo.musicplayer_client.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.object.Data;
import vntd.demo.musicplayer_client.object.UserComment;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    static Activity activity;
    Context context;
    int layout;

    public CommentAdapter(Activity activity, Context context, int layout) {
        this.activity = activity;
        this.context = context;
        this.layout = layout;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView content;

        public ViewHolder(final View itemView) {
            super(itemView);
            Log.d("AA", "dong 33 adapter");
            username = itemView.findViewById(R.id.txtUsername);
            content = itemView.findViewById(R.id.txtContent);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(Data.commentList.get(position).getUserName() + " :");
        holder.content.setText(Data.commentList.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return Data.commentList.size();
    }

    public void addItem(UserComment userComment, int position) {
        Data.commentList.add(position, userComment);
        notifyItemInserted(position);
    }

}
