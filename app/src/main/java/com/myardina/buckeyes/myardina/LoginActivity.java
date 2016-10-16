package com.myardina.buckeyes.myardina;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.res.Resources.Theme;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{


    private UserLoginTask mAuthTask = null;
    private UserRegisterTask mRegTask = null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate method for LoginActivity being called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);


        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLoginOrRegister(0);
            }
        });

        Button mEmailRegisterButton = (Button) findViewById(R.id.email_register_button);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLoginOrRegister(1);
            }
        });


        mLoginFormView = findViewById(R.id.login_form);
    }

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


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     * int l_r is 0 if login 1 if register
     */
    private void attemptLoginOrRegister(int l_r) {
        if (mAuthTask != null) {
            return;
        }


        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();


        mAuthTask = new UserLoginTask(email, password, this);
        mRegTask = new UserRegisterTask(email, password, this);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else{
            // perform the user login attempt.
            if (l_r == 0){
                mAuthTask = new UserLoginTask(email, password, this);
                mAuthTask.execute((Void) null);
                mAuthTask = null;//important do not remove
                mRegTask = null;//important do not remove
            }
            else if (l_r == 1){
                mRegTask = new UserRegisterTask(email, password, this);
                mRegTask.execute((Void) null);
                mAuthTask = null;//important do not remove
                mRegTask = null;//important do not remove
            }

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
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

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            Task login_task  = auth.signInWithEmailAndPassword(mEmail, mPassword);
            OnSuccessListener login_success = new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Intent nextActivity = new Intent(LoginActivity.this, SymptomsActivity.class);
                    LoginActivity.this.startActivity(nextActivity);
                }
            };
            OnFailureListener login_failure = new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // there was an error
                    Gravity gravity = new Gravity();
                    Toast created_user_toast = Toast.makeText(mContext, "User " + mEmail + " cannot be logged in. Try again!", Toast.LENGTH_SHORT);
                    created_user_toast.setGravity(gravity.CENTER | gravity.CENTER, 0, 0);
                    created_user_toast.show();
                }
            };

            login_task.addOnSuccessListener(login_success);
            login_task.addOnFailureListener(login_failure);



            return true;



        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success){

            } else {


            }

        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }




    }

    /**
     * Represents an asynchronous registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final Context mContext;
        FirebaseAuth auth = FirebaseAuth.getInstance();


        UserRegisterTask(String email, String password, Context context) {
            mEmail = email;
            mPassword = password;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt to make a new user id

            Task register_task = auth.createUserWithEmailAndPassword(mEmail, mPassword);
            OnSuccessListener login_success = new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Gravity gravity = new Gravity();
                    Toast created_user_toast = Toast.makeText(mContext, "Created user " + mEmail + ", now please try to login!", Toast.LENGTH_SHORT);
                    created_user_toast.setGravity(gravity.TOP | Gravity.CENTER, 0, 0);
                    created_user_toast.show();
                }
            };
            OnFailureListener login_failure = new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {



                    // there was an error
                    Gravity gravity = new Gravity();
                    Toast created_user_toast = Toast.makeText(mContext, "User " + mEmail + " cannot be created, might exist already, try again!", Toast.LENGTH_SHORT);
                    created_user_toast.setGravity(gravity.TOP | Gravity.CENTER, 0, 0);
                    created_user_toast.show();
                }
            };

            register_task.addOnSuccessListener(login_success);
            register_task.addOnFailureListener(login_failure);



            return true;
        }
        @Override
        protected void onPostExecute(final Boolean success) {
            mRegTask = null;
            if (success){



            }

        }
    }
}

