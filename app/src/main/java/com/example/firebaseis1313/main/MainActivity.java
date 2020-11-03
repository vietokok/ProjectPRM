package com.example.firebaseis1313.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Room;
import com.example.firebaseis1313.fragment.LoginFragment;
import com.example.firebaseis1313.fragment.HomeFragment;
import com.example.firebaseis1313.fragment.ListRoomFragment;
import com.example.firebaseis1313.fragment.MidmanFragment;
import com.example.firebaseis1313.fragment.Profilefragment;
import com.example.firebaseis1313.fragment.SavedFragment;
import com.example.firebaseis1313.fragment.SearchFragment;
import com.example.firebaseis1313.helper.OnFragmentInteractionListener;
import com.example.firebaseis1313.helper.ViewPageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    //Fragement
    private ListRoomFragment listRoomFragment;
    private SearchFragment searchFragment;
    private LoginFragment loginFragment;
    private SavedFragment savedFragment;
    private HomeFragment homeFragment;
    private Profilefragment profilefragment;
    private MidmanFragment midmanFragment;

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
        System.out.println("Main runnnnnnnnnnnnnnnn");
        viewPager=findViewById(R.id.view_page);
        tabLayout=findViewById(R.id.tab_layout);

        // Khai báo Activity --------------
        listRoomFragment =new ListRoomFragment();
        searchFragment =new SearchFragment();
        loginFragment =new LoginFragment();
        savedFragment =new SavedFragment();
        homeFragment=new HomeFragment();
        profilefragment=new Profilefragment();
        midmanFragment=new MidmanFragment();

        //---------------------
        tabLayout.setupWithViewPager(viewPager);
        ViewPageAdapter viewPageApdater =new ViewPageAdapter(getSupportFragmentManager(),0);
        // add to apdater
        viewPageApdater.addFragment(homeFragment,"Home");
        viewPageApdater.addFragment(searchFragment,getString(R.string.search));
        if(isLogin()){
            SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
            String result = sharedPreferences.getString("userId", null);
            if(result !=null){
                setSavedRoom(result);
            }
            viewPageApdater.addFragment(profilefragment, getString(R.string.account));
        }else {
            viewPageApdater.addFragment(loginFragment, getString(R.string.account));
        }
            viewPageApdater.addFragment(midmanFragment, getString(R.string.saved));

        // add to tab_layout
        // Đối vs
        viewPager.setAdapter(viewPageApdater);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
        tabLayout.getTabAt(2).setIcon(R.drawable.user);
        tabLayout.getTabAt(3).setIcon(R.drawable.apartment);
        Intent intent = getIntent();
        String id = intent.getStringExtra("loginStatus");
        if(id !=null && id.equals("current")){
            tabLayout.getTabAt(2).select();
        }
    }


    @Override
    public void setSavedRoom(String userId) {
        db.collection("User").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<String> list_room_Id=(ArrayList<String>)  task.getResult().get("listSaveRoom");
                    BadgeDrawable badgeDrawable = tabLayout.getTabAt(3).getOrCreateBadge();
                    badgeDrawable.setVisible(true);
                    badgeDrawable.setNumber(list_room_Id.size());
                }
            }
        });
    }

    @Override
    public boolean isLogin() {
        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
        boolean result = sharedPreferences.getBoolean("isLogin", false);
        return result;
    }



}






