package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.example.firebaseis1313.R;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText etAccountR;
    private EditText etPasswordR;
    private EditText etPhoneR;
    private EditText etDisplayName;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnLogin);
        etAccountR = findViewById(R.id.etAccountR);
        etPasswordR = findViewById(R.id.etPasswordR);
        etPhoneR = findViewById(R.id.etPhoneR);
        etDisplayName = findViewById(R.id.etDisplayNameR);
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                String displayName = etDisplayName.getText().toString();
                String username = etAccountR.getText().toString();
                String password = etPasswordR.getText().toString();
                String phone = etPhoneR.getText().toString();
                if (displayName.isEmpty()) {
                    etDisplayName.setError("Display name is required!");
                    etDisplayName.requestFocus();
                    return;
                }
                if (username.isEmpty()) {
                    etAccountR.setError("username is required!");
                    etAccountR.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    etPasswordR.setError("password is required!");
                    etPasswordR.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    etPhoneR.setError("phone is required!");
                    etPhoneR.requestFocus();
                    return;
                }
                if (displayName.length() > 0 && username.length() > 0 && password.length() > 0 && phone.length() > 0) {
                    user.put("displayName", etDisplayName.getText().toString());
                    user.put("username", etAccountR.getText().toString());
                    user.put("password", etPasswordR.getText().toString());
                    user.put("phone", etPhoneR.getText().toString());
                    user.put("photoUrl", "https://firebasestorage.googleapis.com/v0/b/is1313mk.appspot.com/o/user%20image%2Fmale.jpg?alt=media&token=a20ef42d-6747-433f-a819-fcd31e2da093");
                    firebaseFirestore.collection("User").add(user);
                    Toast.makeText(getApplicationContext(), "Register Succesfull !", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Fill Full Information", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void back(View view) {
        finish();
    }
}