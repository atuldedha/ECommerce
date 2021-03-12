package com.example.e_commerce.gridview_product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.R;
import com.example.e_commerce.horizontal_scroll.HorizontalScrollModel;
import com.example.e_commerce.product_details.ProductDetailsActivity;

import java.util.List;

public class GridViewProductLAyoutAdapter extends BaseAdapter {
    List<HorizontalScrollModel> gridViewProductModelList;

    public GridViewProductLAyoutAdapter(List<HorizontalScrollModel> gridViewProductModelList) {
        this.gridViewProductModelList = gridViewProductModelList;
    }

    @Override
    public int getCount() {


            return gridViewProductModelList.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final View view;
        if(convertView==null){

            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent getProductDetailsIntent=new Intent(parent.getContext(), ProductDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PRODUCT_ID",gridViewProductModelList.get(position).getProductId());
                    getProductDetailsIntent.putExtras(bundle);
                    parent.getContext().startActivity(getProductDetailsIntent);
                }
            });

            ImageView productImage=view.findViewById(R.id.horizontalScrollProductImageView);
            TextView productName=view.findViewById(R.id.horizontalScrollProductNameTextView);
            TextView productDescription=view.findViewById(R.id.horizontalProductProcessorTextView);
            TextView productPrice=view.findViewById(R.id.horizontalScrollProductPriceTextView);

           // productImage.setImageResource(gridViewProductModelList.get(position).getProductImage());
            Glide.with(view.getContext()).load(gridViewProductModelList.get(position).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo)).into(productImage);
            productName.setText(gridViewProductModelList.get(position).getProductName());
            productDescription.setText(gridViewProductModelList.get(position).getProductProcessor());
            productPrice.setText(gridViewProductModelList.get(position).getProductPrice());


        }else{

            view=convertView;

        }

        return view;
    }
}
