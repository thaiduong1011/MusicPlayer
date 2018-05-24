package vntd.demo.musicplayer_client.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.adapter.PlaylistAdapter;
import vntd.demo.musicplayer_client.asynctask.AsyncAddNewPlaylist;
import vntd.demo.musicplayer_client.object.Data;

public class DialogAddPlaylist {
    Context context;
    PlaylistAdapter adapter;

    public DialogAddPlaylist(Context context, PlaylistAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public void showDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_add_playlist);

        final EditText edtPlaylistName =dialog.findViewById(R.id.edtNewPlaylistName);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnAdd = dialog.findViewById(R.id.btnAdd);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = edtPlaylistName.getText().toString();
                new AsyncAddNewPlaylist(context, adapter, newName).execute(Data.USER_ID + "", newName);
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
