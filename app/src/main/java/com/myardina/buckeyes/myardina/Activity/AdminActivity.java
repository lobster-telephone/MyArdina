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
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.PendingPaymentDTO;
import com.myardina.buckeyes.myardina.R;
import com.myardina.buckeyes.myardina.Sevice.Impl.PaymentServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.PaymentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    // Activity log tag
    private static final String LOG_TAG = "ADMIN_ACTIVITY";

    // Data information objects
    private DatabaseReference mDatabase;

    // Services
    private PaymentService mPaymentService;

    private ArrayAdapter<String> mAdapter;
    private List<String> textItems;
    private Map<Integer, PendingPaymentDTO> mPayments;

    // Listeners
    private ValueEventListener mDatabaseListener;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //setting custom toolbar don't remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        initializeListeners();

        textItems = new ArrayList<>();
        mPayments = new HashMap<>();

        ListView lvDoctorListView = (ListView) findViewById(R.id.lvPendingPaymentsList);
        lvDoctorListView.setOnItemClickListener(mOnItemClickListener);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textItems);
        lvDoctorListView.setAdapter(mAdapter);

        mPaymentService = new PaymentServiceImpl();

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        mDatabase = mRef.getReference();

        Log.d(LOG_TAG, "Exiting onCreate...");
    }

    private void initializeListeners() {

        mOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(LOG_TAG, "Entering onItemClick...");
                int viewId = parent.getId();
                switch (viewId) {
                    case R.id.lvPendingPaymentsList:
                        PendingPaymentDTO pendingPaymentDTO = mPayments.get(position);
                        Intent activity = new Intent(AdminActivity.this, UpdatePaymentActivity.class);
                        activity.putExtra(CommonConstants.PENDING_PAYMENT_DTO, pendingPaymentDTO);
                        mDatabase.removeEventListener(mDatabaseListener);
                        AdminActivity.this.startActivity(activity);
                        break;
                    default:
                        break;
                }
                Log.d(LOG_TAG, "Exiting onItemClick...");
            }
        };

        mDatabaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Entering onDataChange...");
                textItems.clear();
                List<PendingPaymentDTO> pendingPayments = mPaymentService.retrievePendingPayments(dataSnapshot);
                for (PendingPaymentDTO payment : pendingPayments) {
                    DoctorDTO doctorDTO = payment.getDoctorDTO();
                    PatientDTO patientDTO = payment.getPatientDTO();
                    String doctorName = doctorDTO.getFirstName() + CommonConstants.SPACE + doctorDTO.getLastName();
                    String patientName = patientDTO.getFirstName() + CommonConstants.SPACE + patientDTO.getLastName();
                    String displayText = doctorName + CommonConstants.FROM + patientName;
                    mPayments.put(textItems.size(), payment);
                    textItems.add(displayText);
                }

                if (textItems.size() > 0) {
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AdminActivity.this, CommonConstants.NO_PAYMENTS_MESSAGE, Toast.LENGTH_LONG).show();
                }
                Log.d(LOG_TAG, "Exiting onDataChange...");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        mDatabase.removeEventListener(mDatabaseListener);
        super.onPause();
    }

    @Override
    protected void onResume(){
        System.out.println("onResume method for LoginActivity being called");
        mDatabase.addValueEventListener(mDatabaseListener);
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
