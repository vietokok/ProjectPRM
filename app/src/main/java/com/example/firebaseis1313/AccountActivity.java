package com.example.firebaseis1313;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountActivity extends Fragment {
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private ImageView avatar;
    private FirebaseFirestore db;
    private DatabaseReference mDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountActivity newInstance(String param1, String param2) {
        AccountActivity fragment = new AccountActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        avatar=view.findViewById(R.id.imageAvatar);
        Picasso.get().load("https://www.gstatic.com/webp/gallery/4.sm.jpg").into(avatar);

        //-----get pt----//
        btnLogin=view.findViewById(R.id.btn_login);
        etUserName=view.findViewById(R.id.etUserName);
        etPassword=view.findViewById(R.id.etPassword);
        db = FirebaseFirestore.getInstance();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName=etUserName.getText().toString();
                String password=etPassword.getText().toString();

                if(userName.trim() !=null && password.trim() !=null || userName.trim().length()!=0 && password.trim().length()!=0){
                    db.collection("user").whereEqualTo("user name", userName).whereEqualTo("password",password).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        View myView=view.getRootView();
                                        TabLayout tabLayout = (TabLayout) myView.findViewById(R.id.tab_layout);
                                        TabLayout.Tab tab = tabLayout.getTabAt(0);
                                        tab.select();
                                    } else {

                                    }
                                }
                            });

                }

            }
        });

        super.onViewCreated(view, savedInstanceState);
    }
}