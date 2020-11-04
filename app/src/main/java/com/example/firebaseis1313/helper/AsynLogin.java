package com.example.firebaseis1313.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.activity.LoginActivity;
import com.example.firebaseis1313.fragment.HomeFragment;
import com.example.firebaseis1313.fragment.ListRoomFragment;
import com.example.firebaseis1313.fragment.LoginFragment;
import com.example.firebaseis1313.fragment.MidmanFragment;
import com.example.firebaseis1313.fragment.Profilefragment;
import com.example.firebaseis1313.fragment.SavedFragment;
import com.example.firebaseis1313.fragment.SearchFragment;
import com.example.firebaseis1313.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AsynLogin extends AsyncTask<String, Void, String> {


    ProgressDialog progressDialog;
    Context context;
    TextView textView;
    FirebaseFirestore db;
    MainActivity mainActivity;


    HomeFragment homeFragment;
    SearchFragment searchFragment;
    private ListRoomFragment listRoomFragment;
    private LoginFragment loginFragment;
    private SavedFragment savedFragment;
    private Profilefragment profilefragment;
    private MidmanFragment midmanFragment;



    private ViewPager viewPager;
    private TabLayout tabLayout;

    public AsynLogin(TextView textView, FirebaseFirestore db, MainActivity mainActivity, HomeFragment homeFragment, SearchFragment searchFragment, ListRoomFragment listRoomFragment, LoginFragment loginFragment, SavedFragment savedFragment, Profilefragment profilefragment, MidmanFragment midmanFragment, ViewPager viewPager, TabLayout tabLayout) {

        this.textView = textView;
        this.db = db;
        this.mainActivity = mainActivity;
        this.homeFragment = homeFragment;
        this.searchFragment = searchFragment;
        this.listRoomFragment = listRoomFragment;
        this.loginFragment = loginFragment;
        this.savedFragment = savedFragment;
        this.profilefragment = profilefragment;
        this.midmanFragment = midmanFragment;
        this.viewPager = viewPager;
        this.tabLayout = tabLayout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Hiển thị Dialog khi bắt đầu xử lý.
        db=FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setTitle("HowKteam");
        progressDialog.setMessage("Dang xu ly...");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(final String... strings) {
        final String result="";
        db.collection("User").whereEqualTo("username", strings[0]).whereEqualTo("password", strings[1]).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() == 1) {
                       setTabLayout(1,strings[2]);
                    } else{
                        SharedPreferences.Editor editor = mainActivity.getSharedPreferences("isLogin", Context.MODE_PRIVATE).edit();
                        editor.putBoolean("isLogin", false);
                        editor.putString("userId", "");
                        editor.putString("userAvatar","");
                        editor.putString("userDisplayName","");
                        editor.putString("userName","");
                        editor.putString("userPassword","");
                        setTabLayout(2,strings[2]);
                    }
                } else {
                    setTabLayout(2,strings[2]);
                }
            }

        });
        return null;
    }

    void  setTabLayout(int type,String user_id){
        ViewPageAdapter viewPageApdater =new ViewPageAdapter(mainActivity.getSupportFragmentManager(),0);
        // add to apdater
        viewPageApdater.addFragment(homeFragment,"Home");
        viewPageApdater.addFragment(searchFragment,mainActivity.getString(R.string.search));
        Intent intent = mainActivity.getIntent();
        String id = intent.getStringExtra("loginStatus");
        System.out.println(user_id);

        if(type==1){
            viewPageApdater.addFragment(profilefragment, mainActivity.getString(R.string.account));
            mainActivity.setSavedRoom(user_id);
        }else if(type==2){
            viewPageApdater.addFragment(loginFragment, mainActivity.getString(R.string.account));
        }

        viewPageApdater.addFragment(midmanFragment, mainActivity.getString(R.string.saved));

        // add to tab_layout
        // Đối vs
        viewPager.setAdapter(viewPageApdater);
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.search);
        tabLayout.getTabAt(2).setIcon(R.drawable.user);
        tabLayout.getTabAt(3).setIcon(R.drawable.apartment);
        if(id !=null && id.equals("current")){
            tabLayout.getTabAt(2).select();
        }
    }
    @Override
    protected void onPostExecute(String aString) {
        super.onPostExecute(aString);
        // Hủy dialog đi.
        progressDialog.dismiss();
        // Hiển thị IP lên TextView.
//        textView.setText(aString);
    }
}
