package com.example.btscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 按鈕物件
        Button button = (Button)findViewById( R.id.button );

        // Button 的 OnClick Trigger
        button.setOnClickListener(  new View.OnClickListener(){
            public void onClick(View v) {

                // 指定要呼叫的 Activity Class
                Intent newAct = new Intent(MainActivity.this, MainActivity2.class);

                // 呼叫新的 Activity Class
                startActivity( newAct );

                // 結束原先的 Activity Class
                MainActivity.this.finish();
            }
        });
    }
}