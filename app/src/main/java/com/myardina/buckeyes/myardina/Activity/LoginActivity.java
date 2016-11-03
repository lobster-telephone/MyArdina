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
import com.myardina.buckeyes.myardina.DAO.UserDAO;
import com.myardina.buckeyes.myardina.DTO.UserDTO;
import com.myardina.buckeyes.myardina.R;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final String LOG_TAG = "LOG_LOGIN_ACTIVITY";

    private UserLoginTask mAuthTask = null;

    // Data information objects
    private UserDTO mUserDTO;
    private UserDAO mUserDAO;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    private DatabaseReference mUsersTable;

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
        mUsersTable = mRef.getReference().child(CommonConstants.USERS_TABLE);
        mUserDTO = new UserDTO();
        mUserDAO = new UserDAO();

        // TODO: DEBUG BUTTONS ! REMOVE BEFORE DEPLOYING
        Button bQuickLogin = (Button) findViewById(R.id.b_quick_login);
        bQuickLogin.setOnClickListener(this);
        Button bQuickLoginDoctor = (Button) findViewById(R.id.b_quick_login_doctor);
        bQuickLoginDoctor.setOnClickListener(this);
        Log.d(LOG_TAG, "Exiting onCreate...");
    }

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
            case R.id.b_quick_login:
                mEmailView.setText("p@m.com");
                mPasswordView.setText("Dummy1234");
                attemptLogin();
                break;
            case R.id.b_quick_login_doctor:
                mEmailView.setText("t6@gmail.com");
                mPasswordView.setText("Dummy1234");
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
                    mUsersTable.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(LOG_TAG, "Entering onDataChange...");
                            mUserDTO = mUserDAO.retrieveUserFromDataSnapshot(dataSnapshot, true);
                            Intent nextActivity;
                            if (mUserDTO.isDoctor()) {
                                nextActivity = new Intent(LoginActivity.this, DoctorActivity.class);
                            } else {
                                nextActivity = new Intent(LoginActivity.this, SymptomsActivity.class);
                            }
                            nextActivity.putExtra(CommonConstants.USER_DTO, mUserDTO);
                            mUsersTable.removeEventListener(this);
                            Log.d(LOG_TAG, "Exiting onDataChange...");
                            LoginActivity.this.startActivity(nextActivity);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
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
            // if (success){} else {}
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    /**
     **************************
     *  ACTIVITY STATE LOGIC  *
     **************************
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
