package com.myardina.buckeyes.myardina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseDatabase ref;
    private DatabaseReference usersTable;
    DatabaseReference childRef;
    Spinner doctor_availability_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        ref = FirebaseDatabase.getInstance();
        usersTable = ref.getReference("Users");
        childRef = DoctorActivity.this.usersTable.push();

        doctor_availability_spinner = (Spinner) findViewById(R.id.spinner_doctor_availability);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.doctor_availability_spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        doctor_availability_spinner.setAdapter(adapter);
        doctor_availability_spinner.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            //if yes is selected doctor is available
            case R.id.spinner_doctor_availability:

                if (position == 0) childRef.child("Available").setValue("Y");
                else if( position == 1) childRef.child("Available").setValue("Y");
                break;
            //else not
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
