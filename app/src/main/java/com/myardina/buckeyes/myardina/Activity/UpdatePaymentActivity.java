package com.myardina.buckeyes.myardina.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.DoctorDTO;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.DTO.PaymentDTO;
import com.myardina.buckeyes.myardina.DTO.PendingPaymentDTO;
import com.myardina.buckeyes.myardina.R;
import com.myardina.buckeyes.myardina.Sevice.Impl.PaymentServiceImpl;
import com.myardina.buckeyes.myardina.Sevice.PaymentService;

public class UpdatePaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "UPDATE_PAYMENT";

    private PendingPaymentDTO mPendingPaymentDTO;

    private PaymentService mPaymentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Entering onCreate...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_payment);
        //setting custom toolbar don't remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        TextView mTvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        TextView mTvPatientName = (TextView) findViewById(R.id.tv_patient_name);
        TextView mTvPaymentAmount = (TextView) findViewById(R.id.tv_payment_amount);

        Button payDoctorButton = (Button) findViewById(R.id.b_pay_doctor);
        Button backButton = (Button) findViewById(R.id.b_back_button);
        backButton.setOnClickListener(this);

        mPendingPaymentDTO = (PendingPaymentDTO) getIntent().getExtras().get(CommonConstants.PENDING_PAYMENT_DTO);
        if (mPendingPaymentDTO != null) {
            DoctorDTO doctorDTO = mPendingPaymentDTO.getDoctorDTO();
            PatientDTO patientDTO = mPendingPaymentDTO.getPatientDTO();
            PaymentDTO paymentDTO = mPendingPaymentDTO.getPaymentDTO();

            mTvDoctorName.setText(String.format("%s %s", doctorDTO.getFirstName(), doctorDTO.getLastName()));
            mTvPatientName.setText(String.format("%s %s", patientDTO.getFirstName(), patientDTO.getLastName()));
            mTvPaymentAmount.setText(paymentDTO.getAmountOwedToDoctor());

            payDoctorButton.setOnClickListener(this);
        } else {
            mTvDoctorName.setText(CommonConstants.ERROR_RETRIEVE_DOCTOR);
            payDoctorButton.setEnabled(false);
        }

        mPaymentService = new PaymentServiceImpl();

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
            case R.id.b_pay_doctor:
                mPaymentService.updatePaidPendingPayment(mPendingPaymentDTO.getPaymentDTO());
                break;
            case R.id.b_back_button:
                break;
            default:
                break;
        }
        Intent adminActivity = new Intent(UpdatePaymentActivity.this, AdminActivity.class);
        startActivity(adminActivity);
        Log.d(LOG_TAG, "Exiting onClick...");
    }

    /**
     * *************************
     * ACTIVITY STATE LOGIC  *
     * *************************
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
