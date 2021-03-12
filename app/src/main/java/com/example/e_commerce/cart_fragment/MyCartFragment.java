package com.example.e_commerce.cart_fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;
import com.example.e_commerce.delivery.DeliveryActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    public static CartAdapter cartAdapter;
    private Button cartContinueButton;

    private TextView totalCartAmountTextView;

    /////loading dialog

    private Dialog progressDialog;

    /////loading dialog

    public MyCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_cart, container, false);

        totalCartAmountTextView = view.findViewById(R.id.totalCartAmountTextView);

        //////loading dialog

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setCancelable(false);

        progressDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        progressDialog.show();

        //////loading dialog;


        cartRecyclerView=view.findViewById(R.id.myCartRecyclerView);
        cartContinueButton=view.findViewById(R.id.cartContinueButton);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        cartRecyclerView.setLayoutManager(linearLayoutManager);

        if(DBqueries.cartItemModelList.size() == 0){

            DBqueries.cartItemModelList.clear();
            DBqueries.loadCartItem(getContext(), progressDialog, true, new TextView(getContext()), totalCartAmountTextView);

        }else{

            if(DBqueries.cartItemModelList.get(DBqueries.cartItemModelList.size()-1).getType() == CartItemModel.TOTAL_AMOUNT){

                LinearLayout parentLinearLayout = (LinearLayout) totalCartAmountTextView.getParent().getParent();
                parentLinearLayout.setVisibility(View.VISIBLE);
            }

            progressDialog.dismiss();

        }


        cartAdapter=new CartAdapter(DBqueries.cartItemModelList, totalCartAmountTextView, true);
        cartRecyclerView.setAdapter(cartAdapter);

        cartAdapter.notifyDataSetChanged();

        cartContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeliveryActivity.cartItemModelList = new ArrayList<>();

                for (int i=0; i<DBqueries.cartItemModelList.size(); i++){

                    CartItemModel cartItemModel = DBqueries.cartItemModelList.get(i);

                    if(cartItemModel.isInStock()){

                        DeliveryActivity.cartItemModelList.add(cartItemModel);

                    }

                }

                DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));

                progressDialog.show();

                if (DBqueries.myAddressesModelList.size() == 0){

                    DBqueries.loadAddresses(getContext(), progressDialog);

                }else{

                    progressDialog.dismiss();

                    Intent deliveryIntent = new Intent(getContext(), DeliveryActivity.class);
                    startActivity(deliveryIntent);

                }



            }
        });

        return view;
    }
}
