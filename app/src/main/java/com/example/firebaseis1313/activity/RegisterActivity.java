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

public class  RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText etAccountR;
    private EditText etPasswordR;
    private EditText etPhoneR;
    private EditText etDisplayName;
    private EditText etEmailR;
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
        etEmailR = findViewById(R.id.etEmailR);
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                if (validateDisplayName(etDisplayName) && validateUsername(etAccountR) && validatePassword(etPasswordR) && validatePhone(etPhoneR) && validateEmail(etEmailR)) {
                    user.put("displayName", etDisplayName.getText().toString());
                    user.put("username",  etAccountR.getText().toString());
                    user.put("password", etPasswordR.getText().toString());
                    user.put("phone", etPhoneR.getText().toString());
                    user.put("email", etEmailR.getText().toString());
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

    private boolean validateDisplayName(EditText dpName){
        if(dpName.getText().toString().isEmpty()){
            dpName.setError("Field can't empty");
            return false;
        }else {
            dpName.setError(null);
            return true;
        }
    }

    private boolean validateUsername(EditText username){
        String regex ="\\A\\w{4,20}\\z";
        if(username.getText().toString().isEmpty()){
            username.setError("Field can't empty!");
            return false;
        }else if(username.getText().toString().length() >= 14){
            username.setError("Username too long!");
            return false;
        }else if(!username.getText().toString().matches(regex)){
            username.setError("White Spaces are not allowed");
            return false;
        }
        else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePassword(EditText password){
        if(password.getText().toString().isEmpty()){
            password.setError("Field can't empty");
            return false;
        }else if(password.getText().toString().length() > 10){
            password.setError("Phone too long!");
            return false;
        }
        else {
            password.setError(null);
            return true;
        }
    }

    private boolean validatePhone(EditText phone){
        if(phone.getText().toString().isEmpty()){
            phone.setError("Field can't empty");
            return false;
        }else if(phone.getText().toString().length() > 10){
            phone.setError("Phone too long!");
            return false;
        }
        else {
            phone.setError(null);
            return true;
        }
    }

    private boolean validateEmail(EditText email){
        String regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.getText().toString().isEmpty()){
            email.setError("Field can't empty!");
            return false;
        }
        else if(!email.getText().toString().matches(regex)){
            email.setError("Invalid email address");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    public void back(View view) {
        finish();
    }
}