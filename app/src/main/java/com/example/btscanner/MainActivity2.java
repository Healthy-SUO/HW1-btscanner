package com.example.btscanner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        int hasGone = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
//        if (hasGone != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(MainActivity2.this,
//                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                    1);
//            ActivityCompat.requestPermissions(MainActivity2.this,
//                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                    1);
//        }


    }


}