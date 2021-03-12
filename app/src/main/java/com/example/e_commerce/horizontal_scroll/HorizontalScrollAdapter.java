package com.example.e_commerce.horizontal_scroll;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.R;
import com.example.e_commerce.product_details.ProductDetailsActivity;

import java.util.List;

public class HorizontalScrollAdapter extends RecyclerView.Adapter<HorizontalScrollAdapter.ViewHolder> {

    List<HorizontalScrollModel> horizontalScrollModelList;

    public HorizontalScrollAdapter(List<HorizontalScrollModel> horizontalScrollModelList) {
        this.horizontalScrollModelList = horizontalScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalScrollAdapter.ViewHolder holder, int position) {

        String resource = horizontalScrollModelList.get(position).getProductImage();
        String name = horizontalScrollModelList.get(position).getProductName();
        String processor = horizontalScrollModelList.get(position).getProductProcessor();
        String price = horizontalScrollModelList.get(position).getProductPrice();
        String productId = horizontalScrollModelList.get(position).getProductId();

        holder.setData(resource,name,processor,price,productId);


    }

    @Override
    public int getItemCount() {
        if(horizontalScrollModelList.size()>8){
           return 8;
        }else{
            return horizontalScrollModelList.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView;
        TextView productNameTextView;
        TextView productProcessorTextView;
        TextView productPriceTextView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImageView=itemView.findViewById(R.id.horizontalScrollProductImageView);
            productNameTextView=itemView.findViewById(R.id.horizontalScrollProductNameTextView);
            productProcessorTextView=itemView.findViewById(R.id.horizontalProductProcessorTextView);
            productPriceTextView=itemView.findViewById(R.id.horizontalScrollProductPriceTextView);

        }

        public void setData(String resource, String title, String processor, String price, final String productId){

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_photo)).into(productImageView);
            productNameTextView.setText(title);
            productProcessorTextView.setText(processor);
            productPriceTextView.setText(price);

            if(!title.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("PRODUCT_ID",productId);
                        productDetailIntent.putExtras(bundle);
                        itemView.getContext().startActivity(productDetailIntent);
                    }
                });
            }

        }

    }
}
