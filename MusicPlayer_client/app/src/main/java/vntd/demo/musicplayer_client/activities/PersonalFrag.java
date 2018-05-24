package vntd.demo.musicplayer_client.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.object.Data;

public class PersonalFrag extends android.support.v4.app.Fragment {

    TextView txtUserName;
    Button btnChangePass, btnSignOut;
    ListPlaylistFrag playlistFrag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        AnhXa(view);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = CheckDataActivity.sharedPreferences.edit();
                editor.remove("id");
                editor.remove("user");
                editor.remove("checkbox");
                editor.commit();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePassActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return view;
    }

    void AnhXa(View v){
        btnChangePass = v.findViewById(R.id.btnChangePass);
        btnSignOut = v.findViewById(R.id.btnSignOut);
        txtUserName = v.findViewById(R.id.txtUser);
        txtUserName.setText(Data.USER_NAME);

        playlistFrag = new ListPlaylistFrag();
    }

}
