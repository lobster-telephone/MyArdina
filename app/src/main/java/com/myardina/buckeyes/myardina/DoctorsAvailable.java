package com.myardina.buckeyes.myardina;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorsAvailable extends AppCompatActivity {

    private static final String LOG_DOCTOR_AVAILABLE = "LOG_DOCTOR_AVAILABLE";

    private FirebaseDatabase mRef;
    private DatabaseReference mUsersTable;

    private ListView lvDoctorListView;
    private List<String> names;
    private Map<Integer, String> userIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_available);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        lvDoctorListView = (ListView) findViewById(R.id.lvDoctorsAvailableList);
        names = new ArrayList<>();
        userIds = new HashMap<>();

        mRef = FirebaseDatabase.getInstance();
        mUsersTable = mRef.getReference().child("Users");

        mUsersTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                names.clear();

                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    if (available(user)) {
                        String firstName = "";
                        String lastName = "";
                        for (DataSnapshot userInfo : user.getChildren()) {
                            if (TextUtils.equals("FirstName", userInfo.getKey())) {
                                firstName = userInfo.getValue().toString();
                            }
                            if (TextUtils.equals("LastName", userInfo.getKey())) {
                                lastName = userInfo.getValue().toString();
                            }
                        }
                        userIds.put(names.size(), user.getKey());
                        String name = firstName + " " + lastName;
                        Log.d(LOG_DOCTOR_AVAILABLE, "Name: " + name);
                        names.add(name);
                    }
                }

                if (names.size() > 0) {
                    ArrayAdapter adapter = new ArrayAdapter(DoctorsAvailable.this, android.R.layout.simple_list_item_1, names);
                    lvDoctorListView.setAdapter(adapter);
                } else {
                    Toast.makeText(DoctorsAvailable.this, "No doctors are currently available.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // ListView Item Click Listener
        lvDoctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                DatabaseReference doctor = mUsersTable.child(userIds.get(position));
                doctor.child("Requested").setValue("Y");
                doctor.child("Available").setValue("N");
//                TelephonyManager tMgr = (TelephonyManager) DoctorsAvailable.this.getSystemService(Context.TELEPHONY_SERVICE);
//                String mPhoneNumber = tMgr.getLine1Number();
//                doctor.child("RequesterPhoneNumber").setValue(mPhoneNumber);
                Intent activity = new Intent(DoctorsAvailable.this, TeleMedicineActivity.class);
                DoctorsAvailable.this.startActivity(activity);
            }
        });
    }

    private boolean available (DataSnapshot ds) {
        boolean result = true;
        for (DataSnapshot userInfo : ds.getChildren()) {
            if (TextUtils.equals("Available", userInfo.getKey())) {
                result = TextUtils.equals(userInfo.getValue().toString(), "N") ? false : result;
            }
            if (TextUtils.equals("Doctor", userInfo.getKey())) {
                result = TextUtils.equals(userInfo.getValue().toString(), "N") ? false : result;
            }
        }
        return result;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
