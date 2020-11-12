package com.example.firebaseis1313.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Home;
import com.example.firebaseis1313.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private FirebaseFirestore db;
    private TextView tvForgot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnUpdate);
        etUserName = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgot = findViewById(R.id.tvForgot);
        db = FirebaseFirestore.getInstance();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (userName.trim().length() != 0 && password.trim().length() != 0) {
                    db.collection("User").whereEqualTo("username", userName).whereEqualTo("password", password).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                           
                            if (task.isSuccessful()) {
                                if (task.getResult().size() == 1) {
                                    SharedPreferences.Editor editor =getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit();
                                    editor.putBoolean("isLogin", true);
                                    editor.putString("userId",task.getResult().getDocuments().get(0).getId());
                                    editor.putString("userAvatar",task.getResult().getDocuments().get(0).get("photoUrl").toString());
                                    editor.putString("userDisplayName",task.getResult().getDocuments().get(0).get("displayName").toString());
                                    editor.putString("userName",task.getResult().getDocuments().get(0).get("username").toString());
                                    editor.putString("userPassword",task.getResult().getDocuments().get(0).get("password").toString());
                                    editor.commit();


                                    // Lay cai nay dung bo
                                    Intent getData=getIntent();
                                    String messFromDetail=getData.getStringExtra("mess_from_detail");
                                    String currentRoomId=getData.getStringExtra("room_id");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("currentTab",getData.getIntExtra("page_position",0));
                                    // get mess from login to know what page is login
                                    intent.putExtra("mess_from_login", messFromDetail);
                                   if(messFromDetail !=null && messFromDetail.equals("saveWithoutLogin")){

                                        DocumentReference washingtonRef = db.collection("User").document(task.getResult().getDocuments().get(0).getId());
                                        washingtonRef.update("listSaveRoom", FieldValue.arrayUnion(currentRoomId));
                                    }
                                       intent.putExtra("room_id",currentRoomId);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);


//                                    Intent myIntent = getIntent();
//                                    String referer = myIntent.getStringExtra("referer");
//                                    if(referer != null){
//                                        String room_id = myIntent.getStringExtra("room_id");
//                                        finish();
//                                        Intent intent = new Intent(LoginActivity.this, ReviewActivity.class);
//                                        intent.putExtra("room_id", room_id);
//                                        startActivity(intent);
//                                    }else {

//                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong Input", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Username and Password is required!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("123");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
    }



}