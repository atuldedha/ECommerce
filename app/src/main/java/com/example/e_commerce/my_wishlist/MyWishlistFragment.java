package com.example.e_commerce.my_wishlist;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishlistFragment extends Fragment {

    private RecyclerView myWishlistRecyclerView;

    public static WishlistAdapter wishlistAdapter;

    /////loading dialog

    private Dialog progressDialog;

    /////loading dialog

    public MyWishlistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_wishlist, container, false);

        myWishlistRecyclerView=view.findViewById(R.id.myWishlistRecyclerView);

        //////loading dialog

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setCancelable(false);

        progressDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        progressDialog.show();

        //////loading dialog;

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        myWishlistRecyclerView.setLayoutManager(linearLayoutManager);

        if(DBqueries.wishListModelList.size() == 0){

            DBqueries.wishListModelList.clear();
            DBqueries.loadWishlist(getContext(), progressDialog, true);

        }else{

            progressDialog.dismiss();

        }

        wishlistAdapter=new WishlistAdapter(DBqueries.wishListModelList,true);
        myWishlistRecyclerView.setAdapter(wishlistAdapter);

        wishlistAdapter.notifyDataSetChanged();

        return view;
    }
}
