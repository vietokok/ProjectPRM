package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.firebaseis1313.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class ImageActivity extends AppCompatActivity {
    private Button btnExit;
    CarouselView carouselView;
    int[] sampleImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        carouselView = findViewById(R.id.carouselView);
        btnExit = findViewById(R.id.btnExit);
        Intent intent = getIntent();
        sampleImages = intent.getIntArrayExtra("images");
        int index = intent.getIntExtra("imageIndex", 0);
        carouselView.setPageCount(4);
        carouselView.setImageListener(imageListener);
        carouselView.setCurrentItem(index);

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                setResult(100, myIntent);
                finish();
            }
        });
    }
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };
}