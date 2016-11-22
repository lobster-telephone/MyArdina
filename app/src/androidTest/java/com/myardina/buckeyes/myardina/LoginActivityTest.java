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
     * Enters username and password of a patient and checks if it goes to symptoms activity
     * @throws Exception
     */
    public void testListItemClickShouldGoToRegister() throws Exception {
        solo.unlockScreen();
        solo.waitForActivity(LoginActivity.class, 5000);

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
        //go back to login activity
        solo.goBack();
    }

    @Override
    protected void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
