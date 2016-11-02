package com.myardina.buckeyes.myardina;

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

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

public class PatientPaymentActivity extends AppCompatActivity {

    private Button continueButton;
    private Button paypalLoginButton;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    // note that these credentials will differ between live & sandbox environments.
    private static final String CONFIG_CLIENT_ID = "ASXfMywB-eKIJKk2fSZ9ydmY6L_g3LKLEcG1JScixKR9-t1X_cTMrTbQg5fhv-FsPwdzH3c4RNHmOjs6";

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    private static final int REQUEST_CODE_PROFILE_SHARING = 3;


    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)

            .clientId(CONFIG_CLIENT_ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        //starts up paypal service when activity launched
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        paypalLoginButton = (Button) findViewById(R.id.login_paypal);
        paypalLoginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // PAYMENT_INTENT_SALE will cause the payment to complete immediately.
                // Change PAYMENT_INTENT_SALE to
                //   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
                //   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
                //     later via calls from your server.

                PayPalPayment payment = new PayPalPayment(new BigDecimal("79.00"), "USD", "doctor telemedicine",
                        PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);

                // send the same configuration for restart resiliency
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

                startActivityForResult(intent, 0);
            }
        });

        continueButton = (Button) findViewById(R.id.b_continue_to_map);
        continueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent teleMedicineActivity = new Intent(PatientPaymentActivity.this, DoctorsAvailable.class);
                PatientPaymentActivity.this.startActivity(teleMedicineActivity);
            }
        });
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    Toast created_user_toast = Toast.makeText(getApplicationContext(), "Payment successful!", Toast.LENGTH_SHORT);
                    created_user_toast.setGravity(Gravity.CENTER, 0, 0);
                    created_user_toast.show();

                    Intent teleMedicineActivity = new Intent(PatientPaymentActivity.this, DoctorsAvailable.class);
                    PatientPaymentActivity.this.startActivity(teleMedicineActivity);

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.

                    //some firebase stuff should go here to keep track of payment

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }
}
