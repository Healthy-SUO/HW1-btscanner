package com.example.btscanner;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

public class SearchFragment extends Fragment implements RecyclerViewAdapter.OnButtonClickHandler{

    RecyclerView recyclerView;
    private boolean isScanning = false;
    ArrayList<ScannedData> findDevice = new ArrayList<>();
    RecyclerViewAdapter mAdapter;
    BluetoothLeScanner mBluetoothLeScanner;
    private View view;
    private Button btScan;
    private RecyclerView scanlist;

    final String permissions[] = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        // Initialize View
        btScan = view.findViewById(R.id.button_Scan);
        scanlist = view.findViewById(R.id.recyclerView_ScannedList);

        // Initialize RecyclerView
        mAdapter = new RecyclerViewAdapter(findDevice);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        scanlist.setLayoutManager(layoutManager);
        scanlist.setAdapter(mAdapter);

        return view;


    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        BluetoothAdapter  mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        /**權限相關認證*/
        /**初始藍牙掃描及掃描開關之相關功能*/
        bluetoothScan(mBluetoothAdapter);

    }

    private void bluetoothScan(BluetoothAdapter  mBluetoothAdapter) {

        boolean isGranted = isGranted(getActivity(), permissions);

        if (!isGranted) {
            // Access Permissions Dynamically
            ActivityCompat.requestPermissions(getActivity(), permissions, 1);
        }
        /**啟用藍牙適配器*/

        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

        // If Bluetooth not open or not enabled, request bluetooth action
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBluetoothIntent);
        }
        if (mBluetoothAdapter != null) {
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
            Log.e("onViewCreated: ", "Bluetooth Adapter Not NULL");
        }

        /**製作停止/開始掃描的按鈕*/
        btScan = (Button) requireActivity().findViewById(R.id.button_Scan);
        btScan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(getActivity(), "Please enable BlueTooth", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (!isScanning) {
                        //mBluetoothAdapter.startLeScan(mLeScanCallback);
                        if(mBluetoothAdapter.isEnabled()) {
                            isScanning = true;
                            btScan.setText("關閉掃描");
                            findDevice.clear(); // clear items
                            mBluetoothLeScanner.startScan(leScanCallback);
                            Toast.makeText(getActivity(), "Discovery started", Toast.LENGTH_SHORT).show();
                            mAdapter = new RecyclerViewAdapter(findDevice);
                            recyclerView.setAdapter(mAdapter);
                        }
                    }
                    else{
                        /**開啟掃描*/
                        isScanning = false;
                        btScan.setText("開啟掃描");
                        findDevice.clear();
                        mBluetoothLeScanner.stopScan(leScanCallback);
                        mAdapter.clearDevice();
                    }
                }
            }

        });


        /**設置Recyclerview列表*/
        recyclerView = (RecyclerView) requireActivity().findViewById(R.id.recyclerView_ScannedList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // 設置格線
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));



    }

    @Override
    public void onPause() {
        super.onPause();
        btScan = (Button)requireActivity().findViewById(R.id.button_Scan);
        isScanning = false;
        btScan.setText("開啟掃描");
        mBluetoothLeScanner.stopScan(leScanCallback);
    }

    /**顯示掃描到物件*/
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            ScanRecord mScanRecord = result.getScanRecord();
            String address = device.getAddress();
            byte[] content = mScanRecord.getBytes();
            int mRssi = result.getRssi();

            findDevice.add(new ScannedData(device.getName()
                    , String.valueOf(mRssi)
                    , byteArrayToHexStr(content)
                    , device.getAddress()));
            ArrayList newList = getSingle(findDevice);
            mAdapter = new RecyclerViewAdapter(newList);
            recyclerView.setAdapter(mAdapter);
        }
    };

    @Override
    public void onButtonClick(ScannedData scannedData) {
        onPause();


    }

    /**濾除重複的藍牙裝置(以Address判定)*/
    private ArrayList getSingle(ArrayList list) {
        ArrayList tempList = new ArrayList<>();
        try {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                if (!tempList.contains(obj)) {
                    tempList.add(obj);
                }
                else {
                    for (int i=0;i<tempList.size();i++)
                    {
                        if (tempList.get(i)==obj){
                            tempList.remove(i);
                            tempList.add(obj);
                        }

                    }

                }
            }
            return tempList;
        } catch (ConcurrentModificationException e) {
            return tempList;
        }
    }

    /**
     * 以Address篩選陣列->抓出該值在陣列的哪處
     */
    private int getIndex(ArrayList temp, Object obj) {
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).toString().contains(obj.toString())) {
                return i;
            }
        }
        return -1;
    }
    /**
     * MAC地址-Byte轉16進字串
     */
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String byteArrayToHexStr(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public boolean isGranted(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }



}