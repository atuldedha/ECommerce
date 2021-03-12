package com.example.e_commerce.my_rewards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyRewardsFragment extends Fragment {

    private RecyclerView myRewardsRecyclerView;

    public MyRewardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_rewards, container, false);

        myRewardsRecyclerView=view.findViewById(R.id.myRewardsRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        myRewardsRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardsModel> rewardsModelList=new ArrayList<>();

        rewardsModelList.add(new RewardsModel("Cashback","25 Jul,2020","Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
        rewardsModelList.add(new RewardsModel("Dicount","25 Jul,2020","Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
        rewardsModelList.add(new RewardsModel("Buy 1 get 1 free","25 Jul,2020","Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
        rewardsModelList.add(new RewardsModel("Cashback","25 Jul,2020","Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
        rewardsModelList.add(new RewardsModel("Discount","25 Jul,2020","Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
        rewardsModelList.add(new RewardsModel("Cashback","25 Jul,2020","Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
        rewardsModelList.add(new RewardsModel("Buy 1 get 1 free","25 Jul,2020","Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
        rewardsModelList.add(new RewardsModel("Cashback","25 Jul,2020","Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));

        RewardsAdapter rewardsAdapter=new RewardsAdapter(rewardsModelList,false);
        myRewardsRecyclerView.setAdapter(rewardsAdapter);

        rewardsAdapter.notifyDataSetChanged();

        return view;
    }
}
