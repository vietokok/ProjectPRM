package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.firebaseis1313.R;
import com.example.firebaseis1313.fragment.SearchFragment;

import java.text.NumberFormat;
import java.util.Locale;

public class FindActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView txtOnclick;
    private TextView txtHeader;
    private TextView txtMin;
    private TextView txtMax;
    private TextView txtBottom;
    private Button btnApply;
    private Button btnCancel;
    int value = 0;
    Locale localeVN = new Locale("vi", "VN");
    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        seekBar = findViewById(R.id.seekBar2);
        txtOnclick = findViewById(R.id.txtOnclick);
        txtHeader = findViewById(R.id.txtHeader);
        txtMin = findViewById(R.id.txtMin);
        txtMax = findViewById(R.id.txtMax);
        txtBottom = findViewById(R.id.txtBottom);
        btnApply = findViewById(R.id.btnApply);
        btnCancel = findViewById(R.id.btnCancel);
        Intent intent = getIntent();
        final String type = intent.getStringExtra("type");

        //set on click for button "Hủy"
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Display type of search
        switch (type){
            case "1":
                //search for Price
                txtHeader.setText("Chọn Khoảng Giá");
                txtMin.setText(currencyVN.format(0));
                txtMax.setText(currencyVN.format(6000000));
                seekBar.setMax(6000000);
                txtBottom.setText("Giá nhỏ hơn:");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        value = seekBar.getProgress();
                        String formatMoney = currencyVN.format(value);
                        txtOnclick.setText(String.valueOf(formatMoney));
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
            case "2":
                //search for area
                int max = 50;
                txtHeader.setText("Diện tích");
                txtMin.setText("0 m2");
                txtMax.setText(max + " m2");
                seekBar.setMax(max);
                txtBottom.setText("Diện tích nhỏ hơn:");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        value = seekBar.getProgress();
                        txtOnclick.setText(String.valueOf(value)+ " m2");
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
            case "3":
                //search for Distance
                int max1 = 30;
                txtHeader.setText("Khoảng cách");
                txtMin.setText("0 km");
                txtMax.setText(max1 +" km");
                seekBar.setMax(max1);
                txtBottom.setText("Cách trường:");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        value = seekBar.getProgress();
                        txtOnclick.setText(String.valueOf(value)+ " km");
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                break;
        }

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent();
                SearchFragment searchFragment = new SearchFragment();
                myIntent.putExtra("type", type);
                myIntent.putExtra("value", value);
                setResult(searchFragment.RESULT_CODE, myIntent);
                finish();
            }
        });

    }

}