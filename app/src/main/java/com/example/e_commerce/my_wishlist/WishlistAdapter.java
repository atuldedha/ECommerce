package com.example.e_commerce.my_wishlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;
import com.example.e_commerce.product_details.ProductDetailsActivity;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    List<WishListModel> wishListModelList;
    private boolean wishlist;
    private int lastPosition = -1;

    public WishlistAdapter(List<WishListModel> wishListModelList,boolean wishlist) {
        this.wishListModelList = wishListModelList;
        this.wishlist=wishlist;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, int position) {

        String productID = wishListModelList.get(position).getProductID();
        String resource=wishListModelList.get(position).getProductImage();
        String title=wishListModelList.get(position).getProductTitle();
        long freeCoupon=wishListModelList.get(position).getFreeCoupons();
        String averageRatingRate=wishListModelList.get(position).getRating();
        long totalRatingRate=wishListModelList.get(position).getTotalRatings();
        String price=wishListModelList.get(position).getProductPrice();
        String cutPrice=wishListModelList.get(position).getProductCuttedPrice();
        boolean deliveryMethod=wishListModelList.get(position).isCOD();

        holder.setWishlist(productID,resource,title, freeCoupon, averageRatingRate,
                totalRatingRate, price, cutPrice, deliveryMethod,position);

        if(lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupons;
        private ImageView couponIcon;
        private TextView averageRatings;
        private TextView totalRatings;
        private TextView productPrice;
        private TextView productCuttedPrice;
        private TextView paymentMethod;
        private View wishlistDivider;
        private ImageButton deleteImageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage=itemView.findViewById(R.id.wishlistProductImageView);
            productTitle=itemView.findViewById(R.id.wishlistProductTitleTextView);
            freeCoupons=itemView.findViewById(R.id.wishlistCouponTextView);
            couponIcon=itemView.findViewById(R.id.wishlistCouponIconImageView);
            averageRatings=itemView.findViewById(R.id.productRatingMiniTextView);
            totalRatings=itemView.findViewById(R.id.wishlistTotalRatingTextView);
            productPrice=itemView.findViewById(R.id.wishlistProductPriceTextView);
            productCuttedPrice=itemView.findViewById(R.id.wishlistProductCuttedPriceTextView);
            paymentMethod=itemView.findViewById(R.id.wishlistDEliveryOptionTextView);
            wishlistDivider=itemView.findViewById(R.id.wishlistDivider);
            deleteImageButton=itemView.findViewById(R.id.wishlistDeleteImageButton);

        }

        private void setWishlist(final String productID, String resource, String title, long freeCouponNo,
                                 String averageRatingRate, long totalRatingsRate,
                                 String price, String cutPrice, boolean deliveryethod,
                                 final int index){

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_photo)).into(productImage);
            productTitle.setText(title);
            if(freeCouponNo !=0){
                couponIcon.setVisibility(View.VISIBLE);
                if(freeCouponNo==1){

                    freeCoupons.setText("free  Coupon");

                }else{
                    freeCoupons.setText("free " + freeCouponNo + " Coupon");
                }

            }else{
                couponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }

            averageRatings.setText(averageRatingRate);
            totalRatings.setText("("+totalRatingsRate +") ratings");
            productPrice.setText(price);
            productCuttedPrice.setText(cutPrice);
            if(deliveryethod){
                paymentMethod.setText("Cash on delivery available");
            }else{
                paymentMethod.setText("Cash on delivery is not available");
            }

            if(wishlist){

                deleteImageButton.setVisibility(View.VISIBLE);

            }else{

                deleteImageButton.setVisibility(View.GONE);

            }

            deleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!ProductDetailsActivity.runWishlistQuery) {

                        ProductDetailsActivity.runWishlistQuery = true;

                        DBqueries.removeFromWishlist(index, itemView.getContext());

                        Toast.makeText(itemView.getContext(), "Removed Successfully", Toast.LENGTH_SHORT).show();
                    }
                }

            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent=new Intent(itemView.getContext(), ProductDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("PRODUCT_ID",productID);
                    productDetailsIntent.putExtras(bundle);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });

        }
    }
}
