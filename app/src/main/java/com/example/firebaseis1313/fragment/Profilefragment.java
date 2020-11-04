package com.example.firebaseis1313.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.activity.DetailActivity;
import com.example.firebaseis1313.helper.OnFragmentInteractionListener;
import com.example.firebaseis1313.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profilefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profilefragment extends Fragment {
    private TextView displayName;
    private TextView phone;
    private TextView email;
    private TextView username;
    private CircleImageView avatar;
    private FirebaseFirestore db;

    private OnFragmentInteractionListener onFragmentInteractionListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnLogout;
    private Button btnTest;

    public Profilefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profilefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Profilefragment newInstance(String param1, String param2) {
        Profilefragment fragment = new Profilefragment();
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
        return inflater.inflate(R.layout.fragment_profilefragment, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onFragmentInteractionListener=(OnFragmentInteractionListener)getActivity();
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        btnLogout = view.findViewById(R.id.btnLogout);
        btnTest = view.findViewById(R.id.test);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                startActivity(intent);
            }
        });
        db=FirebaseFirestore.getInstance();

        displayName=view.findViewById(R.id.etDisplayName);
        phone=view.findViewById(R.id.etPhone);
        email=view.findViewById(R.id.etEmail);
        username = view.findViewById(R.id.etUsername);
        avatar=view.findViewById(R.id.imgHostAvatar);

        if(onFragmentInteractionListener.isLogin()){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
            String result = sharedPreferences.getString("userId", null);
            setProfile(result);
        }




        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = view.getRootView().getContext().getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit();
                editor.putBoolean("isLogin", false);
                editor.putString("userId", "");
                editor.putString("userAvatar","");
                editor.putString("userDisplayName","");
                editor.putString("userName","");
                editor.putString("userPassword","");
                editor.putString("destroy","123");
                editor.commit();
                getActivity().finish();
                Intent intent = new Intent(view.getRootView().getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void setProfile(String userId){
        db.collection("User").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    final Map<String, Object> list = task.getResult().getData();
                    displayName.setText(list.get("displayName").toString());
                    phone.setText(list.get("phone").toString());
                    email.setText(list.get("email").toString());
                    username.setText(list.get("username").toString());
                    Picasso.get().load(list.get("photoUrl").toString()).into(avatar);
                }else{
                    Toast.makeText(getContext(), "Some Thing Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void onTest(View view){

    }


}