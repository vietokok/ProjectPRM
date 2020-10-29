package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.firebaseis1313.R;
import android.widget.Toast;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = findViewById(R.id.googleMapImg);
        String url = "https://maps.googleapis.com/maps/api/staticmap?center=21.01325,105.5248756&zoom=17&size=600x400&markers=color:red%7C21.01325,105.5248756&key=AIzaSyA3kg7YWugGl1lTXmAmaBGPNhDW9pEh5bo";
        Picasso.get()
                .load(url)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("done");
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println("error");
                    }
                });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}