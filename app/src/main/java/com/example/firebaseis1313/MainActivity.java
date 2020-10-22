package com.example.firebaseis1313;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etPrice;
    private EditText etColor;
    private Button btnAdd;
    private Button btnShow;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etColor = findViewById(R.id.etColor);
        btnAdd = findViewById(R.id.buttonAdd);
        btnShow = findViewById(R.id.btnShow);
        db = FirebaseFirestore.getInstance();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String , Object> product = new HashMap<>();
                product.put("name", etName.getText().toString());
                product.put("price", etPrice.getText().toString());
                product.put("color", etColor.getText().toString());
                db.collection("products")
                        .add(product);
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("products")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.i("IS1313", document.getData().toString());
                                    }
                                } else {

                                }
                            }
                        });
            }
        });
    }
}