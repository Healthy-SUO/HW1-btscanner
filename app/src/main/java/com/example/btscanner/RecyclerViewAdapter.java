package com.example.btscanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    interface OnButtonClickHandler {
        void onButtonClick(ScannedData value);
    }

    private List<ScannedData> arrayList = new ArrayList<>();
    private Activity activity;
    private OnButtonClickHandler mButtonClickHandler;


    public RecyclerViewAdapter(ArrayList<ScannedData> arrayList) {
        this.arrayList = arrayList;
    }

    public void clearDevice(){
        this.arrayList.clear();
        notifyDataSetChanged();
    }

    public void addDevice(List<ScannedData> arrayList){
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName,tvAddress,tvInfo,tvRssi;
        Button info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textView_DeviceName);
            tvAddress = itemView.findViewById(R.id.textView_Address);
            tvRssi = itemView.findViewById(R.id.textView_Rssi);
            info = itemView.findViewById(R.id.button_info);
        }
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_searchFragment4_to_infoFragment2);


            }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scanned_item,parent,false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScannedData value = arrayList.get(position);
        if (arrayList.get(position).getDeviceName() == null) {
            holder.tvName.setText("Unknown Divice");
        } else {
            holder.tvName.setText(arrayList.get(position).getDeviceName());
        }

        holder.tvAddress.setText("裝置位址：" + arrayList.get(position).getAddress());
        holder.tvRssi.setText("訊號強度：" + arrayList.get(position).getRssi());
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);

                // Implementation of Safe Arguments
                SearchFragmentDirections.ActionSearchFragment4ToInfoFragment2 action =
                        SearchFragmentDirections.actionSearchFragment4ToInfoFragment2("裝置名稱：" + value.getDeviceName()
                                , "Mac: " + value.getAddress()
                                , value.getDeviceByteInfo());

                // Navigating to specific fragment
                navController.navigate(action);

            }
        });
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}
