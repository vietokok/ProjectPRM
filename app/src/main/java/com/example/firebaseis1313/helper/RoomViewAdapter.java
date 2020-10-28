package com.example.firebaseis1313.helper;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Room;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomViewAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Room> listRoom;

    public RoomViewAdapter(Activity activity, ArrayList<Room> listRoom) {
        this.activity = activity;
        this.listRoom = listRoom;
    }

    @Override
    public int getCount() {
        return listRoom.size();
    }

    @Override
    public Object getItem(int position) {
        return listRoom.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public String getItemIdByMe(int position){
        return listRoom.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        TextView descresption;
        TextView price;
        TextView addrress;
        ConstraintLayout constraintLayout;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.room_view, null);
            imageView = convertView.findViewById(R.id.imageView);
            constraintLayout=convertView.findViewById(R.id.viewDes);
//            descresption=constraintLayout.findViewById(R.id.tvDes);
//            price=constraintLayout.findViewById(R.id.tvPrice);
            convertView.setTag(R.id.imageView, imageView);
            convertView.setTag(R.id.viewDes,constraintLayout);

        }
        else{
            imageView = (ImageView) convertView.getTag(R.id.imageView);
            constraintLayout = (ConstraintLayout) convertView.getTag(R.id.viewDes);
        }
        descresption=constraintLayout.findViewById(R.id.tvDes);
        price=constraintLayout.findViewById(R.id.tvPrice);
        addrress=constraintLayout.findViewById(R.id.tvAddress);
        Room room =listRoom.get(position);
        descresption.setText("\t*"+room.getDescription());
        String validatePrice="";
        String db_price=room.getPrice();
        int index=-1;
        for(int i=room.getPrice().length()-1;i>=0;i--){
            index++;
            validatePrice+=db_price.charAt(i);
            if(index==2 && i!=0){
                validatePrice+=".";
                index=-1;
            }

        }
        String reverse = new StringBuffer(validatePrice).reverse().toString();
        reverse=reverse+"VND/tháng";
        price.setText(reverse);
        addrress.setText(room.getAddress());
        Picasso.get().load(room.getImageUrl()).into(imageView);

        return convertView;
    }
}