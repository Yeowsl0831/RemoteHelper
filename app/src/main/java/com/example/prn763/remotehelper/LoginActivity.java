package com.example.prn763.remotehelper;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by prn763 on 12/21/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private Button sendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        sendMsg = findViewById(R.id.sendMsg);
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.READ_SMS},1);
                finish();
            }

        };
        sendMsg.setOnClickListener(ocl);
    }
}
