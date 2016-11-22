package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DAO.Impl.DoctorDAOImpl;
import com.myardina.buckeyes.myardina.DAO.Impl.PatientDAOImpl;
import com.myardina.buckeyes.myardina.DAO.UserDAO;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.R;

public class AdditionalInformationActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener{

    private static final String LOG_TAG = "ADDITIONAL_INFORMATION";

    // Data access and transfer objects
    private UserDAO mUserDAO;
    private PatientDTO mPatientDTO;
    private DoctorDTO mDoctorDTO;

    // UI References
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mLocationView;
    private EditText mPhoneNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_information);
        Log.d(LOG_TAG, "Entering onCreate...");
        //setting custom toolbar dont remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //setting back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize UI elements
        mFirstNameView = (EditText) findViewById(R.id.etFirstName);
        mFirstNameView.setOnFocusChangeListener(this);
        mLastNameView = (EditText) findViewById(R.id.etLastName);
        mLastNameView.setOnFocusChangeListener(this);
        mLocationView = (EditText) findViewById(R.id.etLocation);
        mLocationView.setOnFocusChangeListener(this);
        mPhoneNumberView = (EditText) findViewById(R.id.etPhoneNumber);
        mPhoneNumberView.setOnFocusChangeListener(this);
        Button mContinue = (Button) findViewById(R.id.bContinueFromAdditionalInfo);
        mContinue.setOnClickListener(this);

        // Initialize data objects
        mUserDAO = new PatientDAOImpl();
        mPatientDTO = (PatientDTO) getIntent().getExtras().get(CommonConstants.PATIENT_DTO);
        if (mPatientDTO == null) {
            mUserDAO = new DoctorDAOImpl();
            mDoctorDTO = (DoctorDTO) getIntent().getExtras().get(CommonConstants.DOCTOR_DTO);
        }

        // Dynamically display location or phone number based on type of user
        if(mPatientDTO == null) {
            mPhoneNumberView.setVisibility(View.GONE);
        } else {
            mLocationView.setVisibility(View.GONE);
        }
        Log.d(LOG_TAG, "Exiting onCreate...");
    }

    /**
     *****************************
     *  UI EVENT LISTENER LOGIC  *
     *****************************
     */

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(LOG_TAG, "Entering onFocusChange...");
        switch (v.getId()) {
            case R.id.etFirstName:
                validateNormalText(mFirstNameView);
                break;
            case R.id.etLastName:
                validateNormalText(mLastNameView);
                break;
            case R.id.etLocation:
                validateNormalText(mLocationView);
                break;
            case R.id.etPhoneNumber:
                validatePhoneNumber(mPhoneNumberView);
                break;
            default:
                break;
        }
        Log.d(LOG_TAG, "Exiting onFocusChange...");
    }

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "Entering onClick...");
        switch(v.getId()) {
            case R.id.bContinueFromAdditionalInfo:
                attemptContinue();
                break;
            default:
                break;
        }
        Log.d(LOG_TAG, "Exiting onClick...");
    }

    /**
     ****************************
     *  FIELD VALIDATION LOGIC  *
     ****************************
     */

    private boolean validateNormalText(EditText view) {
        Log.d(LOG_TAG, "Entering validateNormalText...");
        boolean result = true;
        String input = view.getText().toString();
        if (TextUtils.isEmpty(input)) {
            view.setError(getString(R.string.error_field_required));
            result = false;
        }
        Log.d(LOG_TAG, "Exiting validateNormalText...");
        return result;
    }

    private boolean validatePhoneNumber(EditText view) {
        Log.d(LOG_TAG, "Entering validatePhoneNumber...");
        boolean result = true;
        String input = view.getText().toString();
        if (TextUtils.isEmpty(input)) {
            view.setError(getString(R.string.error_field_required));
            result = false;
        } else if (input.length() != 10) {
            view.setError(getString(R.string.error_phone_length));
            result = false;
        } else if (TextUtils.equals(input.substring(3,6), "555")) {
            view.setError(getString(R.string.error_phone_valid));
            result = false;
        }
        Log.d(LOG_TAG, "Exiting validatePhoneNumber...");
        return result;
    }

    /**
     ******************************
     *  PRIVATE BACKGROUND LOGIC  *
     ******************************
     */

    private void attemptContinue() {
        Log.d(LOG_TAG, "Entering attemptContinue...");
        // Reset errors.
        mFirstNameView.setError(null);
        mLastNameView.setError(null);
        mLocationView.setError(null);
        mPhoneNumberView.setError(null);

        View focusView = null;
        if (!validateNormalText(mFirstNameView)) {
            focusView = mLocationView;
        } else if (!validateNormalText(mLastNameView)) {
            focusView = mLocationView;
        } else if (mDoctorDTO != null && !validateNormalText(mLocationView)) {
            focusView = mLocationView;
        } else if (mPatientDTO != null && !validatePhoneNumber(mPhoneNumberView)) {
            focusView = mPhoneNumberView;
        }

        if (focusView != null) {
            // There was an error, place focus on the top most field.
            focusView.requestFocus();
        } else {
            saveFormInformation();
            Intent nextActivity;
            if (mDoctorDTO != null) {
                nextActivity = new Intent(AdditionalInformationActivity.this, DoctorActivity.class);
                nextActivity.putExtra(CommonConstants.DOCTOR_DTO, mDoctorDTO);
            } else {
                nextActivity = new Intent(AdditionalInformationActivity.this, SymptomsActivity.class);
                nextActivity.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
            }
            AdditionalInformationActivity.this.startActivity(nextActivity);
        }
        Log.d(LOG_TAG, "Exiting attemptContinue...");
    }

    private void saveFormInformation() {
        Log.d(LOG_TAG, "Entering saveFormInformation...");
        if (mPatientDTO != null) {
            mPatientDTO.setFirstName(mFirstNameView.getText().toString());
            mPatientDTO.setLastName(mLastNameView.getText().toString());
            mPatientDTO.setPhoneNumber(mPhoneNumberView.getText().toString());
            mUserDAO.saveAdditionalInformation(mPatientDTO);
        } else {
            mDoctorDTO.setFirstName(mFirstNameView.getText().toString());
            mDoctorDTO.setLastName(mLastNameView.getText().toString());
            mDoctorDTO.setLocation(mLocationView.getText().toString());
            mUserDAO.saveAdditionalInformation(mDoctorDTO);
        }
        Log.d(LOG_TAG, "Exiting saveFormInformation...");
    }

    /**
     ******************************
     *  ACTIVITY LIFECYCLE LOGIC  *
     ******************************
     */

    @Override
    protected void onStart() {
        System.out.println("onStart method for RegisterActivity being called");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        System.out.println("onRestart method for RegisterActivity being called");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        System.out.println("onPause method for RegisterActivity being called");
        super.onPause();
    }

    @Override
    protected void onResume() {
        System.out.println("onResume method for RegisterActivity being called");
        super.onResume();
    }

    @Override
    protected void onStop() {
        System.out.println("onStop method for RegisterActivity being called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy method for RegisterActivity being called");
        super.onDestroy();
    }
}
