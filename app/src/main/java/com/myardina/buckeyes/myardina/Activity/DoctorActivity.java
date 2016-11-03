package com.myardina.buckeyes.myardina.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.UserDAO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;
import com.myardina.buckeyes.myardina.R;

public class DoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private static final String LOG_TAG = "DOCTOR_ACTIVITY";

    // Data information objects
    private UserDTO mUserDTO;
    private UserDAO mUserDAO;

    private Spinner mDoctorAvailabilitySpinner;
    private boolean mAvailabilitySpinnerSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        DatabaseReference mUsersTable = mRef.getReference().child(CommonConstants.USERS_TABLE);

        mUserDTO = (UserDTO) getIntent().getExtras().get(CommonConstants.USER_DTO);
        mUserDAO = new UserDAO();

        mUsersTable.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                UserDTO userChanged = mUserDAO.retrieveUserFromDataSnapshot(dataSnapshot, false);
                if (TextUtils.equals(mUserDTO.getUserId(), userChanged.getUserId())) {
                    mUserDTO = userChanged;
                    if (mUserDTO.isRequested()) {
                        sendNotification(mUserDTO.getRequesterPhoneNumber());
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

        mDoctorAvailabilitySpinner = (Spinner) findViewById(R.id.spinner_doctor_availability);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doctor_availability_spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mDoctorAvailabilitySpinner.setAdapter(adapter);
        mDoctorAvailabilitySpinner.setOnItemSelectedListener(this);
        mDoctorAvailabilitySpinner.setOnTouchListener(this);
        mAvailabilitySpinnerSelected = false;
        Log.d(LOG_TAG, "Exiting onCreate...");
    }

    private void sendNotification(String phoneNumber) {
        Log.d(LOG_TAG, "Entering sendNotification...");

        // Gets an instance of the NotificationManager service
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent acceptIntent = new Intent(Intent.ACTION_DIAL);
        acceptIntent.setData(Uri.parse("tel:" + phoneNumber));
        PendingIntent pendingAcceptIntent = PendingIntent.getActivity(this, 0, acceptIntent, 0);

//        Intent declineIntent = new Intent(this, TeleMedicineActivity.class);
//        PendingIntent pendingDeclineIntent = PendingIntent.getActivity(this, 0, declineIntent, 0);

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
        mNotificationManager.notify(1, mBuilder.build());
        Log.d(LOG_TAG, "Exiting sendNotification...");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Log.d(LOG_TAG, "Entering onItemSelected...");
        switch (adapterView.getId()) {
            //if yes is selected doctor is available
            case R.id.spinner_doctor_availability:
                if (mAvailabilitySpinnerSelected) {
                    if (position == 0) {
                        mUserDTO.setAvailable(true);
                        mUserDAO.updateDoctorAvailability(mUserDTO);
                    } else if (position == 1) {
                        mUserDTO.setAvailable(false);
                        mUserDAO.updateDoctorAvailability(mUserDTO);
                    }
                    mAvailabilitySpinnerSelected = false;
                } else {
                    if (mUserDTO.isAvailable()) {
                        adapterView.setSelection(0);
                    } else {
                        adapterView.setSelection(1);
                    }
                }
                break;
            default:
                break;
        }
        Log.d(LOG_TAG, "Exiting onItemSelected...");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(LOG_TAG, "Entering onTouch...");
        switch (v.getId()) {
            //if yes is selected doctor is available
            case R.id.spinner_doctor_availability:
                mAvailabilitySpinnerSelected = true;
                break;
            default:
                break;
        }
        Log.d(LOG_TAG, "Exiting onTouch...");
        return false;
    }
}
