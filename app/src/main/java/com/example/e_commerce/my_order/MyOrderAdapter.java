package com.example.e_commerce.my_order;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.cart_fragment.CartAdapter;
import com.example.e_commerce.order_details.OrderDetailsActivity;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {

        int resource=myOrderItemModelList.get(position).getOrderedProductImage();
        int rating=myOrderItemModelList.get(position).getRating();
        String title=myOrderItemModelList.get(position).getOrderedProductTitle();
        String deliveryDate=myOrderItemModelList.get(position).getOrderDeliveryStatus();

        holder.setOrderDetails(resource,title,deliveryDate,rating);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView orderDeliveryIndicaterTextView;
        private TextView orderedproductTitle;
        private ImageView orderedProductImageView;
        private ImageView orderIndicater;
        private LinearLayout yourRatingLinearLayout;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            orderDeliveryIndicaterTextView=itemView.findViewById(R.id.orderDeliveryTextView);
            orderedproductTitle=itemView.findViewById(R.id.orderedProductTitle);
            orderedProductImageView=itemView.findViewById(R.id.orderedProductImageView);
            orderIndicater =itemView.findViewById(R.id.orderIndicater);
            yourRatingLinearLayout=itemView.findViewById(R.id.yourRatingLinearLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent orderDetailsIntent= new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);

                }
            });
        }

        private void setOrderDetails(int resource,String title,String deliveryDate,int rating){

            orderedProductImageView.setImageResource(resource);
            orderedproductTitle.setText(title);
            if(deliveryDate.equals("cancelled")){
                orderIndicater.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FFED4E4E")));
            }else{
                orderIndicater.setImageTintList(ColorStateList.valueOf(Color.parseColor("#94ED2C")));
            }

            orderDeliveryIndicaterTextView.setText(deliveryDate);

            //////rating layout
            setRating(rating);
            for(int i=0; i<yourRatingLinearLayout.getChildCount(); i++){

                final int startPosition=i;
                yourRatingLinearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(startPosition);
                    }
                });

            }

            //////rating layout

        }

        public void setRating(int starPosition){

            for(int i=0;i<yourRatingLinearLayout.getChildCount();i++){

                ImageView starImageView=(ImageView)yourRatingLinearLayout.getChildAt(i);
                starImageView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#CDC8C8")));
                if(i<=starPosition){
                    starImageView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F4E87E")));
                }

            }

        }
    }


}
