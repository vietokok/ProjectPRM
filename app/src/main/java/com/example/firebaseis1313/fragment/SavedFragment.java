package com.example.firebaseis1313.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Room;
import com.example.firebaseis1313.helper.OnFragmentInteractionListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Fragment login;
    private ArrayList<Room> saved_room;
    private OnFragmentInteractionListener onFragmentInteractionListener;
//    private OnFragmentInteractionListener mListtener;

    private FirebaseFirestore db;


    public SavedFragment() {
        // Required empty public constructor
    }


    public void setListRoom(final View view,String userId) {
    // From user get ListSave
        db.collection("User").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<String> list_room_id = (ArrayList<String>) task.getResult().get("listSaveRoom");
                    for (String room_id: list_room_id) {
                        // From List Saved Get room_id
                        db.collection("Motel").document(room_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    final Map<String, Object> list = task.getResult().getData();
                                    final Room e = new Room();
                                    e.setAcreage(Float.parseFloat(list.get("area").toString()));
                                    e.setDescription(list.get("description").toString());
                                    e.setPrice(list.get("price").toString());
                                    e.setHouse_id(list.get("home_id").toString());
                                    // from room_id get room and get image_id from room to get image_url From table Image
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
                                                //From home_id from room get address from Home Table
                                                db.collection("Home").document(e.getHouse_id())
                                                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task1) {
                                                        if (task1.isSuccessful()) {
                                                            e.setAddress(task1.getResult().getData().get("address").toString());
                                                            ListRoomFragment listRoomFragment = (ListRoomFragment) getChildFragmentManager().findFragmentById(R.id.list_room_saved);
                                                            listRoomFragment.receiveData(e);
                                                        } else {
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });

                                }
                            }
                        });
                    }
                }else{
                }

            }
        });

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SavedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SavedFragment newInstance(String param1, String param2) {
        SavedFragment fragment = new SavedFragment();
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
        saved_room=new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        return inflater.inflate(R.layout.fragment_saved_activity, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onFragmentInteractionListener=(OnFragmentInteractionListener)getActivity();
//        mListtener=(OnFragmentInteractionListener) getActivity();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        btn=view.findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListtener.setSavedRoom();
//            }
//        });

        if(onFragmentInteractionListener.isLogin()){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("isLogin", MODE_PRIVATE);
            String result = sharedPreferences.getString("userId", null);
            setListRoom(view,result);
        }else{
            LoginFragment login = new LoginFragment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.saveFragment, login);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }

}