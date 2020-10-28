package com.example.firebaseis1313.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Room;
import com.example.firebaseis1313.fragment.AccountFragment;
import com.example.firebaseis1313.fragment.HomeFragment;
import com.example.firebaseis1313.fragment.ListRoomFragment;
import com.example.firebaseis1313.fragment.SavedFragment;
import com.example.firebaseis1313.fragment.SearchFragment;
import com.example.firebaseis1313.helper.ViewPageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
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
    private ListRoomFragment listRoomFragment;
    private SearchFragment searchFragment;
    private AccountFragment accountFragment;
    private SavedFragment savedFragment;
    private HomeFragment homeFragment;

    private int numberOfSaved;

    //dv
    private FirebaseFirestore db;
    SharedPreferences onBoardingScreen;
    private  Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=FirebaseFirestore.getInstance();
//        sdasdasdasdasdasd
        viewPager=findViewById(R.id.view_page);
        tabLayout=findViewById(R.id.tab_layout);

        // Khai báo Activity --------------
        listRoomFragment =new ListRoomFragment();
        searchFragment =new SearchFragment();
        accountFragment =new AccountFragment();
        savedFragment =new SavedFragment();
        homeFragment=new HomeFragment();
        //---------------------
        tabLayout.setupWithViewPager(viewPager);
        ViewPageAdapter viewPageApdater =new ViewPageAdapter(getSupportFragmentManager(),0);
        // add to apdater
        viewPageApdater.addFragment(homeFragment,"Home");
//        viewPageApdater.addFragment(listRoomFragment,getString(R.string.home));
        viewPageApdater.addFragment(searchFragment,getString(R.string.search));
        viewPageApdater.addFragment(accountFragment,getString(R.string.account));
        viewPageApdater.addFragment(savedFragment,getString(R.string.saved));
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
        System.out.println("123");
        setSavedNumber();
    }

    public void setSavedNumber(){
        db.collection("Room")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            BadgeDrawable badgeDrawable = tabLayout.getTabAt(3).getOrCreateBadge();
                            badgeDrawable.setVisible(true);
                            badgeDrawable.setNumber(task.getResult().size());
                        } else {

                        }
                    }
                });
    }

}