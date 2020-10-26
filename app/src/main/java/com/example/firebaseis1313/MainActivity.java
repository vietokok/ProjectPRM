package com.example.firebaseis1313;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
//Load Image from an url
    private View createTabItemView(String imgUri) {
        ImageView imageView = new ImageView(this);
        TabLayout.LayoutParams params = new TabLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        Picasso.get().load("https://www.gstatic.com/webp/gallery/4.sm.jpg").into(imageView);
        return imageView;
    }

    private ViewPager viewPager;
    private TabLayout tabLayout;
    //Activity
    private HomeActivity homeActivity;
    private SearchActivity searchActivity;
    private AccountActivity accountActivity;
    private SavedActivity savedActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("asdasd");
        viewPager=findViewById(R.id.view_page);
        tabLayout=findViewById(R.id.tab_layout);

        // Khai báo Activity --------------
        homeActivity=new HomeActivity();
        searchActivity=new SearchActivity();
        accountActivity=new AccountActivity();
        savedActivity=new SavedActivity();
        //---------------------
        tabLayout.setupWithViewPager(viewPager);
        ViewPageAdapter viewPageApdater =new ViewPageAdapter(getSupportFragmentManager(),0);
        // add to apdater
        viewPageApdater.addFragment(homeActivity,getString(R.string.home));
        viewPageApdater.addFragment(searchActivity,getString(R.string.search));
        viewPageApdater.addFragment(accountActivity,getString(R.string.account));
        viewPageApdater.addFragment(savedActivity,getString(R.string.saved));
        // add to tab_layout
        // Đối vs
        viewPager.setAdapter(viewPageApdater);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
        tabLayout.getTabAt(2).setIcon(R.drawable.user);
        tabLayout.getTabAt(3).setIcon(R.drawable.apartment);


        // Đối với chức năng save khi thêm vào thì sẽ hiển thị số lượng save lên dùng code này --------------------//
//        BadgeDrawable badgeDrawable = tabLayout.getTabAt(3).getOrCreateBadge();
//        badgeDrawable.setVisible(true);
//        badgeDrawable.setNumber(12);
        //--------------------------------------//


//        db = FirebaseFirestore.getInstance();
//
//
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String , Object> product = new HashMap<>();
//                product.put("name", etName.getText().toString());
//                product.put("price", etPrice.getText().toString());
//                product.put("color", etColor.getText().toString());
//                db.collection("products")
//                        .add(product);
//            }
//        });
//
//        btnShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.collection("products")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.i("IS1313", document.getData().toString());
//                                    }
//                                } else {
//
//                                }
//                            }
//                        });
//            }
//        });
    }
}