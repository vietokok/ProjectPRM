package com.example.firebaseis1313.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebaseis1313.R;
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
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db;
    private ArrayList<Room> list_room_order_by_date;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public void setListRoom(final View view) {
        db.collection("Motel")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println(task.getResult().getDocuments().size());
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                final Map<String, Object> list = document.getData();
                                final Room e = new Room();
                                e.setId(document.getId());
                                db.collection("Image").document(list.get("image_id").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            ArrayList<String> listImageUrl=(ArrayList<String>)task.getResult().get("url");
                                            e.setImageUrl(listImageUrl.get(0));
                                            e.setAcreage(Float.parseFloat(list.get("area").toString()));
                                            e.setDescription(list.get("description").toString());
                                            e.setPrice(list.get("price").toString());
                                            e.setHouse_id(list.get("home_id").toString());
                                            db.collection("Home").document(e.getHouse_id())
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                                    if (task1.isSuccessful()) {
                                                        e.setAddress(task1.getResult().getData().get("address").toString());
                                                        ListRoomFragment listRoomFragment = (ListRoomFragment) getChildFragmentManager().findFragmentById(R.id.list_room_frag);
                                                        listRoomFragment.receiveData(e);
                                                    } else {
                                                    }
                                                }
                                            });
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setListRoom(view);
        super.onViewCreated(view, savedInstanceState);
    }
}