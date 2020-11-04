package com.example.firebaseis1313.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.activity.DetailActivity;
import com.example.firebaseis1313.activity.LoginActivity;
import com.example.firebaseis1313.entity.Room;
import com.example.firebaseis1313.helper.RoomViewAdapter;
import com.example.firebaseis1313.main.MainActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListRoomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RoomViewAdapter room_view_apdapter;
    private ListView list_view_room;
    private ArrayList<Room> list_room;

    int Querytype = -1;

    // quy dinh 0 : home 1 search 2 dien tich 3 khoang cach 4 saved
    public ListRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListRoomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListRoomFragment newInstance(String param1, String param2) {
        ListRoomFragment fragment = new ListRoomFragment();
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
        return inflater.inflate(R.layout.fragment_list_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list_room = new ArrayList<>();
        list_view_room = view.findViewById(R.id.list_room);
        room_view_apdapter = new RoomViewAdapter(getActivity(), list_room);
        list_view_room.setAdapter(room_view_apdapter);
        list_view_room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String room_id = list_room.get(i).getId();
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("room_id",room_id);
                startActivity(intent);
            }
        });
    }

    public void clearData() {
        list_room.clear();
        room_view_apdapter.notifyDataSetChanged();
    }

    public void receiveData(Room room) {
        list_room.add(room);
        room_view_apdapter.notifyDataSetChanged();
    }
}