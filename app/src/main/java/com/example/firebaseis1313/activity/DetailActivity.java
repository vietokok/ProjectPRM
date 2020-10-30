package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.More;
import com.example.firebaseis1313.helper.MoreAdapter;
import com.google.api.Http;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button btnMore;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};
    private String[] items;
    private List<More> moreList;
    private MoreAdapter moreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        btnMore = findViewById(R.id.btnMore);
        carouselView = findViewById(R.id.carouselView);
        items = new String[]{"Gọi điện", "Chỉ đường", "Nhắn tin"};

        carouselView.setPageCount(4);
        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(DetailActivity.this, ImageActivity.class);
                intent.putExtra("imageIndex", position);
                intent.putExtra("images", sampleImages);
                startActivityForResult(intent, 100);
            }
        });

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

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

    public void onButtonMoreClick(View view) {
        moreList = new ArrayList<>();
        moreList.add(new More(1, "Gọi điện"));
        moreList.add(new More(2, "Nhắn tin"));
        moreList.add(new More(3, "Chỉ đường"));

        moreAdapter = new MoreAdapter(DetailActivity.this, moreList);
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(DetailActivity.this);

        builderSingle.setAdapter(moreAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:0123456789"));
                    startActivity(intent);
                } else if (which == 1) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("smsto:019232323"));  // This ensures only SMS apps respond
                    intent.putExtra("sms_body", "ok");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                } else {
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=Trường+Đại+Học+FPT");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
            }
        });
        builderSingle.show();
        System.out.println("okokok");
    }
}