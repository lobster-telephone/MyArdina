package com.myardina.buckeyes.myardina.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.myardina.buckeyes.myardina.R;

public class TeleMedicineActivity extends AppCompatActivity  {

    private static final String LOG_TAG = "TELE_MEDICINE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele_medicine);
        //setting custom toolbar don't remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //TODO: not sure which parent activity to go back to (need to add parent in Manifest)
        //setting back button
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
        Log.d(LOG_TAG, "Exiting onCreate...");
    }

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "PHONE CALL LOG";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.d(LOG_TAG, "Entering onCallStateChanged...");

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.d(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.d(LOG_TAG, "OFFHOOK");
                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.d(LOG_TAG, "IDLE");

                if (isPhoneCalling) {
                    Log.d(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    Log.d(LOG_TAG, "Time Call took: " + CallLog.Calls.DURATION);

                    isPhoneCalling = false;
                }
            }
            Log.d(LOG_TAG, "Exiting onCallStateChanged...");
        }
    }
}