package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.firebase.client.Firebase;
import com.myardina.buckeyes.myardina.R;

/**
 * Activity for the splash screen
 */

public class SplashActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SPLASH_ACTIVITY";

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "Entering onCreate...");

        Firebase.setAndroidContext(this);
        //Remove title bar
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_splash);

        //thread created to put the activity to sleep for 3 seconds
        Thread splashTimer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashTimer.start();
        Log.d(LOG_TAG, "Exiting onCreate...");
    }

    /**
     ******************************
     *  ACTIVITY LIFECYCLE LOGIC  *
     ******************************
     */

    @Override
    protected void onStart() {
        System.out.println("onStart method for RegisterActivity being called");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        System.out.println("onRestart method for RegisterActivity being called");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        System.out.println("onPause method for RegisterActivity being called");
        super.onPause();
    }

    @Override
    protected void onResume() {
        System.out.println("onResume method for RegisterActivity being called");
        super.onResume();
    }

    @Override
    protected void onStop() {
        System.out.println("onStop method for RegisterActivity being called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy method for RegisterActivity being called");
        super.onDestroy();
    }
}
