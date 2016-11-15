package com.myardina.buckeyes.myardina.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.R;

import java.util.ArrayList;


public class SymptomsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "SYMPTOMS_ACTIVITY";

    // Data transfer object
    private PatientDTO mPatientDTO;

    //list of selected items, for now is in integers referencing the multi choice items in fragment below
    private static ArrayList<Integer> mSelList;
    //this second list will actually keep the text of the symptoms, to be extracted per body part
    private static  ArrayList<String> mSymptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        Log.d(LOG_TAG, "Entering onCreate...");
        //setting custom toolbar dont remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // Initialize UI elements
        Button bHead = (Button) findViewById(R.id.mv_head_button);
        bHead.setOnClickListener(this);
        Button bChest = (Button) findViewById(R.id.mv_chest_button);
        bChest.setOnClickListener(this);
        Button bAbdomen = (Button) findViewById(R.id.mv_abdomen_button);
        bAbdomen.setOnClickListener(this);
        Button bArm1 = (Button) findViewById(R.id.mv_arm1_button);
        bArm1.setOnClickListener(this);
        Button bArm2 = (Button) findViewById(R.id.mv_arm2_button);
        bArm2.setOnClickListener(this);
        Button bLegs = (Button) findViewById(R.id.mv_legs_button);
        bLegs.setOnClickListener(this);

        Button continueButton = (Button) findViewById(R.id.b_continue_to_payment);
        continueButton.setOnClickListener(this);

        mSelList = new ArrayList<>();
        mSymptoms = new ArrayList<>();

        mPatientDTO = (PatientDTO) getIntent().getExtras().get(CommonConstants.PATIENT_DTO);
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
        switch(v.getId()) {
            case R.id.mv_head_button:
                showSymptomsPickerDialog(CommonConstants.MY_HEAD);
                break;
            case R.id.mv_chest_button:
                showSymptomsPickerDialog(CommonConstants.MY_CHEST);
                break;
            case R.id.mv_abdomen_button:
                showSymptomsPickerDialog(CommonConstants.MY_ABDOMEN);
                break;
            case R.id.mv_arm1_button:
                showSymptomsPickerDialog(CommonConstants.MY_ARM);
                break;
            case R.id.mv_arm2_button:
                showSymptomsPickerDialog(CommonConstants.MY_ARM);
                break;
            case R.id.mv_legs_button:
                showSymptomsPickerDialog(CommonConstants.MY_LEGS);
                break;
            case R.id.b_continue_to_payment:
                Intent paymentActivity = new Intent(SymptomsActivity.this, PatientPaymentActivity.class);
                paymentActivity.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                SymptomsActivity.this.startActivity(paymentActivity);
                break;
            default:
                break;
        }
        Log.d(LOG_TAG, "Exiting onClick...");
    }

    /**
     ******************************
     *  PRIVATE BACKGROUND LOGIC  *
     ******************************
     */

    //this method shows the symptoms picker dialog
    //passes body part as variable to the fragment
    private void showSymptomsPickerDialog(String body_part) {
        Log.d(LOG_TAG, "Entering showSymptomsPickerDialog...");
        FragmentManager fm = this.getFragmentManager();
        DialogFragment newFragment = new SymptomsPickerFragment();
        Bundle args = new Bundle();
        args.putString(CommonConstants.BODY_PART, body_part);
        newFragment.setArguments(args);
        newFragment.show(fm, CommonConstants.SYMPTOMS_PICKER);
        Log.d(LOG_TAG, "Exiting showSymptomsPickerDialog...");
    }

    //fragment that will have dialog to pick symptoms, depending on body part chosen
    public static class SymptomsPickerFragment extends DialogFragment {

        //list of symptoms options, for now arbitrary, eventually will have to extract from database
        String[] symptomsList = { "Migraine", "Sore Throat", "Pink Eye", "Ear Ache" };
        //will eventually be replaced by dynamically generated list extracted from Firebase based on the body part
        boolean[] bl = new boolean[symptomsList.length];

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.d(LOG_TAG, "Entering onCreateDialog...");
//            Bundle args = getArguments();
//            String body_part = args.getString(CommonConstants.BODY_PART);

            // Use the AlertDialog.Builder to configure the AlertDialog.
            AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(this.getActivity())
                        .setTitle(getString(R.string.choose_3_symptoms))
                        .setMultiChoiceItems(symptomsList, bl, new DialogInterface.OnMultiChoiceClickListener() {

                            //still need to write code here that makes sure that when the alertdialog is initialized, it already checks the items that were selected before
                            //for example one clicks on the head and selects pink eye, then clicks out, and then opens the alert dialog for head again,
                            //make sure that pink eye checkbox is selected, otherwise can have problems with duplicates of symptoms arising

                            @Override
                            public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
                                if(arg2)
                                {
                                    //if selects a fourth item, remove first selected item
                                    if (mSelList.size()==3){
                                        int removed = mSelList.remove(0);
                                        mSymptoms.remove(0);
                                        //un check the the first checkbox selected too
                                        bl[removed] = false;
                                        ((AlertDialog) arg0).getListView().setItemChecked(removed, false);
                                    }
                                    // If user select a item then add it in selected items
                                    mSelList.add(arg1);
                                    mSymptoms.add(symptomsList[arg1]);

                                    for (int i=0; i<mSelList.size(); i++){
                                        System.out.println(mSymptoms.get(i));
                                    }
                                }
                                else if (mSelList.contains(arg1))
                                {
                                    // if the item is already selected then remove it
                                    int removed = mSelList.remove(mSelList.indexOf(arg1));
                                    mSymptoms.remove(mSymptoms.indexOf(symptomsList[arg1]));
                                    bl[arg1] = false;
                                    ((AlertDialog) arg0).getListView().setItemChecked(removed, false);

                                    for (int i=0; i<mSelList.size(); i++){
                                        System.out.println(mSymptoms.get(i));
                                    }
                                }
                            }
                        });
            Log.d(LOG_TAG, "Exiting onCreateDialog...");

            // Show the AlertDialog
            return alertDialogBuilder.show();
        }
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
