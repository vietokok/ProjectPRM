package com.example.firebaseis1313.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.entity.More;
import com.example.firebaseis1313.helper.MoreAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private CarouselView carouselView;
    private TextView txtAddress;
    private TextView txtArea;
    private TextView txtContact;
    private TextView txtTitle;
    private TextView txtPrice;
    private TextView txtDetail;
    private List<More> moreList;
    private MoreAdapter moreAdapter;
    private FirebaseFirestore db;
    private ArrayList<String> listImageUrl;
    private HashMap<String, String> geo;
    private Button btnMore;
    private Button btnSave;
    private Drawable save;
    private Drawable notSave;
    private ArrayList<String> listSaveRoom;
    private Button btnComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        carouselView = findViewById(R.id.carouselView);
        imageView = findViewById(R.id.googleMapImg);
        txtAddress = findViewById(R.id.txtAddress);
        txtArea = findViewById(R.id.txtArea);
        txtContact = findViewById(R.id.txtContact);
        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        txtDetail = findViewById(R.id.txtDetail);
        btnMore = findViewById(R.id.btnMore);
        btnSave = findViewById(R.id.btnSave);
        btnComment = findViewById(R.id.btnComment);

        Intent rootIntent = getIntent();
        final String room_id = rootIntent.getStringExtra("room_id");

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
                String user_id = sharedPreferences.getString("userId", "");
                if (user_id != "") {
                    Intent intent = new Intent(DetailActivity.this, ReviewActivity.class);
                    intent.putExtra("room_id", room_id);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });


        db = FirebaseFirestore.getInstance();

        save = btnSave.getContext().getResources().getDrawable(R.drawable.ic_baseline_bookmark_24, null);
        notSave = btnSave.getContext().getResources().getDrawable(R.drawable.ic_outline_bookmark_border_24, null);

        SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
        String user_id = sharedPreferences.getString("userId", "");
        if (user_id != "") {
            db.collection("User").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    listSaveRoom = (ArrayList<String>) task.getResult().get("listSaveRoom");
                    for (String room : listSaveRoom) {
                        if (room.equals("5x9wBS8LGwNc31BhOqwJ")) {
                            btnSave.setCompoundDrawablesWithIntrinsicBounds(save, null, null, null);
                        } else {
                            btnSave.setCompoundDrawablesWithIntrinsicBounds(notSave, null, null, null);
                        }
                    }
                }
            });
        } else {
            btnSave.setCompoundDrawablesWithIntrinsicBounds(notSave, null, null, null);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("isLogin", MODE_PRIVATE);
                String user_id = sharedPreferences.getString("userId", "");
                if (user_id != "") {
                    if (btnSave.getCompoundDrawables()[0] == save) {
                        Toast.makeText(DetailActivity.this, "Phòng trọ này đã được lưu", Toast.LENGTH_SHORT).show();
                    } else {
                        listSaveRoom.add("5x9wBS8LGwNc31BhOqwJ");
                        db.collection("User").document(user_id).update("listSaveRoom", listSaveRoom);
                        btnSave.setCompoundDrawablesWithIntrinsicBounds(save, null, null, null);
                        Toast.makeText(DetailActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

//        vnd format
        Locale localeVN = new Locale("vi", "VN");
        final NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);

        db.collection("Motel").document(room_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    float price = Float.parseFloat(task.getResult().get("price").toString());
                    txtPrice.setText(currencyVN.format(price) + " / " + "tháng");
                    txtArea.setText("   " + "Diện tích " + task.getResult().get("area").toString() + "m2");
                    txtTitle.setText(task.getResult().get("title").toString());
                    txtDetail.setText(String.valueOf(task.getResult().get("description")));

                    String home_id = (String) task.getResult().get("home_id");
                    db.collection("Home").document(home_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                String host_id = task.getResult().get("host_id").toString();

                                geo = (HashMap) task.getResult().get("location");
                                txtAddress.setText("   " + task.getResult().get("address").toString());

                                db.collection("Host").document(host_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull final Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            String otherPhone = task.getResult().get("otherPhone").toString() != "" ? (" - " + task.getResult().get("otherPhone").toString()) : "";
                                            String contact = "   " + task.getResult().get("phone").toString() + otherPhone +
                                                    " - " + task.getResult().get("name").toString();
                                            txtContact.setText(contact);

                                            btnMore.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
//                                                  show option for btnMore
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
//                                                                open phone app
                                                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                                                intent.setData(Uri.parse("tel:" + task.getResult().get("phone")));
                                                                startActivity(intent);
                                                            } else if (which == 1) {
//                                                                open message app
                                                                Intent intent = new Intent(Intent.ACTION_SENDTO);
                                                                intent.setData(Uri.parse("smsto:" + task.getResult().get("phone")));
                                                                intent.putExtra("sms_body", "");
                                                                if (intent.resolveActivity(getPackageManager()) != null) {
                                                                    startActivity(intent);
                                                                }
                                                            } else {
//                                                                open google map app
                                                                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + geo.get("lat") + "," + geo.get("long"));
                                                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                                                mapIntent.setPackage("com.google.android.apps.maps");
                                                                startActivity(mapIntent);
                                                            }
                                                        }
                                                    });
                                                    builderSingle.show();
                                                }
                                            });
                                        }
                                    }
                                });


//                                Picasso.get().load("").into(imageView);

//                                open MapsActivity
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(DetailActivity.this, MapsActivity.class);
                                        intent.putExtra("lat", geo.get("lat"));
                                        intent.putExtra("long", geo.get("long"));
                                        intent.putExtra("title", task.getResult().get("address").toString());
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });

//                    load image to carousel
                    String image_id = (String) task.getResult().get("image_id");
                    db.collection("Image").document(image_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                listImageUrl = (ArrayList<String>) task.getResult().get("url");
                                ImageListener imageListener = new ImageListener() {
                                    @Override
                                    public void setImageForPosition(int position, ImageView imageView) {
                                        Picasso.get().load(listImageUrl.get(position)).into(imageView);
                                    }
                                };
                                carouselView.setImageListener(imageListener);
                                carouselView.setPageCount(listImageUrl.size());
                                carouselView.setImageClickListener(new ImageClickListener() {
                                    @Override
                                    public void onClick(int position) {
                                        Intent intent = new Intent(DetailActivity.this, ImageActivity.class);
                                        intent.putExtra("imageIndex", position);
                                        intent.putExtra("images", listImageUrl);
                                        startActivityForResult(intent, 100);
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    //    back to referer page
    public void onButtonBackClick(View view) {
        finish();
    }
}