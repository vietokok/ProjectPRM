package com.example.firebaseis1313.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.activity.FindActivity;
import com.example.firebaseis1313.activity.FindByPriceActivity;
import com.example.firebaseis1313.entity.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button btnPrice;
    private Button btnArea;
    private Button btnDistance;
    private Handler handler = new Handler();
    public static int REQUEST_CODE = 100;
    public static int RESULT_CODE = 200;
    private ProgressBar progressBar;
    public ListRoomFragment listRoomFragment;
    int price;
    int area;
    FirebaseFirestore db;
    private ArrayList<Room> list_room;

    public SearchFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        return inflater.inflate(R.layout.fragment_search_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // add depen
        db = FirebaseFirestore.getInstance();
        listRoomFragment =(ListRoomFragment)getChildFragmentManager().findFragmentById(R.id.list_room_frag_k);

        btnPrice = view.findViewById(R.id.btnPrice);
        btnArea = view.findViewById(R.id.btnArea);
        btnDistance = view.findViewById(R.id.btnDistance);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getRootView().getContext(), FindByPriceActivity.class);
                intent.putExtra("type", "1");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getRootView().getContext(), FindActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getRootView().getContext(), FindActivity.class);
                intent.putExtra("type", "3");

                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    public void setList_room(int type, int value){
        String field = null;
        if(type == 1) {
           field = "price";
        }
        if(type == 2){
            field = "acreage";
        }
        if(type == 3){
            field = "distan";
        }
        final ArrayList<Room> rooms = new ArrayList<>();
        db.collection("Motel")
                .whereLessThanOrEqualTo(field, value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull final Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                Map<String,Object> list=document.getData();
                                final Room e =new Room();
                                e.setId(document.getId());
                                ArrayList a = (ArrayList) list.get("image_id");
                                e.setImageUrl(a.get(0).toString());
                                e.setAcreage(Float.parseFloat(list.get("area").toString()));
                                e.setDescription(list.get("description").toString());
                                e.setPrice(list.get("price").toString());
                                e.setHouse_id(list.get("house_id").toString());
                                db.collection("House").document(e.getHouse_id())
                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                        if(task1.isSuccessful()){
                                            e.setAddress(task1.getResult().getData().get("address").toString());
                                            listRoomFragment.receiveData(e);
                                        }else{
                                        }
                                    }
                                });
                            }
                        } else {

                        }
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            listRoomFragment.clearData();
            //value is area or distance
            final int value = data.getIntExtra("value",0);
            final String type = data.getStringExtra("type");
            final String textValue = data.getStringExtra("textValue");
            // min max for price
            final int min = data.getIntExtra("min",0);
            final int max = data.getIntExtra("max",0);
            //type = search for price
            if(type.equals("1")){
                btnPrice.setText(textValue);

            }if(type.equals("2")){
                btnArea.setText(textValue);
            }
            this.progressBar.setIndeterminate(true);
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });

                    SystemClock.sleep(3000);
                    progressBar.setIndeterminate(false);
                    progressBar.setMax(1);
                    progressBar.setProgress(1);

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            //setListByPrice(min, max);
                            //setList_room(Integer.parseInt(type),max);
                        }
                    });
                }
            });
            thread.start();
        }
    }


}