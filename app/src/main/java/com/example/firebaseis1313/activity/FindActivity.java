package com.example.firebaseis1313.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.firebaseis1313.R;

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

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        switch (type){
            case "1":
                txtHeader.setText("Chọn Khoảng Giá");
                txtMin.setText(currencyVN.format(0));
                txtMax.setText(currencyVN.format(6000000));
                seekBar.setMax(6000000);
                txtBottom.setText("Giá nhỏ hơn:");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int value = seekBar.getProgress();
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
                int max = 50;
                txtHeader.setText("Diện tích");
                txtMin.setText("0 m2");
                txtMax.setText(max + " m2");
                seekBar.setMax(max);
                txtBottom.setText("Diện tích nhỏ hơn:");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int value = seekBar.getProgress();
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
                int max1 = 30;
                txtHeader.setText("Khoảng cách");
                txtMin.setText("0 km");
                txtMax.setText(max1 +" km");
                seekBar.setMax(max1);
                txtBottom.setText("Cách trường:");
                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int value = seekBar.getProgress();
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



    }
}