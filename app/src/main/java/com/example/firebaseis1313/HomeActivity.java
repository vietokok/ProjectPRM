package com.example.firebaseis1313;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<Room> list_room;
    private RoomViewAdapter room_view_apdapter;
    private ListView list_view_room;

    private FirebaseFirestore db;

    public HomeActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeActivity newInstance(String param1, String param2) {
        HomeActivity fragment = new HomeActivity();
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
        db = FirebaseFirestore.getInstance();
        list_room=new ArrayList<>();




        return inflater.inflate(R.layout.fragment_home_activity, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_view_room = view.findViewById(R.id.list_room);
        setListRoom(view);
        room_view_apdapter=new RoomViewAdapter(getActivity(),list_room);
        list_view_room.setAdapter(room_view_apdapter);
        list_view_room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = list_view_room.getItemAtPosition(i).toString();
                System.out.println(s);
                // Doi KV
//                Intent intent =new Intent(view.getRootView().getContext(),De)
            }
        });

    }
    public void setListRoom(final View view){
        final ArrayList<Room> room = new ArrayList<>();
                db.collection("Room")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String,Object> list=document.getData();
                                Room e =new Room();
                                e.setId(document.getId());
                                ArrayList a = (ArrayList) list.get("image_id");
                                e.setImageUrl(a.get(0).toString());
                                e.setAcreage(Float.parseFloat(list.get("acreage").toString()));
                                e.setDescription(list.get("description").toString());
                                e.setPrice(Float.parseFloat(list.get("price").toString()));
                                list_room.add(e);
                            }
                            room_view_apdapter.notifyDataSetChanged();
                        } else {

                        }
                    }
                });
    }
    public void receiveData(ArrayList<Room> rooms){

        // call database
//        db.collection("products").document(room_id)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.i("IS1313", document.getData().toString());
//                            }
//                        } else {
//
//                        }
//                    }
//                });
    }
}