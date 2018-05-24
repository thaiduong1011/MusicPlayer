package vntd.demo.musicplayer_client.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.object.Data;

public class CheckDataActivity extends AppCompatActivity {

    public static SharedPreferences sharedPreferences;

    public int id;
    public String username;
    Boolean ischeck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_data);

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);

        id = sharedPreferences.getInt("id", -1);
        username = sharedPreferences.getString("user", "");
        ischeck = sharedPreferences.getBoolean("checkbox", false);

        if (ischeck == true && (id != -1)) {
            Intent intent = new Intent(CheckDataActivity.this, MainActivity.class);
            Data.USER_ID = id;
            Data.USER_NAME = username;
            startActivity(intent);
        } else {
            Intent intent = new Intent(CheckDataActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
