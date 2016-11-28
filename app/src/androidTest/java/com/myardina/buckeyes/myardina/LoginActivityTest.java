package com.myardina.buckeyes.myardina;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.myardina.buckeyes.myardina.Activity.LoginActivity;
import com.myardina.buckeyes.myardina.Activity.SplashActivity;
import com.myardina.buckeyes.myardina.Activity.SymptomsActivity;
import com.robotium.solo.Solo;

public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(),getActivity());

    }

    /**
     * Assumption: User has already registered and the account is in the database
     * Enters valid user email and password of a patient and checks if it goes to symptoms activity
     * @throws Exception
     */
    public void testLoginGotoSymptoms() throws Exception {
        solo.unlockScreen();
        solo.waitForActivity(LoginActivity.class, 1000);

        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "s@t.com");
        //add password
        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Applepie");
        //click sign in button
        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //waiting for login in case there is a network delay
        solo.waitForActivity(SymptomsActivity.class, 2000);
        // assert that the current activity is the SymptomsActivity.class
        solo.assertCurrentActivity("Expected Symptoms activity", SymptomsActivity.class);
    }


    /**
     * Assumption: User has already registered and the account is in the database
     * Enters valid user email and a incorrect password where a toast is shown
     * @throws Exception
     */
    public void testLoginErrorsMessages1() throws Exception {
        solo.unlockScreen();
        solo.waitForActivity(LoginActivity.class, 1000);

        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "s@t.com");
        //add password
        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Applepi");
        //click sign in button
        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //wait for cannot be logged in toast
        solo.waitForText("user s@t.com cannot be logged in.");
        // assert that the current activity is the LoginActivity.class
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
    }

    /**
     * Assumption: User has already registered and the account is in the database
     * Enters an invalid email and password and user will see an error message
     * @throws Exception
     */
    public void testLoginErrorsMessages2() throws Exception {
        solo.unlockScreen();
        solo.waitForActivity(LoginActivity.class, 1000);

        // check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "s.com");
        //add password
        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "Applepie");
        //click sign in button
        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //wait for error message
        solo.waitForText("This email address is invalid");
        // assert that the current activity is the LoginActivity.class
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
    }

    /**
     * Assumption: User has already registered and the account is in the database
     * Enters a valid email and short password and user will see an error message
     * @throws Exception
     */
    public void testLoginErrorsMessages3() throws Exception {
        solo.unlockScreen();
        solo.waitForActivity(LoginActivity.class, 1000);

        //check that we have the right activity
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
        //add username
        EditText email = (EditText) solo.getCurrentActivity().findViewById(R.id.email);
        solo.enterText(email, "s@t.com");
        //add password
        EditText password = (EditText) solo.getCurrentActivity().findViewById(R.id.password);
        solo.enterText(password, "App");
        //click sign in button
        Button loginBtn = (Button) solo.getCurrentActivity().findViewById(R.id.email_sign_in_button);
        solo.clickOnView(loginBtn);
        //wait for error message
        solo.waitForText("This password is too short");
        //assert that the current activity is the LoginActivity.class
        solo.assertCurrentActivity("Expected Login activity", LoginActivity.class);
    }

    @Override
    protected void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
