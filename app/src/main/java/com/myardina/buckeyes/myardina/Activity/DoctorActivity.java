package com.myardina.buckeyes.myardina.Activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
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
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.R;

public class DoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnTouchListener {

    private static final String LOG_TAG = "DOCTOR_ACTIVITY";

    // Data information objects
    private DoctorDTO mDoctorDTO;
    private UserDAO mUserDAO;

    private boolean mAvailabilitySpinnerSelected;

    private DatabaseReference mDoctorsTable;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        //setting custom toolbar dont remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        mDoctorsTable = mRef.getReference().child(CommonConstants.DOCTORS_TABLE);

        initializeDBListeners();

        mDoctorDTO = (DoctorDTO) getIntent().getExtras().get(CommonConstants.DOCTOR_DTO);
        mUserDAO = new UserDAO();

        Spinner doctorAvailabilitySpinner = (Spinner) findViewById(R.id.spinner_doctor_availability);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doctor_availability_spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        doctorAvailabilitySpinner.setAdapter(adapter);
        doctorAvailabilitySpinner.setOnItemSelectedListener(this);
        doctorAvailabilitySpinner.setOnTouchListener(this);
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
                        mDoctorDTO.setAvailable(true);
                        mUserDAO.updateDoctorAvailability(mDoctorDTO);
                    } else if (position == 1) {
                        mDoctorDTO.setAvailable(false);
                        mUserDAO.updateDoctorAvailability(mDoctorDTO);
                    }
                    mAvailabilitySpinnerSelected = false;
                } else {
                    if (mDoctorDTO.isAvailable()) {
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

    private void initializeDBListeners() {
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                DoctorDTO userChanged = mUserDAO.retrieveUserFromDataSnapshotDoctor(dataSnapshot, false);
                if (TextUtils.equals(mDoctorDTO.getUserId(), userChanged.getUserId())) {
                    mDoctorDTO = userChanged;
                    if (mDoctorDTO.isRequested()) {
                        sendNotification(mDoctorDTO.getRequesterPhoneNumber());
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
    }

    /**
     ******************************
     *  ACTIVITY LIFECYCLE LOGIC  *
     ******************************
     */

    @Override
    protected void onStart(){
        System.out.println("onStart method for LoginActivity being called");
        super.onStart();
    }

    @Override
    protected void onRestart(){
        System.out.println("onRestart method for LoginActivity being called");
        super.onRestart();
    }

    @Override
    protected void onPause(){
        System.out.println("onPause method for LoginActivity being called");
        super.onPause();
    }

    @Override
    protected void onResume(){
        System.out.println("onResume method for LoginActivity being called");
        // Attach db listeners
        mDoctorsTable.addChildEventListener(mChildEventListener);
        super.onResume();
    }

    @Override
    protected void onStop()
    {
        System.out.println("onStop method for LoginActivity being called");
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        System.out.println("onDestroy method for LoginActivity being called");
        // Release db listeners
        mDoctorsTable.removeEventListener(mChildEventListener);
        super.onDestroy();
    }
}
