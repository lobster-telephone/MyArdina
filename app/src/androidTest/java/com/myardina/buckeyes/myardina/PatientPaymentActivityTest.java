package com.myardina.buckeyes.myardina;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.myardina.buckeyes.myardina.Activity.DoctorsAvailableActivity;
import com.myardina.buckeyes.myardina.Activity.LoginActivity;
import com.myardina.buckeyes.myardina.Activity.PatientPaymentActivity;
import com.myardina.buckeyes.myardina.Activity.SymptomsActivity;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.robotium.solo.Solo;
/**
 * Created by mishaberkovich on 11/30/16.
 */

public class PatientPaymentActivityTest extends ActivityInstrumentationTestCase2<LoginActivity>{


    private Solo solo;
    public PatientPaymentActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());

    }

    /**
     * Assumption: User has already registered and has paypal account
     * Just test that user can get to paypal app
     * @throws Exception
     */
    public void testPayPalApp() throws Exception {
        solo.unlockScreen();
        //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
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
        //click on continue button
        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.waitForView(continueButton, 4000, false);
        solo.clickOnView(continueButton);
        solo.waitForView(continueButton, 4000, false);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
        //should be on payment activity now
        Button paypalLoginButton = (Button) solo.getCurrentActivity().findViewById(R.id.login_paypal);
        solo.waitForView(paypalLoginButton, 2000, false);
        solo.clickOnView(paypalLoginButton);
        //wait for and check that next activity is PaymentActivity
        solo.waitForActivity(PaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected Payment activity", PaymentActivity.class);
        //cannot access the view elements in this activity, because it opens up the paypal app from here...

    }


    /**
     * Assumption: User has already registered and has paypal account
     * Just test that user can get to doctorsavailable activity from paypal activity
     * @throws Exception
     */
    public void testContinue() throws Exception {
        solo.unlockScreen();
        //This code just logs in and gets to symptom activity,
        //repeat of code of testing successful login from login activity test
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
        //click on continue button
        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.waitForView(continueButton, 4000, false);
        solo.clickOnView(continueButton);
        solo.waitForView(continueButton, 4000, false);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected PatientPayment activity", PatientPaymentActivity.class);
        //should be on payment activity now
        continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_debug_to_doctors_available);
        solo.waitForView(continueButton, 2000, false);
        solo.clickOnView(continueButton);
        //wait for and check that next activity is PaymentActivity
        solo.waitForActivity(DoctorsAvailableActivity.class, 2000);
        solo.assertCurrentActivity("Expected Doctors Available activity", DoctorsAvailableActivity.class);
        //cannot access the view elements in this activity, because it opens up the paypal app from here...

    }




    @Override
    protected void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
