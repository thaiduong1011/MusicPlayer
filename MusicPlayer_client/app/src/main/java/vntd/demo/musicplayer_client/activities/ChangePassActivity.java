package vntd.demo.musicplayer_client.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vntd.demo.musicplayer_client.R;
import vntd.demo.musicplayer_client.asynctask.AsyncChangePass;
import vntd.demo.musicplayer_client.object.Data;

public class ChangePassActivity extends AppCompatActivity {

    EditText edtOldPass, edtNewPass, edtConfirm;
    Button btnCancel, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        AnhXa();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edtOldPass.getText().toString();
                String newpass = edtNewPass.getText().toString();
                String confirm = edtConfirm.getText().toString();

                if (!newpass.equals(confirm)){
                    Toast.makeText(ChangePassActivity.this, "Please check your password or confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                new AsyncChangePass(getApplicationContext()).execute(Data.USER_ID +"", LoginActivity.convertPassMd5(oldPass),LoginActivity.convertPassMd5(newpass));
            }
        });
    }

    void AnhXa(){
        edtNewPass = findViewById(R.id.edtNewPass);
        edtOldPass = findViewById(R.id.edtOldPass);
        edtConfirm = findViewById(R.id.edtConfirmNewPass);

        btnCancel = findViewById(R.id.btnCancel);
        btnSubmit = findViewById(R.id.btnSubmit);
    }


}
