package com.myardina.buckeyes.myardina.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.myardina.buckeyes.myardina.DAO.Impl.PaymentDAOImpl;
import com.myardina.buckeyes.myardina.DAO.PaymentDAO;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.PaymentDTO;
import com.myardina.buckeyes.myardina.R;
import com.myardina.buckeyes.myardina.Sevice.DoctorService;
import com.myardina.buckeyes.myardina.Sevice.Impl.DoctorServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.Impl.PatientServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.PatientService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    // Activity log tag
    private static final String LOG_TAG = "ADMIN_ACTIVITY";

    // Data information objects
    private PaymentDAO mPaymentDAO;

    private DatabaseReference mPaymentsTable;

    // Services
    private DoctorService mDoctorService;
    private PatientService mPatientService;

    private ArrayAdapter<String> mAdapter;
    private List<String> textItems;
    private Map<Integer, String> paymentKeys;

    // Listeners
    private ValueEventListener mValueEventListener;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeListeners();

        textItems = new ArrayList<>();
        paymentKeys = new HashMap<>();

        ListView lvDoctorListView = (ListView) findViewById(R.id.lvPendingPaymentsList);
        lvDoctorListView.setOnItemClickListener(mOnItemClickListener);
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textItems);
        lvDoctorListView.setAdapter(mAdapter);

        mPaymentDAO = new PaymentDAOImpl();
        mDoctorService = new DoctorServiceImpl();
        mPatientService = new PatientServiceImpl();

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        mPaymentsTable = mRef.getReference().child(CommonConstants.PAYMENTS_TABLE);

        Log.d(LOG_TAG, "Exiting onCreate...");
    }

    private void initializeListeners() {
        mValueEventListener = new ValueEventListener() {
            /**
             * Live update the list of available textItems displayed to the user
             */
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Entering onDataChange...");
                textItems.clear();
                List<PaymentDTO> pendingPayments = mPaymentDAO.retrievePendingPayments(dataSnapshot);
                for (PaymentDTO payment : pendingPayments) {
                    paymentKeys.put(textItems.size(), payment.getTableKey());
                    DoctorDTO doctorDTO = (DoctorDTO) mDoctorService.retrieveFromId(payment.getDoctorId());
                    String doctorName = doctorDTO.getFirstName() + CommonConstants.SPACE + doctorDTO.getLastName();
                    PatientDTO patientDTO = (PatientDTO) mPatientService.retrieveFromId(payment.getPatientId());
                    String patientName = patientDTO.getFirstName() + CommonConstants.SPACE + patientDTO.getLastName();
                    String text = doctorName + CommonConstants.FROM + patientName;
                    textItems.add(text);
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
                Log.d(LOG_TAG, "Entering onCancelled...");
                Log.d(LOG_TAG, "ERROR : " + databaseError.getMessage());
                Log.d(LOG_TAG, "Exiting onCancelled...");
            }
        };

//        mOnItemClickListener = new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(LOG_TAG, "Entering onItemClick...");
//                int viewId = parent.getId();
//                switch (viewId) {
//                    case R.id.lvDoctorsAvailableList:
//                        DoctorDTO doctorDTO = new DoctorDTO();
//                        doctorDTO.setUserKey(paymentKeys.get(position));
//                        doctorDTO.setRequesterPhoneNumber(mPatientDTO.getPhoneNumber());
//                        mDoctorDAO.updateDoctorToNotAvailable(doctorDTO);
//                        Intent activity = new Intent(DoctorsAvailableActivity.this, TeleMedicineActivity.class);
//                        activity.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
//                        activity.putExtra(CommonConstants.PAYMENT_DTO, mPaymentDTO);
//                        mDoctorsTable.removeEventListener(mValueEventListener);
//                        DoctorsAvailableActivity.this.startActivity(activity);
//                        break;
//                    default:
//                        break;
//                }
//                Log.d(LOG_TAG, "Exiting onItemClick...");
//            }
//        };
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
        mPaymentsTable.removeEventListener(mValueEventListener);
        super.onPause();
    }

    @Override
    protected void onResume(){
        System.out.println("onResume method for LoginActivity being called");
        mPaymentsTable.addValueEventListener(mValueEventListener);
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
