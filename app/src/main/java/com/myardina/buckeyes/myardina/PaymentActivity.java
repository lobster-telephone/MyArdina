package com.myardina.buckeyes.myardina;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PaymentActivity extends AppCompatActivity {

    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        continueButton = (Button) findViewById(R.id.b_continue_to_map);
        continueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent teleMedicineActivity = new Intent(PaymentActivity.this, TeleMedicineActivity.class);
                PaymentActivity.this.startActivity(teleMedicineActivity);
            }
        });
    }

}
