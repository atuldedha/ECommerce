package com.example.e_commerce.my_rewards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;

import java.util.List;

import static com.example.e_commerce.product_details.ProductDetailsActivity.dialogCouponBody;
import static com.example.e_commerce.product_details.ProductDetailsActivity.dialogCouponDate;
import static com.example.e_commerce.product_details.ProductDetailsActivity.dialogCouponTitle;
import static com.example.e_commerce.product_details.ProductDetailsActivity.showDialogrecyclerView;

public class RewardsAdapter extends RecyclerView.Adapter<RewardsAdapter.ViewHolder> {

    private List<RewardsModel> rewardsModelList;
    private boolean useMiniLayout=false;

    public RewardsAdapter(List<RewardsModel> rewardsModelList,boolean useMiniLayout) {
        this.rewardsModelList = rewardsModelList;
        this.useMiniLayout=useMiniLayout;
    }

    @NonNull
    @Override
    public RewardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(useMiniLayout){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_reward_item_layout,parent,false);

        } else{
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.reward_item_layout,parent,false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsAdapter.ViewHolder holder, int position) {

        String title=rewardsModelList.get(position).getRewardTitle();
        String validity=rewardsModelList.get(position).getRewardValidity();
        String body=rewardsModelList.get(position).getRewardBody();

        holder.setRewards(title,validity,body);

    }

    @Override
    public int getItemCount() {
        return rewardsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView couponTitle;
        private TextView couponValidity;
        private TextView couponBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            couponTitle=itemView.findViewById(R.id.rewardDiscountCouponTextView);
            couponValidity=itemView.findViewById(R.id.rewardCouponValidityTextView);
            couponBody=itemView.findViewById(R.id.rewardBodyTextView);

        }

        private void setRewards(final String title, final String validity, final String body){

            couponTitle.setText(title);
            couponValidity.setText(validity);
            couponBody.setText(body);

            if(useMiniLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogCouponTitle.setText(title);
                        dialogCouponDate.setText(validity);
                        dialogCouponBody.setText(body);

                        showDialogrecyclerView();
                    }
                });
            }

        }
    }
}
