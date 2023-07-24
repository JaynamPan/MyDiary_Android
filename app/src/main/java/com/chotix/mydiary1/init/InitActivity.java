package com.chotix.mydiary1.init;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chotix.mydiary1.MainActivity;
import com.chotix.mydiary1.R;
import com.chotix.mydiary1.security.PasswordActivity;
import com.chotix.mydiary1.shared.MyDiaryApplication;

public class InitActivity extends AppCompatActivity implements InitTask.InitCallBack {
    private int initTime = 2500; // 2.5S
    private Handler initHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        initHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new InitTask(InitActivity.this, InitActivity.this).execute();
            }
        }, initTime);
    }

    @Override
    protected void onPause() {
        super.onPause();
        initHandler.removeCallbacksAndMessages(null);
    }


    @Override
    public void onInitCompiled(boolean showReleaseNote) {

        if (((MyDiaryApplication) getApplication()).isHasPassword()) {
            Intent goSecurityPageIntent = new Intent(this, PasswordActivity.class);
            goSecurityPageIntent.putExtra("password_mode", PasswordActivity.VERIFY_PASSWORD);
            goSecurityPageIntent.putExtra("showReleaseNote", showReleaseNote);
            finish();
            InitActivity.this.startActivity(goSecurityPageIntent);
        } else {
            Intent goMainPageIntent = new Intent(InitActivity.this, MainActivity.class);
            goMainPageIntent.putExtra("showReleaseNote", showReleaseNote);
            finish();
            InitActivity.this.startActivity(goMainPageIntent);
        }
    }
}
