package com.example.e_commerce.my_account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.e_commerce.R;
import com.example.e_commerce.delivery.DeliveryActivity;
import com.example.e_commerce.my_addresses.MyAddressesActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {

    public static final int MANAGE_ADDRESS=1;
    private Button viewAllAddressbutton;

    public MyAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_account, container, false);

        viewAllAddressbutton=view.findViewById(R.id.viewAllAddressButton);
        viewAllAddressbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressesIntent=new Intent(getContext(), MyAddressesActivity.class);
                addressesIntent.putExtra("MODE",MANAGE_ADDRESS);
                startActivity(addressesIntent);
            }
        });

        return view;
    }
}
