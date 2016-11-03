package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.myardina.buckeyes.myardina.DTO.UserDTO;
import com.myardina.buckeyes.myardina.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsAvailableActivity extends AppCompatActivity {

    // Data information objects
    private DoctorDAO mDoctorDAO;
    private UserDTO mUserDTO;

    private DatabaseReference mUsersTable;

    // Activity log tag
    private static final String LOG_TAG = "DOCTORS_AVAILABLE_ACT";

    private ListView lvDoctorListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> names;
    private Map<Integer, String> userIds;

    // Listeners
    private ValueEventListener mValueEventListener;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_available);

        initializeListeners();

        names = new ArrayList<>();
        userIds = new HashMap<>();

        lvDoctorListView = (ListView) findViewById(R.id.lvDoctorsAvailableList);
        lvDoctorListView.setOnItemClickListener(mOnItemClickListener);
        mAdapter = new ArrayAdapter<>(DoctorsAvailableActivity.this, android.R.layout.simple_list_item_1, names);
        lvDoctorListView.setAdapter(mAdapter);

        mUserDTO = (UserDTO) getIntent().getExtras().get(CommonConstants.USER_DTO);
        mDoctorDAO = new DoctorDAO();

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        mUsersTable = mRef.getReference().child(CommonConstants.USERS_TABLE);
        mUsersTable.addValueEventListener(mValueEventListener);

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
                List<UserDTO> availableDoctors = mDoctorDAO.retrieveAvailableDoctors(dataSnapshot);
                for (UserDTO doctor : availableDoctors) {
                    userIds.put(names.size(), doctor.getUserKey());
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
                        UserDTO doctorDTO = new UserDTO();
                        doctorDTO.setUserKey(userIds.get(position));
                        doctorDTO.setRequested(true);
                        doctorDTO.setAvailable(false);
                        doctorDTO.setRequesterPhoneNumber(mUserDTO.getPhoneNumber());
                        mDoctorDAO.updateDoctorToNotAvailable(doctorDTO);
                        Intent activity = new Intent(DoctorsAvailableActivity.this, TeleMedicineActivity.class);
                        activity.putExtra(CommonConstants.USER_DTO, mUserDTO);
                        mUsersTable.removeEventListener(mValueEventListener);
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
    public void onStart() {
        Log.d(LOG_TAG, "Entering onStart...");
        super.onStart();
        Log.d(LOG_TAG, "Exiting onItemClick...");
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "Entering onResume...");
        super.onResume();
        Log.d(LOG_TAG, "Exiting onResume...");
    }
}
