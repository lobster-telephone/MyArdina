package com.myardina.buckeyes.myardina;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
//import com.firebase.client.DataSnapshot;
//import com.firebase.client.Firebase;
//import com.firebase.client.FirebaseError;
//import com.firebase.client.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginToFacebook extends AppCompatActivity {

    private CallbackManager callbackManager;
    private ProfileTracker mProfileTracker;
    private LoginButton mLoginButton;
    private AccessTokenTracker mAccessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.login_to_facebook);

        // skip login if user is already logged in
        if (Profile.getCurrentProfile() != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.putExtra("user_name", Profile.getCurrentProfile().getFirstName());
            startActivity(intent);
        }

        // hiding action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

//         Add code to print out the key hash
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.myardina.buckeyes.myardina",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//        } catch (NoSuchAlgorithmException e) { }

        callbackManager = CallbackManager.Factory.create();

        mLoginButton = (LoginButton)findViewById(R.id.login_button);
        mLoginButton.setReadPermissions(Arrays.asList(new String[]{"user_friends", "public_profile", "email", "user_birthday"}));

        mLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            mProfileTracker.stopTracking();

//                            saveNewProfileName(currentProfile);
//                            saveNewProfileInDb(currentProfile);
                        }
                    };
                    mProfileTracker.startTracking();
                }
                else {
                    Profile profile = Profile.getCurrentProfile();
//                    saveNewProfileName(profile);
//                    saveNewProfileInDb(profile);
                }

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("user_name", Profile.getCurrentProfile().getFirstName());
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(getClass().getSimpleName(), "User cancelled login");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(getClass().getSimpleName(), error.toString());
            }
        });

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (oldAccessToken != null && currentAccessToken == null) {
                    Intent intent = new Intent(getApplicationContext(), LoginToFacebook.class);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached LoginToFacebook.onStart();");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached LoginToFacebook.onPause();");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached LoginToFacebook.onResume();");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached LoginToFacebook.onStop();");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mProfileTracker.stopTracking();
        mAccessTokenTracker.stopTracking();
        Log.d("SHTUFF", "LIFECYCLE UPDATE :: App has reached LoginToFacebook.onDestroy();");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

//    private void saveNewProfileName(Profile profile) {
//        SharedPreferences prefs = getSharedPreferences(Utility.prefsFile, MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("userId", profile.getId());
//        editor.putString("userName", profile.getName());
//        editor.apply();
//    }

//    private void saveNewProfileInDb(final Profile profile) {
//        final Firebase ref = new Firebase("https://sizzling-torch-801.firebaseio.com/profiles");
//
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                boolean found = false;
//                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    ProfileDTO dbProfile = postSnapshot.getValue(ProfileDTO.class);
//                    if (dbProfile.getName().equals(profile.getName())) {
//                        found = true;
//                    }
//                    if (!found) {
//                        com.thebeast.com.thebeast.Profile newPlayer = new com.thebeast.com.thebeast.Profile(profile.getName());
//                        Firebase profileRef = ref.push();
//                        profileRef.setValue(newPlayer);
//                    }
//                }
//                if (!found) {
//                    ProfileDTO newPlayer = new ProfileDTO(profile.getName(), 0, 0, 0);
//                    ref.push().setValue(newPlayer);
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.d("SHTUFF", "The read failed: " + firebaseError.getMessage());
//            }
//        });
//    }
}
