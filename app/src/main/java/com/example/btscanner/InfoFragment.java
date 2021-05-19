package com.example.btscanner;

import android.app.ActionBar;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class InfoFragment extends Fragment {
    TextView info,DeviceName,Mac;
    private ImageButton back;
    private View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_info, container, false);


        info = view.findViewById(R.id.info);
        DeviceName = view.findViewById(R.id.DeviceName);
        Mac = view.findViewById(R.id.MacAddress);

        back=view.findViewById(R.id.button_back);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("onClick: ", "BackPressed");
                NavController navController = Navigation.findNavController(view);
                // Navigating to Previous Fragment
                navController.popBackStack();
            }
        } );
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        // Safe Arguments Received
        InfoFragmentArgs args = InfoFragmentArgs.fromBundle(getArguments());
        info.setText(args.getContent());
        args.getName();
        DeviceName.setText(args.getName());

        Mac.setText(args.getMacAddress());
    }
}