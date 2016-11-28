package com.myardina.buckeyes.myardina;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.myardina.buckeyes.myardina.Activity.LoginActivity;
import com.myardina.buckeyes.myardina.Activity.SplashActivity;
import com.myardina.buckeyes.myardina.Activity.SymptomsActivity;
import com.myardina.buckeyes.myardina.Activity.PatientPaymentActivity;
import com.robotium.solo.Solo;

public class SymptomsActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Solo solo;
    public SymptomsActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());

    }




    /**
     * Assumption: User has already registered and the account is in the database
     * Clicks on leg and chooses "pink eye" for symptom, goes to PatientPaymentActivity
     * @throws Exception
     */
   public void testSymptomsPickerOneSymptom() throws Exception {
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
        Button bLegs = (Button) solo.getCurrentActivity().findViewById(R.id.mv_legs_button);
        //click on the legs
        solo.waitForView(bLegs);
        solo.clickOnView(bLegs);
        //symptoms picker fragment should show up with symptoms associated with legs
        solo.waitForFragmentByTag("SymptomsPickerDialog",2000);
        DialogFragment SymptomsPickerFragment = (DialogFragment) solo.getCurrentActivity().getFragmentManager().findFragmentById(0);
        //get the view elements inside fragment & click on pink eye option
        AlertDialog SymptomsPickerDialog = (AlertDialog) SymptomsPickerFragment.getDialog();
        //check the pink eye
        solo.clickOnView(SymptomsPickerDialog.getListView().getChildAt(2));
        //needs to wait or else things get messed up
        solo.waitForDialogToClose(2500);
        SymptomsPickerFragment.dismiss();
        //check that symptoms list is just pink eye
        assertEquals(1, SymptomsActivity.mSelList.size());
        assertEquals(1, SymptomsActivity.mSymptoms.size());
        assertEquals(2, (int) SymptomsActivity.mSelList.get(0));
        assertEquals("Pink Eye", SymptomsActivity.mSymptoms.get(0));
        //click on continue button
        Button continueButton = (Button) solo.getCurrentActivity().findViewById(R.id.b_continue_to_payment);
        solo.clickOnView(continueButton);
        //wait for and check that next activity is PatientPaymentActivity
        solo.waitForActivity(PatientPaymentActivity.class, 2000);
        solo.assertCurrentActivity("Expected Payment activity", PatientPaymentActivity.class);


    }


    @Override
    protected void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}
