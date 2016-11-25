package com.myardina.buckeyes.myardina.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.PicklistDTO;
import com.myardina.buckeyes.myardina.R;
import com.myardina.buckeyes.myardina.Sevice.Impl.DoctorServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.Impl.PatientServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.Impl.PicklistServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.PicklistService;
import com.myardina.buckeyes.myardina.Sevice.UserService;

import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String LOG_TAG = "LOG_LOGIN_ACTIVITY";

    private UserLoginTask mAuthTask = null;
    private DatabaseReference mDoctorsTable;
    private DatabaseReference mPatientsTable;
    private DatabaseReference mPicklistTable;
    private ValueEventListener mValueEventListenerDoctor;
    private ValueEventListener mValueEventListenerPatient;
    private ValueEventListener mAdminPicklistListener;

    // Services
    private PicklistService mPicklistService;
    private UserService mUserService;

    // Data information objects
    private DoctorDTO mDoctorDTO;
    private PatientDTO mPatientDTO;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    private List<PicklistDTO> mAdmins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mEmailView.setOnFocusChangeListener(this);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnFocusChangeListener(this);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);
        Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(this);

        FirebaseDatabase mRef = FirebaseDatabase.getInstance();
        mDoctorsTable = mRef.getReference().child(CommonConstants.DOCTORS_TABLE);
        mPatientsTable = mRef.getReference().child(CommonConstants.PATIENTS_TABLE);
        mPicklistTable = mRef.getReference().child(CommonConstants.PICKLIST_TABLE);
        initializeValueEventListeners();
        mPicklistTable.addValueEventListener(mAdminPicklistListener);

        mPicklistService = new PicklistServiceImpl();

        // TODO: DEBUG BUTTONS ! REMOVE BEFORE DEPLOYING
        Button bQuickLogin = (Button) findViewById(R.id.b_quick_login);
        bQuickLogin.setOnClickListener(this);
        Button bQuickLoginDoctor = (Button) findViewById(R.id.b_quick_login_doctor);
        bQuickLoginDoctor.setOnClickListener(this);
        Log.d(LOG_TAG, "Exiting onCreate...");
    }

    /**
     *****************************
     *  UI EVENT LISTENER LOGIC  *
     *****************************
     */

    @Override
    public void onClick(View v) {
        Log.d(LOG_TAG, "Entering onClick...");
        int id = v.getId();
        switch(id) {
            case R.id.email_sign_in_button:
                attemptLogin();
                break;
            case R.id.email_register_button:
                Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                String email = mEmailView.getText().toString();
                if (!TextUtils.isEmpty(email)) {
                    registerActivity.putExtra(CommonConstants.EXTRA_EMAIL, email);
                }
                LoginActivity.this.startActivity(registerActivity);
                break;
            case R.id.b_quick_login: // TODO: DEBUG BUTTONS ! REMOVE BEFORE DEPLOYING
                mEmailView.setText(CommonConstants.DUMMY_PATIENT_EMAIL);
                mPasswordView.setText(CommonConstants.DUMMY_PATIENT_PASSWORD);
                attemptLogin();
                break;
            case R.id.b_quick_login_doctor:
                mEmailView.setText(CommonConstants.DUMMY_DOCTOR_EMAIL);
                mPasswordView.setText(CommonConstants.DUMMY_DOCTOR_PASSWORD);
                attemptLogin();
                break;

            default:
                break;
        }
        Log.d(LOG_TAG, "Exiting onClick...");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.d(LOG_TAG, "Entering onFocusChange...");
        int id = v.getId();
        if (!hasFocus) {
            switch (id) {
                case R.id.email:
                    validateEmail(mEmailView.getText().toString());
                    break;
                case R.id.password:
                    validatePassword(mPasswordView.getText().toString());
                    break;
                default:
                    break;
            }
        }
        Log.d(LOG_TAG, "Exiting onFocusChange...");
    }

    /**
     ******************************
     *  PRIVATE BACKGROUND LOGIC  *
     ******************************
     */

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        Log.d(LOG_TAG, "Entering attemptLogin...");
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        View focusView;

        boolean validPassword = validatePassword(password);
        focusView = validPassword ? mPasswordView : null;

        boolean validEmail = validateEmail(email);
        focusView = validEmail ? mEmailView : focusView;

        if (focusView != null) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // perform the user login attempt.
            mAuthTask = new UserLoginTask(email, password, this);
            mAuthTask.execute((Void) null);
            mAuthTask = null; //important do not remove
        }
        Log.d(LOG_TAG, "Exiting attemptLogin...");
    }

    private boolean validatePassword(String password) {
        Log.d(LOG_TAG, "Entering validatePassword...");
        boolean result = false;
        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            result = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            result = true;
        }
        Log.d(LOG_TAG, "Exiting validatePassword...");
        return result;
    }

    private boolean validateEmail(String email) {
        Log.d(LOG_TAG, "Entering validateEmail...");
        boolean result = false;
        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            result = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            result = true;
        }
        Log.d(LOG_TAG, "Exiting validateEmail...");
        return result;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final Context mContext;
        FirebaseAuth auth = FirebaseAuth.getInstance();

        UserLoginTask(String email, String password, Context context) {
            mEmail = email;
            mPassword = password;
            mContext = context;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(LOG_TAG, "Entering doInBackground...");
            Task<AuthResult> login_task  = auth.signInWithEmailAndPassword(mEmail, mPassword);
            OnSuccessListener login_success = new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    boolean isAdmin = false;
                    for (PicklistDTO admin : mAdmins) {
                        if (TextUtils.equals(mEmail, admin.getValue())) {
                            isAdmin = true;
                            break;
                        }
                    }
                    if (isAdmin) {
                        Intent adminActivity = new Intent(LoginActivity.this, AdminActivity.class);
                        LoginActivity.this.startActivity(adminActivity);
                    } else {
                        mDoctorsTable.addValueEventListener(mValueEventListenerDoctor);
                        mPatientsTable.addValueEventListener(mValueEventListenerPatient);
                    }
                }
            };
            OnFailureListener login_failure = new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(LOG_TAG, "Entering onFailure...");
                    // there was an error
                    Toast created_user_toast = Toast.makeText(mContext, "User " + mEmail + " cannot be logged in. Try again!", Toast.LENGTH_SHORT);
                    created_user_toast.setGravity(Gravity.CENTER, 0, 0);
                    created_user_toast.show();
                    Log.d(LOG_TAG, "Exiting onFailure...");
                }
            };

            login_task.addOnSuccessListener(login_success);
            login_task.addOnFailureListener(login_failure);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    private void initializeValueEventListeners() {
        Log.d(LOG_TAG, "Entering initializeValueEventListeners...");
        mValueEventListenerDoctor = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Entering onDataChange...");
                mUserService = new DoctorServiceImpl();
                mDoctorDTO = (DoctorDTO) mUserService.retrieveUser(dataSnapshot, true);
                if (mDoctorDTO != null && mDoctorDTO.getUserAccountId() != null) {
                    Intent nextActivity = new Intent(LoginActivity.this, DoctorActivity.class);
                    nextActivity.putExtra(CommonConstants.DOCTOR_DTO, mDoctorDTO);
                    Log.d(LOG_TAG, "Exiting onDataChange...");
                    LoginActivity.this.startActivity(nextActivity);
                } else {
                    // Do not let Firebase listeners linger through the app after a login failure
                    mDoctorsTable.removeEventListener(this);
                }
                Log.d(LOG_TAG, "Exiting onDataChange...");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        mValueEventListenerPatient = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Entering onDataChange...");
                mUserService = new PatientServiceImpl();
                mPatientDTO = (PatientDTO) mUserService.retrieveUser(dataSnapshot, true);
                if (mPatientDTO != null && mPatientDTO.getUserAccountId() != null) {
                    Intent nextActivity = new Intent(LoginActivity.this, SymptomsActivity.class);
                    nextActivity.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                    Log.d(LOG_TAG, "Exiting onDataChange...");
                    LoginActivity.this.startActivity(nextActivity);
                } else {
                    // Do not let Firebase listeners linger through the app after a login failure
                    mPatientsTable.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        mAdminPicklistListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(LOG_TAG, "Entering onDataChange...");
                mAdmins = mPicklistService.getPicklist(dataSnapshot, CommonConstants.ADMINS_PICKLIST);
                mPatientsTable.removeEventListener(this);
                Log.d(LOG_TAG, "Exiting onDataChange...");
            }
            @Override public void onCancelled(DatabaseError databaseError) { }
        };
        Log.d(LOG_TAG, "Exiting initializeValueEventListeners...");
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
        // Release db listener
        mDoctorsTable.removeEventListener(mValueEventListenerDoctor);
        mPatientsTable.removeEventListener(mValueEventListenerPatient);
        mPicklistTable.removeEventListener(mAdminPicklistListener);
        super.onPause();
    }

    @Override
    protected void onResume(){
        System.out.println("onResume method for LoginActivity being called");
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
