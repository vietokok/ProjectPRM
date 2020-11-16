    package com.example.firebaseis1313.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.firebaseis1313.R;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

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
        btnRegister = findViewById(R.id.btnUpdate);
        etAccountR = findViewById(R.id.etAccountP);
        etPasswordR = findViewById(R.id.etPasswordR);
        etPhoneR = findViewById(R.id.etPhoneP);
        etDisplayName = findViewById(R.id.etDisplayNameR);
        etEmailR = findViewById(R.id.etEmailP);
        firebaseFirestore = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("AAAAAAA");
                String username = etAccountR.getText().toString();
              firebaseFirestore.collection("User").whereEqualTo("username",username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().size() == 1){
                                Toast.makeText(getApplicationContext(), "Username is already", Toast.LENGTH_SHORT).show();
                            }else{
                                Map<String, Object> user = new HashMap<>();
                                if (validateDisplayName(etDisplayName) && validateUsername(etAccountR) && validatePassword(etPasswordR) && validatePhone(etPhoneR) && validateEmail(etEmailR)) {
                                    user.put("displayName", etDisplayName.getText().toString());
                                    user.put("username",  etAccountR.getText().toString());
                                    user.put("password", etPasswordR.getText().toString());
                                    user.put("phone", etPhoneR.getText().toString());
                                    user.put("email", etEmailR.getText().toString());
                                    user.put("listSaveRoom", new ArrayList<>());
                                    user.put("photoUrl", "https://firebasestorage.googleapis.com/v0/b/is1313mk.appspot.com/o/user%20image%2Fmale.jpg?alt=media&token=a20ef42d-6747-433f-a819-fcd31e2da093");
                                    firebaseFirestore.collection("User").add(user);
                                    Toast.makeText(getApplicationContext(), "Register Succesfull !", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Fill Full Information", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                  }
              });
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

    public boolean validateUsername(EditText username){
        String regex ="\\A\\w{4,20}\\z";
        if(username.getText().toString().isEmpty()){
            username.setError("Field can't empty!");
            return false;
        }else if(username.getText().toString().length() <= 4){
            username.setError("Username too short!");
            return false;
        }
        else if(username.getText().toString().length() >= 14){
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

    public boolean validatePassword(EditText password){
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

    public boolean validatePhone(EditText phone){
        if(phone.getText().toString().isEmpty()){
            phone.setError("Field can't empty");
            return false;
        }else if(!phone.getText().toString().matches("[0-9]+")){
            phone.setError("Phone can't equal text");
            return false;
        }
        else if(phone.getText().toString().length() > 10 || phone.getText().toString().length() <8){
            phone.setError("Phone have 8-10 number");
            return false;
        } else {
            phone.setError(null);
            return true;
        }
    }

    public boolean validateEmail(EditText email){
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