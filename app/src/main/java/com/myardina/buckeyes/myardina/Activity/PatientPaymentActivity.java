package com.myardina.buckeyes.myardina.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myardina.buckeyes.myardina.Common.CommonConstants;
import com.myardina.buckeyes.myardina.DTO.PatientDTO;
import com.myardina.buckeyes.myardina.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class PatientPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOG_TAG = "PATIENT_PAYMENT";

    // Data objects
    private DatabaseReference paymentsTable;
    private PatientDTO mPatientDTO;

    private static PayPalConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_payment);
        Log.d(LOG_TAG, "Entering onCreate...");
        //setting custom toolbar dont remove
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        config = new PayPalConfiguration()
                // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
                // or live (ENVIRONMENT_PRODUCTION)
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                // Use CommonConstants.CONFIG_CLIENT_ID_RELEASE when going live from sandbox and be
                // sure to determine the release key first.
                .clientId(CommonConstants.CONFIG_CLIENT_ID_DEVELOPMENT);

        //starts up paypal service when activity launched
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        // UI References
        Button paypalLoginButton = (Button) findViewById(R.id.login_paypal);
        paypalLoginButton.setOnClickListener(this);
        Button continueButton = (Button) findViewById(R.id.b_continue_to_map);
        continueButton.setOnClickListener(this);

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
            case R.id.login_paypal:
                // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
                // Change PAYMENT_INTENT_SALE to
                //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
                //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
                //     later via calls from your server.
                PayPalPayment payment = new PayPalPayment(new BigDecimal(CommonConstants.FLAT_RATE),
                        CommonConstants.USD, CommonConstants.DOCTOR_TELEMEDICINE,
                        PayPalPayment.PAYMENT_INTENT_SALE);
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                // send the same configuration for restart resiliency
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivityForResult(intent, 0);
                break;
            case R.id.b_continue_to_map:
                Intent doctorsAvailableActivity = new Intent(PatientPaymentActivity.this, DoctorsAvailableActivity.class);
                doctorsAvailableActivity.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                PatientPaymentActivity.this.startActivity(doctorsAvailableActivity);
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

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        Log.d(LOG_TAG, "Entering onActivityResult...");
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    String confirm_str = confirm.toJSONObject().toString(4);
                    Log.i(LOG_TAG, confirm_str);

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                    //confirm payment to our database
                    FirebaseDatabase ref = FirebaseDatabase.getInstance();
                    paymentsTable = ref.getReference(CommonConstants.PAYMENTS_TABLE);
                    DatabaseReference childRef = PatientPaymentActivity.this.paymentsTable.push();
                    childRef.child(CommonConstants.PAYMENTS_TABLE).setValue(confirm);
                    childRef.child(CommonConstants.USER_ID).setValue(mPatientDTO.getUserKey());

                    //toast that says payment successful
                    Toast created_user_toast = Toast.makeText(getApplicationContext(), "Payment successful!", Toast.LENGTH_SHORT);
                    created_user_toast.setGravity(Gravity.CENTER, 0, 0);
                    created_user_toast.show();

                    //gos to doctors available activity
                    Intent doctorsAvailableActivity = new Intent(PatientPaymentActivity.this, DoctorsAvailableActivity.class);
                    doctorsAvailableActivity.putExtra(CommonConstants.PATIENT_DTO, mPatientDTO);
                    PatientPaymentActivity.this.startActivity(doctorsAvailableActivity);

                } catch (JSONException e) {
                    Log.e(LOG_TAG, "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i(LOG_TAG, "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i(LOG_TAG, "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
        Log.d(LOG_TAG, "Exiting onCreate");
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
