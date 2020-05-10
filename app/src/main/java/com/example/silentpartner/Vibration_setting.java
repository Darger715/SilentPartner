package com.example.silentpartner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Vibration_setting extends AppCompatActivity {

    Switch switch_setting_vibration;
    AudioManager audioManager;
    Handler handler_setting_vibration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibration_setting);

        switch_setting_vibration = findViewById(R.id.vibration_setting_switch_on_off_vibration);

        // Получаем доступ к менеджеру звуков
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        //Проверка на включённую вибрацию
        // 1 - Vibration ON
        // 2 - Vibration OFF
        // 3 - None
        final Handler handler_setting_vibration = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {

                if (msg.what == 1) { Toast.makeText(Vibration_setting.this, "Включён режим вибрации", Toast.LENGTH_SHORT).show(); }
                if (msg.what == 2) { Toast.makeText(Vibration_setting.this, "Включён беззвучный режим", Toast.LENGTH_SHORT).show(); }
                if (msg.what == 3) { Toast.makeText(Vibration_setting.this, "Включён обычный режим", Toast.LENGTH_SHORT).show(); } }};


        Thread thread_setting_vibration = new Thread(new Runnable() {
            @Override
            public void run() {


                while (1 == 1) {
                    if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
                        switch_setting_vibration.setEnabled(true);
                        handler_setting_vibration.sendEmptyMessage(1); }
                    if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                        switch_setting_vibration.setEnabled(false);
                        handler_setting_vibration.sendEmptyMessage(2); }
                    if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                        switch_setting_vibration.setSaveEnabled(true);
                        handler_setting_vibration.sendEmptyMessage(3); } } }});
        thread_setting_vibration.start();


        //Слушатель switch
        switch_setting_vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE); } else { audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT); } }});


    }
}
