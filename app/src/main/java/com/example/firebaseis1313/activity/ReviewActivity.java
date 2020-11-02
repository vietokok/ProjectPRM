package com.example.firebaseis1313.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.Review;
import com.example.firebaseis1313.entity.Room;
import com.example.firebaseis1313.entity.User;
import com.example.firebaseis1313.fragment.ListRoomFragment;
import com.example.firebaseis1313.helper.ReviewAdapter;
import com.example.firebaseis1313.helper.RoomViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewActivity extends AppCompatActivity {
    private ListView listViewComment;
    private ArrayList<Review> listReview;

    private FirebaseFirestore db;

    private RatingBar ratingBar_user;
    private TextView tvRoomTitle;
    private CircleImageView hostAvatar;
    private TextView userName;
    private RatingBar ratingBar_room;
    private EditText etComment;
    private CircleImageView userAvatar;

    private Button delete;
    private Button add;



    //add adapter
    private ReviewAdapter reviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        db=FirebaseFirestore.getInstance();
        listViewComment=findViewById(R.id.list_review);
        ratingBar_room=findViewById(R.id.ratingBar_room);
        tvRoomTitle=findViewById(R.id.tvRoomTitle);
        hostAvatar=findViewById(R.id.imgHostAvatar);
        userName=findViewById(R.id.tvUserName);
        ratingBar_user=findViewById(R.id.ratingBar_user);
        etComment=findViewById(R.id.etComment);
        userAvatar=findViewById(R.id.userAvatar);

        delete=findViewById(R.id.btn_delete);
        add=findViewById(R.id.btn_comment);

        


        listReview =new ArrayList<>();
        reviewAdapter=new ReviewAdapter(ReviewActivity.this,listReview);
        listViewComment.setAdapter(reviewAdapter);
        setListReivew("GlunKVfS4kej6zXLjY2X");

    }

    void setListReivew(String room_id){
        db.collection("Review").whereEqualTo("room_id",room_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                final Map<String, Object> list = document.getData();
                                final Review review=new Review();
                                review.setContent(list.get("content").toString());
                                review.setRate(Float.parseFloat(list.get("rate").toString()));
                                String user_id=list.get("user_id").toString();
                                SharedPreferences sharedPreferences =getSharedPreferences("isLogin", MODE_PRIVATE);
                                String result = sharedPreferences.getString("userId", null);
                                if(user_id.equals(result)==false){
                                    db.collection("User").document(list.get("user_id").toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                                            if(task.isSuccessful()){
                                                User user =new User();
                                                user.setDisplayName(task.getResult().get("displayName").toString());
                                                user.setPhotoUrl(task.getResult().get("photoUrl").toString());
                                                review.setUser(user);
                                                listReview.add(review);
                                                reviewAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                                }
                            }
                        } else {

                        }
                    }
                });



    }

}