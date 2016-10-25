package com.myardina.buckeyes.myardina;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String LOG_DOCTOR_ACTIVITY = "LOG_DOCTOR_ACTIVITY";

    private FirebaseDatabase mRef;
    private DatabaseReference mUsersTable;
    private DatabaseReference mChildRef;
    private Spinner doctor_availability_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        mRef = FirebaseDatabase.getInstance();
        mUsersTable = mRef.getReference().child("Users");
        final String userId = (String) this.getIntent().getExtras().get("UserId");
        mChildRef = mUsersTable.child(userId);
        mUsersTable.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                if (TextUtils.equals(userId, dataSnapshot.getKey())) {
                    String requested = "N";
                    String requestedPhoneNumber = "";
                    for (DataSnapshot userInfo : dataSnapshot.getChildren()) {
                        if (TextUtils.equals("Requested", userInfo.getKey())) {
                             requested = userInfo.getValue().toString();
                        } else if (TextUtils.equals("RequesterPhoneNumber", userInfo.getKey())) {
                            requestedPhoneNumber = userInfo.getValue().toString();
                        }
                    }
                    if (TextUtils.equals(requested, "Y")) {
                        sendNotification(requestedPhoneNumber);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        doctor_availability_spinner = (Spinner) findViewById(R.id.spinner_doctor_availability);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doctor_availability_spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        doctor_availability_spinner.setAdapter(adapter);
        doctor_availability_spinner.setOnItemSelectedListener(this);
    }

    private void sendNotification(String phoneNumber) {

        // Gets an instance of the NotificationManager service
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent acceptIntent = new Intent(Intent.ACTION_DIAL);
        acceptIntent.setData(Uri.parse("tel:" + phoneNumber));
        PendingIntent pendingAcceptIntent = PendingIntent.getActivity(this, 0, acceptIntent, 0);

        Intent declineIntent = new Intent(this, TeleMedicineActivity.class);
        PendingIntent pendingDeclineIntent = PendingIntent.getActivity(this, 0, declineIntent, 0);

        // Create the reply action and add the remote input.

        //Get an instance of NotificationManager
        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.splash)
                        .setContentTitle("Ardina Patient Request")
                        .setContentText("You have a request from a patient.");
//                        .addAction(R.drawable.splash, "ACCEPT", pendingAcceptIntent)
//                        .addAction(R.drawable.splash, "DECLINE", pendingDeclineIntent);

        mBuilder.setContentIntent(pendingAcceptIntent);

        // When you issue multiple notifications about the same type of event, it’s best practice for
        // your app to try to update an existing notification with this new information, rather than
        // immediately creating a new notification. If you want to update this notification at a
        // later date, you need to assign it an ID. You can then use this ID whenever you issue a
        // subsequent notification. If the previous notification is still visible, the system will
        // update this existing notification, rather than create a new one. In this example, the
        // notification’s ID is 001
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            //if yes is selected doctor is available
            case R.id.spinner_doctor_availability:
                if (position == 0) {
                    mChildRef.child("Available").setValue("Y");
                } else if( position == 1) {
                    mChildRef.child("Available").setValue("N");
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
