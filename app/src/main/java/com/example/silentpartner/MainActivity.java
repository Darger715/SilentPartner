package com.example.silentpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.button_bluetooth_connect).setOnClickListener(this);
        findViewById(R.id.button_vibration_setting).setOnClickListener(this);
        findViewById(R.id.button_main_setting).setOnClickListener(this);
        findViewById(R.id.sound_setting).setOnClickListener(this);
        findViewById(R.id.main_on_off).setOnClickListener(this);



    }

    //RelativeLayout main_screen = findViewById(R.id.main_screen);
    //boolean sound = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_bluetooth_connect: //bluetooth_connect
                Intent button_bluetooth_connect_intent = new Intent(MainActivity.this, Bluetooth.class);
                startActivity(button_bluetooth_connect_intent);
                break;
            case R.id.button_vibration_setting: //vibration_setting
                Intent button_vibration_setting_intent = new Intent(MainActivity.this, Vibration_setting.class);
                startActivity(button_vibration_setting_intent);
                break;
            case R.id.button_main_setting: //sound_setting
                Intent button_main_setting_intent = new Intent(MainActivity.this, Main_setting.class);
                startActivity(button_main_setting_intent);

                break;
            case R.id.sound_setting: //sound_on_off
                Intent button_sound_setting_intent = new Intent(MainActivity.this, Sound_setting.class);
                startActivity(button_sound_setting_intent);

                break;
            case R.id.main_on_off: //main_on_off
        break;
        }
    }
};