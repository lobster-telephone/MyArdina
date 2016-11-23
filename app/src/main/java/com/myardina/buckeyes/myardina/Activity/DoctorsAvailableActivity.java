package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.DoctorDAO;
import com.myardina.buckeyes.myardina.DAO.Impl.DoctorDAOImpl;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.PaymentDTO;
import com.myardina.buckeyes.myardina.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsAvailableActivity extends AppCompatActivity {

    // Activity log tag
    private static final String LOG_TAG = "DOCTORS_AVAILABLE_ACT";

    // Data information objects
    private DoctorDAO mDoctorDAO;
    private PatientDTO mPatientDTO;
    private PaymentDTO mPaymentDTO;

    private DatabaseReference mDoctorsTable;

    private ArrayAdapter<String> mAdapter;
    private List<String> names;
    private Map<Integer, String> userKeys;

    // Listeners
    private ValueEventListener mValueEventListener;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_available);
        //setting custom toolbar dont remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //setting back button
        if(getSupportActionBar() != null){
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initializeListeners();

        names = new ArrayList<>();
        userKeys = new HashMap<>();

        ListView lvDoctorListView = (ListView) findViewById(R.id.lvDoctorsAvailableList);
        lvDoctorListView.setOnItemClickListener(mOnItemClickListener);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        lvDoctorListView.setAdapter(mAdapter);

        mPatientDTO = (PatientDTO) getIntent().getExtras().get(CommonConstants.PATIENT_DTO);
        mPaymentDTO = (PaymentDTO) getIntent().getExtras().get(CommonConstants.PAYMENT_DTO);
        mDoctorDAO = new DoctorDAOImpl();

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        mDoctorsTable = mRef.getReference().child(CommonConstants.DOCTORS_TABLE);

        Log.d(LOG_TAG, "Exiting onCreate...");
    }

    private void initializeListeners() {
        mValueEventListener = new ValueEventListener() {
            /**
             * Live update the list of available doctors displayed to the user
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Entering onDataChange...");
                names.clear();
                List<DoctorDTO> availableDoctors = mDoctorDAO.retrieveAvailableDoctors(dataSnapshot);
                for (DoctorDTO doctor : availableDoctors) {
                    userKeys.put(names.size(), doctor.getUserKey());
                    String name = doctor.getFirstName() + CommonConstants.SPACE + doctor.getLastName();
                    Log.d(LOG_TAG, "Name: " + name);
                    names.add(name);
                }

                if (names.size() > 0) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(DoctorsAvailableActivity.this, CommonConstants.NO_DOCTORS_MESSAGE, Toast.LENGTH_LONG).show();
                }
                Log.d(LOG_TAG, "Exiting onDataChange...");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(LOG_TAG, "Entering onCancelled...");
                Log.d(LOG_TAG, "ERROR : " + databaseError.getMessage());
                Log.d(LOG_TAG, "Exiting onCancelled...");
            }
        };

        mOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "Entering onItemClick...");
                int viewId = parent.getId();
                switch (viewId) {
                    case R.id.lvDoctorsAvailableList:
                        DoctorDTO doctorDTO = new DoctorDTO();
                        doctorDTO.setUserKey(userKeys.get(position));
                        doctorDTO.setRequesterPhoneNumber(mPatientDTO.getPhoneNumber());
                        mDoctorDAO.updateDoctorToNotAvailable(doctorDTO);
                        Intent activity = new Intent(DoctorsAvailableActivity.this, TeleMedicineActivity.class);
                        activity.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                        mPaymentDTO.setDoctorId(doctorDTO.getTableKey());
                        activity.putExtra(CommonConstants.PAYMENT_DTO, mPaymentDTO);
                        mDoctorsTable.removeEventListener(mValueEventListener);
                        DoctorsAvailableActivity.this.startActivity(activity);
                        break;
                    default:
                        break;
                }
                Log.d(LOG_TAG, "Exiting onItemClick...");
            }
        };
    }

    /**
     * *************************
     * ACTIVITY STATE LOGIC  *
     * *************************
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
        // Release db listener
        mDoctorsTable.removeEventListener(mValueEventListener);
        super.onPause();
    }

    @Override
    protected void onResume(){
        System.out.println("onResume method for LoginActivity being called");
        mDoctorsTable.addValueEventListener(mValueEventListener);
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
        super.onDestroy();
    }
}
