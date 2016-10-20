package com.myardina.buckeyes.myardina;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class SymptomsActivity extends AppCompatActivity {

    //list of selected items, for now is in integers referencing the multichoice items in fragment below
    static ArrayList<Integer> selList=new ArrayList();
    //this second list will actually keep the text of the symptoms, to be extracted per body part
    static ArrayList<String> symptoms=new ArrayList();

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);


        //if head button was clicked on male ventral view, show the symptoms picker with symptoms of the head
        Button MVShowHeadSymptomsPickerButton = (Button) findViewById(R.id.mv_head_button);
        MVShowHeadSymptomsPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call showDatePickerDialog to show it
                showSymptomsPickerDialog("mv_head");
            }
        });
        //if head button was clicked on male ventral view, show the symptoms picker with symptoms of the head
        Button MVShowChestSymptomsPickerButton = (Button) findViewById(R.id.mv_chest_button);
        MVShowChestSymptomsPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call showDatePickerDialog to show it
                showSymptomsPickerDialog("mv_chest");
            }
        });
        //if head button was clicked on male ventral view, show the symptoms picker with symptoms of the head
        Button MVShowAbdomenSymptomsPickerButton = (Button) findViewById(R.id.mv_abdomen_button);
        MVShowAbdomenSymptomsPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call showDatePickerDialog to show it
                showSymptomsPickerDialog("mv_abdomen");
            }
        });
        //if head button was clicked on male ventral view, show the symptoms picker with symptoms of the head
        Button MVShowArm1SymptomsPickerButton = (Button) findViewById(R.id.mv_arm1_button);
        MVShowArm1SymptomsPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call showDatePickerDialog to show it
                showSymptomsPickerDialog("mv_arm");
            }
        });
        //if head button was clicked on male ventral view, show the symptoms picker with symptoms of the head
        Button MVShowArm2SymptomsPickerButton = (Button) findViewById(R.id.mv_arm2_button);
        MVShowArm2SymptomsPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call showDatePickerDialog to show it
                showSymptomsPickerDialog("mv_arm");
            }
        });
        //if head button was clicked on male ventral view, show the symptoms picker with symptoms of the head
        Button MVShowLegsSymptomsPickerButton = (Button) findViewById(R.id.mv_legs_button);
        MVShowLegsSymptomsPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call showDatePickerDialog to show it
                showSymptomsPickerDialog("mv_legs");
            }
        });

        continueButton = (Button) findViewById(R.id.b_continue_to_payment);
        continueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent paymentActivity = new Intent(SymptomsActivity.this, PatientPaymentActivity.class);
                SymptomsActivity.this.startActivity(paymentActivity);
            }
        });
    }

    //this method shows the symptoms picker dialog
    //passes body part as variable to the fragment
    public void showSymptomsPickerDialog(String body_part) {
        FragmentManager fm = this.getFragmentManager();
        DialogFragment newFragment = new SymptomsPickerFragment();
        Bundle args = new Bundle();
        args.putString("body_part", body_part);
        newFragment.setArguments(args);
        newFragment.show(fm, "symptomsPicker");
    }

    //fragment that will have dialog to pick symptoms, depending on body part chosen
    public static class SymptomsPickerFragment extends DialogFragment{

        //list of symptoms options, for now arbitrary, eventually will have to extract from database
        CharSequence symptomsList[] = { "Migraine", "Sore Throat", "Pink Eye", "Ear Ache" };
        //will eventually be replaced by dynamically generated list extracted from firebase based on the body part
        boolean bl[] = new boolean[symptomsList.length];


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle args = getArguments();
            String body_part = args.getString("body_part");

            // Use the AlertDialog.Builder to configure the AlertDialog.
            AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(this.getActivity())
                            .setTitle("Pick up to three symptoms you have")
                            .setMultiChoiceItems(symptomsList, bl, new DialogInterface.OnMultiChoiceClickListener() {

                                //still need to write code here that makes sure that when the alertdialog is initialized, it already checks the items that were selected before
                                //for example one clicks on the head and selects pink eye, then clicks out, and then opens the alert dialog for head again,
                                //make sure that pink eye checkbox is selected, otherwise can have problems with duplicates of symptoms arising

                                @Override
                                public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
                                    // TODO Auto-generated method stub

                                    if(arg2)
                                    {

                                        //if selects a fourth item, remove first selected item
                                        if (selList.size()==3){
                                            int removed = selList.remove(0);
                                            symptoms.remove(0);
                                            //uncheck the the first checkbox selected too
                                            bl[removed] = false;
                                            ((AlertDialog) arg0).getListView().setItemChecked(removed, false);



                                        }
                                        // If user select a item then add it in selected items
                                        selList.add(arg1);
                                        symptoms.add((String)symptomsList[arg1]);

                                        for (int i=0; i<selList.size(); i++){
                                            System.out.println(symptoms.get(i));
                                        }

                                    }
                                    else if (selList.contains(arg1))
                                    {
                                        // if the item is already selected then remove it
                                        int removed = selList.remove(selList.indexOf(Integer.valueOf(arg1)));
                                        symptoms.remove(symptoms.indexOf((String)symptomsList[arg1]));
                                        bl[arg1] = false;
                                        ((AlertDialog) arg0).getListView().setItemChecked(removed, false);


                                        for (int i=0; i<selList.size(); i++){
                                            System.out.println(symptoms.get(i));
                                        }
                                    }
                                }
                            });

            // Show the AlertDialog.
            AlertDialog symptomsDialog = alertDialogBuilder.show();

            return symptomsDialog;
        }


    }
}
