package com.example.e_commerce.my_order;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e_commerce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment {

    private RecyclerView myOrderRecyclerView;

    public MyOrdersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_orders, container, false);

        myOrderRecyclerView=view.findViewById(R.id.myOrderRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        myOrderRecyclerView.setLayoutManager(linearLayoutManager);

        List<MyOrderItemModel> myOrderItemModelList=new ArrayList<>();

        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone,2,"Nokia 1130","Delivered on Fri,17Jul,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone,1,"Nokia 1130","cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone,3,"Nokia 1130","Delivered on Fri,17Jul,2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.phone,4,"Nokia 1130","Delivered on Fri,17Jul,2020"));

        MyOrderAdapter myOrderAdapter=new MyOrderAdapter(myOrderItemModelList);

        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }
}
