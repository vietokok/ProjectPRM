package com.example.firebaseis1313;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText etAccountR;
    private EditText etPasswordR;
    private EditText etPhoneR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister);
        etAccountR = findViewById(R.id.etAccountR);
        etPasswordR = findViewById(R.id.etPasswordR);
        etPhoneR = findViewById(R.id.etPhoneR);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void back(View view){
        finish();
    }
}