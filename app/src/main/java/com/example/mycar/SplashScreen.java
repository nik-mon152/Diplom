package com.example.mycar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    int limit = 2;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        handler = new Handler();
        onEverySecond.run();
    }
    Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {

            count++;
            if (count == limit){
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
            }else {handler.postDelayed(onEverySecond, 1000);
            }
        }
    };
}