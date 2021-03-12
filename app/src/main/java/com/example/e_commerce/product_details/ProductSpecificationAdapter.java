package com.example.e_commerce.product_details;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.ViewHolder> {

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch(productSpecificationModelList.get(position).getType()){

            case 0:
                return ProductSpecificationModel.specificationTitle;
            case 1:
                return ProductSpecificationModel.descriptionBody;
            default :
                return -1;

        }
    }

    @NonNull
    @Override
    public ProductSpecificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case ProductSpecificationModel.specificationTitle:

            TextView title = new TextView(parent.getContext());
            title.setTypeface(null, Typeface.BOLD);
            title.setTextColor(Color.parseColor("#000000"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(setDp(16,parent.getContext()),setDp(8,parent.getContext()),
                    setDp(16,parent.getContext()),setDp(8,parent.getContext()));

            title.setLayoutParams(layoutParams);
            return new ViewHolder(title);

            case ProductSpecificationModel.descriptionBody:

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout,parent,false);
                return new ViewHolder(view);

            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull ProductSpecificationAdapter.ViewHolder holder, int position) {

        switch (productSpecificationModelList.get(position).getType()){

            case 0:

                holder.setTitle(productSpecificationModelList.get(position).getTitle());

                break;
            case 1:

                String featureName=productSpecificationModelList.get(position).getProductFeatureName();
                String featureDetail=productSpecificationModelList.get(position).getProductFeatureDetail();

                holder.setFeatures(featureName,featureDetail);
                break;
            default:
                return;

        }



    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView featureNameTextView;
        private TextView featureDetailTextView;
        private TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private void setTitle(String titleText){
            title = (TextView) itemView;

            title.setText(titleText);
        }

        private void setFeatures(String featureName,String featureDetail){

            featureNameTextView=itemView.findViewById(R.id.featureNameTextView);
            featureDetailTextView=itemView.findViewById(R.id.featureDetailsTextView);

            featureNameTextView.setText(featureName);
            featureDetailTextView.setText(featureDetail);
        }
    }

    private int setDp(int dp, Context context){

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());

    }

}
