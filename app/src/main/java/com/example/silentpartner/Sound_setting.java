package com.example.silentpartner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Sound_setting extends AppCompatActivity {


    TextView textview_volemuLevel;
    Switch switch_volumeLevel;
    private AudioManager audioManager;
    Button button_settingVolume;
    int sound_notificate_Level_save;
    int sound_music_Level_save;
    Handler handler_volumeLevel;
    Handler handler_switch_on_off;
    int save_ringerMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_setting);


        textview_volemuLevel = findViewById(R.id.sound_setting_textview_volumeLevel);
        switch_volumeLevel = findViewById(R.id.sound_setting_switch_on_off);
        button_settingVolume = findViewById(R.id.sound_setting_button_settingVolume);

        /*
    }};
handler_switch_on_off = new Handler(){
    @Override
    public void handleMessage(@NonNull Message msg) {
        switch_volumeLevel.set;
    }
};*/

        // Получаем доступ к менеджеру звуков
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        textview_volemuLevel.setText("Звук мультимедиа: " + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + "\nСистемный звук: " + audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));


        //Сохраняем данные звука
        sound_notificate_Level_save = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        sound_music_Level_save = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        save_ringerMode = audioManager.getRingerMode();

        //Включил ли звук на устройсвте изначально
        /*Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }

                if ((audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) > 0) || (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) > 0)) {
                    switch_volumeLevel.setEnabled(true);
                } else {
                    switch_volumeLevel.setEnabled(false);
                }
            }
        });
        t.start();*/


//Смена уровня звука - Handler
        //0 - Не меняется TextView
        // 1 - Смена показателей звука
        // 2 - Вибрация включена
        // 3 - Беззвучный режим включен
        // 4 - Обычный режим
        final Handler handler_volumeLevel = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {

                if (msg.what == 1) {
                    textview_volemuLevel.setText("Звук мультимедиа: " + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + "\nСистемный звук: " + audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
                }
                if (msg.what == 2) {
                    Toast.makeText(Sound_setting.this, "Включён режим вибрации", Toast.LENGTH_SHORT).show();
                }

                if (msg.what == 3) {
                    Toast.makeText(Sound_setting.this, "Включён беззвучный режим", Toast.LENGTH_SHORT).show();
                }
                if (msg.what == 4) {
                    Toast.makeText(Sound_setting.this, "Включён обычный режим", Toast.LENGTH_SHORT).show();
                }
            }
        };


        Thread thread_volumeLevel = new Thread(new Runnable() {
            @Override
            public void run() {


                while (1 == 1) {
                    if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != sound_music_Level_save) {
                        sound_music_Level_save = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                        handler_volumeLevel.sendEmptyMessageDelayed(1, 200);
                    } else {
                        handler_volumeLevel.sendEmptyMessageDelayed(0, 200);
                    }
                    if (audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) != sound_notificate_Level_save) {
                        sound_notificate_Level_save = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
                        handler_volumeLevel.sendEmptyMessageDelayed(1, 200);
                    } else {
                        handler_volumeLevel.sendEmptyMessageDelayed(0, 200);
                    }


                    if (save_ringerMode != audioManager.getRingerMode()) {
                        save_ringerMode = audioManager.getRingerMode();

                        if (save_ringerMode == AudioManager.RINGER_MODE_VIBRATE) {
                            handler_volumeLevel.sendEmptyMessageDelayed(2, 200);
                        }


                        if (save_ringerMode == AudioManager.RINGER_MODE_SILENT) {
                            handler_volumeLevel.sendEmptyMessageDelayed(3, 200);
                        }


                        if (save_ringerMode == AudioManager.RINGER_MODE_NORMAL) {
                            handler_volumeLevel.sendEmptyMessageDelayed(4, 200);
                        }


                    }


                }
            }
        }

        );
        thread_volumeLevel.start();


        //Слушатель switch
        switch_volumeLevel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, sound_notificate_Level_save, 1);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, sound_music_Level_save, 1);
                    textview_volemuLevel.setText("Звук мультимедиа: " + audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) + "\nСистемный звук: " + audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
                } else {
                    sound_notificate_Level_save = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
                    sound_music_Level_save = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 1);
                    textview_volemuLevel.setText("Звук мультимедиа: 0" + "\nСистемный звук: 0");

                }

            }
        });


    }
}


