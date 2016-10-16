package com.myardina.buckeyes.myardina;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    Intent intent;
    TextView tvHelloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = this.getIntent();
        String firstName = intent.getStringExtra("user_name");

        tvHelloWorld = (TextView)findViewById(R.id.tvHelloWorld);
        tvHelloWorld.setText("Hello, " + firstName + "!");
    }
}
