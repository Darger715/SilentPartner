package com.example.silentpartner;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

import static android.os.Build.ID;

public class Bluetooth extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    TextView textview_in_bluetooth_connect;
    CheckBox autoreload_checkbox;
    Handler handler_autoreload;
    RelativeLayout activity_bluetooth_screen;


    //BluetoothDevice pairedDevices;
    Set<BluetoothDevice> pairedDevices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);


        final String[] device_Name = {null};
        final String[] device_MACAddress = {null};


        findViewById(R.id.button_check_bluettooth_connect).setOnClickListener(this);
        findViewById(R.id.reload_bluetooth_connect).setOnClickListener(this);
        textview_in_bluetooth_connect = findViewById(R.id.textview_in_bluetooth_connect);
        activity_bluetooth_screen = findViewById(R.id.activity_bluetooth_screen);


//Определение Bluetooth-Адаптер
        final BluetoothAdapter bluetoothAdapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter();


//ON - OFF Bluetooth
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        findViewById(R.id.reload_bluetooth_connect).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pairedDevices.size() > 0) {

                            pairedDevices = bluetoothAdapter.getBondedDevices();


// Имя и MAC-адрес каждого подключенного устройства
                            for (BluetoothDevice device : pairedDevices) {
                                device_Name[0] = device.getName(); // Name
                                device_MACAddress[0] = device.getAddress(); // MAC address
                                textview_in_bluetooth_connect.setText("Подключенные устройства: \n" + device_Name[0]);
                            }
                        } else {
                            device_Name[0] = null; // Name
                            device_MACAddress[0] = null; // MAC address
                            textview_in_bluetooth_connect.setText("Нет подключенных устройств");

                        }
                    }
                }
        );


//autoreload_checkbox ON

        if (autoreload_checkbox.isEnabled()) {









            final Thread thread_autoreload = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }

                    if (!bluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        if (pairedDevices.size() > 0) {
                            pairedDevices = bluetoothAdapter.getBondedDevices();


// Имя и MAC-адрес каждого подключенного устройства
                            for (BluetoothDevice device : pairedDevices) {
                                device_Name[0] = device.getName(); // Name
                                device_MACAddress[0] = device.getAddress(); // MAC address
                                handler_autoreload.sendEmptyMessage(1);
                            }
                        } else {
                            device_Name[0] = null; // Name
                            device_MACAddress[0] = null; // MAC address
                            handler_autoreload.sendEmptyMessage(0);
                        }
                    } else {
                        handler_autoreload.sendEmptyMessage(2);
                    }
                }
            });

            thread_autoreload.start();


            final Handler handler_autoreload = new Handler() {
                @SuppressLint("HandlerLeak")
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if (msg.what == 0) {
                        textview_in_bluetooth_connect.setText("Нет подключенных устройств");
                    }
                    if (msg.what == 1) {
                        textview_in_bluetooth_connect.setText("Подключенные устройства: \n" + device_Name[0]);
                    }
                    if (msg.what == 2) {
                        textview_in_bluetooth_connect.setText("Bluetooth выключен");
                    }
                }
            };

        }


    }

    @Override
    public void onClick(View v) {
    }

}




class Checkbox_autoreload_ON{
    int ID;
    String name;

    public Checkbox_autoreload_ON(String name, int ID){
        this.name = name;
        this.ID = ID;
    }

    //void setID(int ID){
    //    this.ID =
    //}


}