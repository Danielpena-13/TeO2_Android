package com.daniel13pe.navdrw_java.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.daniel13pe.navdrw_java.ui.main.MainActivity;
import com.daniel13pe.navdrw_java.R;
import java.util.Timer;
import java.util.TimerTask;
import androidx.annotation.Nullable;

public class SplashActivity extends Activity {

    private static final long SPLASH_DELAY = 338;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent().setClass(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_DELAY);
    }
}
