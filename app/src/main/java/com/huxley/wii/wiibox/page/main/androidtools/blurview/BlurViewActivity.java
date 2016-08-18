package com.huxley.wii.wiibox.page.main.androidtools.blurview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.huxley.wii.wiibox.R;

/**
 * Demo
 * @author Qiushui
 */
public class BlurViewActivity extends AppCompatActivity {

    /**
     * basic btn
     */
    private Button mBasicBtn;

    /**
     * weather btn
     */
    private Button mWeatherBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_view);

        mBasicBtn = (Button) findViewById(R.id.basic_blur_btn);
        mWeatherBtn = (Button) findViewById(R.id.weather_blur_btn);

        mBasicBtn.setOnClickListener(v -> startActivity(new Intent(BlurViewActivity.this, BlurredViewBasicActivity.class)));

        mWeatherBtn.setOnClickListener(v -> startActivity(new Intent(BlurViewActivity.this, WeatherActivity.class)));
    }
}
