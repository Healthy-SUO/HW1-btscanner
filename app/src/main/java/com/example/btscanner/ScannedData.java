package com.example.btscanner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScannedData {

    /**獲取Scanned到的信息*/
    private String deviceName;
    private String rssi;
    private String deviceByteInfo;
    private String address;

    public ScannedData(String deviceName, String rssi, String deviceByteInfo, String address) {
        this.deviceName = deviceName;
        this.rssi = rssi;
        this.deviceByteInfo = deviceByteInfo;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getRssi() {
        return rssi;
    }

    public String getDeviceByteInfo() {
        return deviceByteInfo;
    }

    public String getDeviceName() {
        if(deviceName == null){
            return "Unkown Divice";
        }
        else  {  return deviceName;}
    }

    /**濾除重複的address*/
    @Override
    public boolean equals(@Nullable Object obj) {
        ScannedData p = (ScannedData)obj;

        return this.address.equals(p.address);
    }

    @NonNull
    @Override
    public String toString() {
        return this.address;
    }
}
