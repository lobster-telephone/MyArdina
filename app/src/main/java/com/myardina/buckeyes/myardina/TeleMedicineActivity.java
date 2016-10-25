package com.myardina.buckeyes.myardina;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TeleMedicineActivity extends AppCompatActivity  {

    private static final String LOG_TELE_MEDICINE = "TELE_MEDICINE_ACTIVITY";

    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tele_medicine);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                UserDTO user = dataSnapshot.getValue(UserDTO.class);
                Log.d(LOG_TELE_MEDICINE, "UserId: " + user.getUserId());
                Log.d(LOG_TELE_MEDICINE, "Previous Post ID: " + prevChildKey);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                DatabaseReference user = ref.child(prevChildKey);
//                sendNotification();
                Log.d(LOG_TELE_MEDICINE, "Previous Post ID: " + prevChildKey);}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

//        Button bCall = (Button) findViewById(R.id.bCall);
//        bCall.setOnClickListener(this);
//
//        Button bNotify = (Button) findViewById(R.id.bNotify);
//        bNotify.setOnClickListener(this);

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
    }

//    @Override
//    public void onClick(View v) {
//        int view = v.getId();
//        switch(view) {
//            case R.id.bCall:
//                Intent callIntent = new Intent(Intent.ACTION_DIAL);
//                callIntent.setData(Uri.parse("tel:6145371807"));
//                TeleMedicineActivity.this.startActivity(callIntent);
//                break;
//            case R.id.bNotify:
//                sendNotification();
//                break;
//            default:
//                break;
//        }
//    }

//    private void sendNotification() {
//
//        // Gets an instance of the NotificationManager service
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent acceptIntent = new Intent(Intent.ACTION_CALL);
//        acceptIntent.setData(Uri.parse("tel:6145371807"));
//        PendingIntent pendingAcceptIntent = PendingIntent.getActivity(this, 0, acceptIntent, 0);
//
//        Intent declineIntent = new Intent(this, TeleMedicineActivity.class);
//        PendingIntent pendingDeclineIntent = PendingIntent.getActivity(this, 0, declineIntent, 0);
//
//        // Create the reply action and add the remote input.
//
//        //Get an instance of NotificationManager
//        NotificationCompat.Builder mBuilder =
//                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.splash)
//                        .setContentTitle("My notification")
//                        .setContentText("Hello World!")
//                        .addAction(R.drawable.splash, "ACCEPT", pendingAcceptIntent)
//                        .addAction(R.drawable.splash, "DECLINE", pendingDeclineIntent);
//
//        //mBuilder.setContentIntent(pendingIntent);
//
//        // When you issue multiple notifications about the same type of event, it’s best practice for
//        // your app to try to update an existing notification with this new information, rather than
//        // immediately creating a new notification. If you want to update this notification at a
//        // later date, you need to assign it an ID. You can then use this ID whenever you issue a
//        // subsequent notification. If the previous notification is still visible, the system will
//        // update this existing notification, rather than create a new one. In this example, the
//        // notification’s ID is 001
//        mNotificationManager.notify(1, mBuilder.build());
//    }

    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "PHONE CALL LOG";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

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
        }
    }
}