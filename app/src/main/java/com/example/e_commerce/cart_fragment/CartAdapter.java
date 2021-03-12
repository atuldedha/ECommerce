package com.example.e_commerce.cart_fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class CartAdapter extends RecyclerView.Adapter {

    private int lastPosition = -1;

    private List<CartItemModel> cartItemModelList;

    private TextView totalCartAmountTextView;

    private boolean showDeleteButton;

    public CartAdapter(List<CartItemModel> cartItemModelList, TextView totalCartAmountTextView, boolean showDeleteButton) {

        this.cartItemModelList = cartItemModelList;

        this.totalCartAmountTextView = totalCartAmountTextView;

        this.showDeleteButton = showDeleteButton;

    }

    @Override
    public int getItemViewType(int position) {
        switch(cartItemModelList.get(position).getType()){

            case 0:
                return CartItemModel.CART_ITEM;

            case 1:
                return CartItemModel.TOTAL_AMOUNT;

            default:
                return -1;

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){

            case CartItemModel.CART_ITEM:

                View cartItemview= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                return new CartItemViewHolder(cartItemview);

            case CartItemModel.TOTAL_AMOUNT:

                View cartTotalview=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                return new CartTotalAmountViewHolder(cartTotalview);

            default:

                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (cartItemModelList.get(position).getType()){

            case CartItemModel.CART_ITEM:

                String productID = cartItemModelList.get(position).getProductID();
                String resource=cartItemModelList.get(position).getProductImage();
                String title=cartItemModelList.get(position).getProductTitle();
                long freeCoupons=cartItemModelList.get(position).getNoOfFreeCoupons();
                String productPrice=cartItemModelList.get(position).getProductPrice();
                String cuttedPrice=cartItemModelList.get(position).getProductCuttedPrice();
                long offersApplied=cartItemModelList.get(position).getOffersApplied();
                boolean inStock = cartItemModelList.get(position).isInStock();


                ((CartItemViewHolder)holder).setItemDetails(productID,resource,title,freeCoupons,productPrice,cuttedPrice,offersApplied,position, inStock);

                break;

            case CartItemModel.TOTAL_AMOUNT:

                int totalItems = 0;
                int totalItemPrice = 0;
                int totalAmount;
                int savedAmount =0;

                String deliveryPrice;

                for(int i=0; i<cartItemModelList.size(); i++) {

                    if (cartItemModelList.get(i).getType() == CartItemModel.CART_ITEM && cartItemModelList.get(i).isInStock()) {

                        totalItems++;

                        totalItemPrice = totalItemPrice + Integer.parseInt(cartItemModelList.get(i).getProductPrice());

                    }
                }

                if(totalItemPrice > 500){

                    deliveryPrice = "Free";

                    totalAmount = totalItemPrice;

                }else{

                    deliveryPrice = "60";

                    totalAmount = totalItemPrice + 60;

                }

                ((CartTotalAmountViewHolder)holder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount);

                break;

            default:

               return;

        }if(lastPosition<position){

            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;

        }

    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImageView;
        private ImageView freeCouponIcon;
        private TextView productTitle;
        private TextView freeCoupons;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView couponsApplied;
        private TextView productQuantity;

        private LinearLayout couponRedemptionLayout;

        private LinearLayout removeItemLinearLayout;


        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);

            productImageView=itemView.findViewById(R.id.productImageView);
            productTitle=itemView.findViewById(R.id.productTitleTextView);
            freeCouponIcon=itemView.findViewById(R.id.freeCouponIconImageView);
            freeCoupons=itemView.findViewById(R.id.freeCouponTextView);
            productPrice=itemView.findViewById(R.id.productPriceTextView);
            cuttedPrice=itemView.findViewById(R.id.cuttedPriceTextView);
            offersApplied=itemView.findViewById(R.id.ooferAppliedTextView);
            couponsApplied=itemView.findViewById(R.id.couponAppliedTextView);
            productQuantity=itemView.findViewById(R.id.productQuantityTextView);

            couponRedemptionLayout = itemView.findViewById(R.id.couponRedemptionLinearLayout);

            removeItemLinearLayout = itemView.findViewById(R.id.removeItemLinearLayout);
        }
        private void setItemDetails(String productID, String resource, String title, long freeCouponsNo,
                                    String priceText, String cuttedPriceText, long offersApliedNo,
                                    final int position, boolean inStock){

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_photo)).into(productImageView);
            productTitle.setText(title);

            if(inStock) {

                if(freeCouponsNo>0){

                    if(freeCouponsNo==1){

                        freeCoupons.setText("free "+ freeCouponsNo +" Coupon");

                    }else{

                        freeCoupons.setText("free "+ freeCouponsNo +" Coupons");

                    }
                }else{

                    freeCouponIcon.setVisibility(View.INVISIBLE);
                    freeCoupons.setVisibility(View.INVISIBLE);

                }

                productPrice.setText("RS. " + priceText + " /-");
                productPrice.setTextColor(Color.parseColor("#000000"));
                cuttedPrice.setText("RS. " + cuttedPriceText + " /-");

                couponRedemptionLayout.setVisibility(View.VISIBLE);

                productQuantity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog quantityDialog=new Dialog(itemView.getContext());
                        quantityDialog.setContentView(R.layout.quantity_dialog_layout);

                        quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

                        quantityDialog.setCancelable(false);

                        Button dialogCancelButton=quantityDialog.findViewById(R.id.dialogCancelButton);
                        Button dialogOkButton=quantityDialog.findViewById(R.id.dialodOkButton);
                        final EditText dialogQuantityEditText=quantityDialog.findViewById(R.id.dialogQuantityEditText);

                        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                quantityDialog.dismiss();
                            }
                        });

                        dialogOkButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!TextUtils.isEmpty(dialogQuantityEditText.getText())){

                                    productQuantity.setText("Qty : "+dialogQuantityEditText.getText());
                                    quantityDialog.dismiss();

                                }else{

                                    Toast.makeText(itemView.getContext(), "Field is empty", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                        quantityDialog.show();

                    }
                });

                if(offersApliedNo>0){
                    offersApplied.setVisibility(View.VISIBLE);
                    offersApplied.setText(offersApliedNo + " offers applied");
                }else{
                    offersApplied.setVisibility(View.INVISIBLE);
                }


            }else{

                freeCouponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);

                productPrice.setText("Out of Stock");
                productPrice.setTextColor(itemView.getResources().getColor(R.color.colorRed));
                cuttedPrice.setText("");

                couponRedemptionLayout.setVisibility(View.INVISIBLE);

                productQuantity.setVisibility(View.INVISIBLE);

                offersApplied.setVisibility(View.INVISIBLE);

                //////One alternative fade product quantity text
                /*productQuantity.setText("Qty : 0");
                productQuantity.setTextColor(Color.parseColor("#70000000"));
                productQuantity.setCompoundDrawableTintList(ColorStateList.valueOf(Color.parseColor("#70000000")));
                productQuantity.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#70000000")));
*/


            }


            if(showDeleteButton){
                removeItemLinearLayout.setVisibility(View.VISIBLE);
            }else{
                removeItemLinearLayout.setVisibility(View.GONE);
            }

            removeItemLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!ProductDetailsActivity.runCartQuery){

                        ProductDetailsActivity.runCartQuery = true;

                        DBqueries.removeFromCart(position,itemView.getContext(), totalCartAmountTextView);

                    }

                }
            });

        }

    }

    public class CartTotalAmountViewHolder extends RecyclerView.ViewHolder{

        private TextView totalItems;
        private TextView totalItemPrice;
        private TextView totaldeliverPrice;
        private TextView totalAmount;
        private TextView savedAmount;


        public CartTotalAmountViewHolder(@NonNull View itemView) {
            super(itemView);

            totalItems=itemView.findViewById(R.id.totalItemsTextView);
            totalItemPrice=itemView.findViewById(R.id.totalItemsPriceTextView);
            totaldeliverPrice=itemView.findViewById(R.id.deliveryPriceTextView);
            totalAmount=itemView.findViewById(R.id.totalPriceTextView);
            savedAmount=itemView.findViewById(R.id.savedAmountTextView);

        }

        private void setTotalAmount(int totalItemText,int totalItemPriceText,String totalDeliveryText,int totalAmountText,int savedAmountText){
            totalItems.setText("Price ( " + totalItemText + ") items");
            totalItemPrice.setText("Rs. "+ totalItemPriceText + "/-" );
            if (totalDeliveryText.equals("Free")) {
                totaldeliverPrice.setText(totalDeliveryText);
            }else{
                totaldeliverPrice.setText("Rs. "+ totalDeliveryText + "/-");
            }
            totalAmount.setText("Rs. " + totalAmountText + "/-");

            totalCartAmountTextView.setText("Rs. " + totalAmountText + "/-");

            savedAmount.setText("You saved Rs. " + savedAmountText  + "/- on this order");

            LinearLayout parentLinearLayout = (LinearLayout) totalCartAmountTextView.getParent().getParent();

            if (totalItemPriceText == 0){

                DBqueries.cartItemModelList.remove(DBqueries.cartItemModelList.size()-1);

                parentLinearLayout.setVisibility(View.GONE);

            }else{

                parentLinearLayout.setVisibility(View.VISIBLE);

            }

        }
    }

}
