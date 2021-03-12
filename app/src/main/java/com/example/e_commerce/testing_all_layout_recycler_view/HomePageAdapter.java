package com.example.e_commerce.testing_all_layout_recycler_view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.e_commerce.R;
import com.example.e_commerce.banner_alider.SliderAdapter;
import com.example.e_commerce.banner_alider.SliderModel;
import com.example.e_commerce.horizontal_scroll.HorizontalScrollAdapter;
import com.example.e_commerce.horizontal_scroll.HorizontalScrollModel;
import com.example.e_commerce.my_wishlist.WishListModel;
import com.example.e_commerce.product_details.ProductDetailsActivity;
import com.example.e_commerce.view_all.ViewAllActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {

    private int lastPosition = -1;

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()){
            case 0:
                return HomePageModel.BANNER_SLIDER;

            case 1:
                return HomePageModel.STRIP_AD_BANNER;

            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;

            case 3:
                return HomePageModel.GRID_VIEW_PRODUCT;

            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){
            case HomePageModel.BANNER_SLIDER :

                View bannerSliderView= LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout,parent,false);
                return new BannerSliderViewHolder(bannerSliderView);

            case HomePageModel.STRIP_AD_BANNER:

                View stripAdView= LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout,parent,false);
                return new StripAdViewHolder(stripAdView);

            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:

                View horizontalScrollProductView= LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout,parent,false);
                return new HorizontalScrollProductViewHolder(horizontalScrollProductView);

            case HomePageModel.GRID_VIEW_PRODUCT:

                View gridViewProductView= LayoutInflater.from(parent.getContext()).inflate(R.layout.gridview_product_layout,parent,false);
                return new GridViewProductViewHolder(gridViewProductView);

            default:
                return null;
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (homePageModelList.get(position).getType()){

            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList=homePageModelList.get(position).getSliderModelList();
                ((BannerSliderViewHolder)holder).setBannerSliderViewPager(sliderModelList);
                break;

            case HomePageModel.STRIP_AD_BANNER:
                String resource=homePageModelList.get(position).getResource();
                String color=homePageModelList.get(position).getBackgroundColor();
                ((StripAdViewHolder)holder).setStripAd(resource,color);
                break;

            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                List<HorizontalScrollModel> horizontalScrollModelList=homePageModelList.get(position).getHorizontalScrollModelList();
                String horizontalScrollLayoutColor=homePageModelList.get(position).getBackgroundColor();
                String horizontalProductViewTitle=homePageModelList.get(position).getTitle();
                List<WishListModel> viewAllProductList=homePageModelList.get(position).getViewAllProductList();
                ((HorizontalScrollProductViewHolder)holder).setHorizontalScrollProduct(horizontalScrollModelList,horizontalProductViewTitle,horizontalScrollLayoutColor,viewAllProductList);
                break;

            case HomePageModel.GRID_VIEW_PRODUCT:
               List<HorizontalScrollModel> gridViewProductModelList=homePageModelList.get(position).getHorizontalScrollModelList();

               String gridLayoutColor=homePageModelList.get(position).getBackgroundColor();
               String gridViewProductTitle=homePageModelList.get(position).getTitle();
               ((GridViewProductViewHolder)holder).setGridViewProduct(gridViewProductModelList,gridViewProductTitle,gridLayoutColor);
               break;

            default:
                return;

        }

        if(lastPosition < position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder{

        private ViewPager bannerSliderViewPager;
        private int currentPage;
        private Timer timer;
        private List<SliderModel> arrangeList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);

            bannerSliderViewPager=itemView.findViewById(R.id.banner_slider_viewPager);

        }

        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList){

            currentPage=2;

            if(timer!=null){

                timer.cancel();

            }
            /////arranging list to an infinite Loop List

            arrangeList=new ArrayList<>();

            for(int i=0;i<sliderModelList.size();i++){

                arrangeList.add(i,sliderModelList.get(i));

            }

            arrangeList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangeList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangeList.add(sliderModelList.get(0));
            arrangeList.add(sliderModelList.get(1));

            ///////arranging list to an infinite loop List;

            SliderAdapter sliderAdapter=new SliderAdapter(arrangeList);


            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);

            bannerSliderViewPager.setCurrentItem(currentPage);

            ViewPager.OnPageChangeListener pageChangeListener=new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state==ViewPager.SCROLL_STATE_IDLE){
                        pageLooper(arrangeList);
                    }
                }
            };

            bannerSliderViewPager.addOnPageChangeListener(pageChangeListener);

            bannerSlideshow(arrangeList);

            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    pageLooper(arrangeList);
                    stopBannerSlideshow();

                    if(event.getAction()==MotionEvent.ACTION_UP){
                        bannerSlideshow(arrangeList);
                    }

                    return false;
                }
            });

        }

        private  void pageLooper(List<SliderModel> sliderModelList){
            if(currentPage==sliderModelList.size()-2){
                currentPage=2;
                bannerSliderViewPager.setCurrentItem(currentPage,false);

            }

            if(currentPage==1){
                currentPage=sliderModelList.size()-3;
                bannerSliderViewPager.setCurrentItem(currentPage,false);

            }

        }

        private void bannerSlideshow(final List<SliderModel> sliderModelList){
            final Handler handler=new Handler();
            final Runnable runnable=new Runnable() {
                @Override
                public void run() {
                    if(currentPage>=sliderModelList.size()){
                        currentPage=1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++,true);
                }
            };
            timer=new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            },3000,3000);
        }

        private void stopBannerSlideshow(){
            timer.cancel();
        }
    }

    public class StripAdViewHolder extends RecyclerView.ViewHolder{

        private ImageView stripAdImageView;
        private ConstraintLayout stripAdContainerConstraintLayout;

        public StripAdViewHolder(@NonNull View itemView) {
            super(itemView);

            stripAdImageView=itemView.findViewById(R.id.strip_adImageView);
            stripAdContainerConstraintLayout=itemView.findViewById(R.id.strip_ad_containerConstraintLayout);

        }

        private void setStripAd(String resource,String color){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.ic_photo)).into(stripAdImageView);
            stripAdContainerConstraintLayout.setBackgroundColor(Color.parseColor(color));

        }
    }

    public class HorizontalScrollProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout horizontalScrollConstraintLaout;
        private TextView horizontalScrollTextView;
        private Button horizontalScrollButton;
        private RecyclerView horizontalScrollRecyclerView;

        public HorizontalScrollProductViewHolder(@NonNull View itemView) {
            super(itemView);

            horizontalScrollConstraintLaout=itemView.findViewById(R.id.horizontalScrollConstraintLayout);
            horizontalScrollTextView=itemView.findViewById(R.id.horizontalScrollTextView);
            horizontalScrollButton=itemView.findViewById(R.id.horizontalScrollButton);
            horizontalScrollRecyclerView=itemView.findViewById(R.id.horizontalScrollRecyclerView);
            horizontalScrollRecyclerView.setRecycledViewPool(recycledViewPool);

        }

        private void setHorizontalScrollProduct(List<HorizontalScrollModel> horizontalScrollModelList, final String title, String horizontalScrollLayoutColor, final List<WishListModel> viewAllProductList){

            horizontalScrollConstraintLaout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(horizontalScrollLayoutColor)));

            horizontalScrollTextView.setText(title);

            if(horizontalScrollModelList.size()>8){

                horizontalScrollButton.setVisibility(View.VISIBLE);

                if(!title.equals("")) {
                    horizontalScrollButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ViewAllActivity.wishListModelList = viewAllProductList;

                            Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                            viewAllIntent.putExtra("layoutCode", 0);
                            viewAllIntent.putExtra("title", title);
                            itemView.getContext().startActivity(viewAllIntent);
                        }
                    });
                }

            }else{
                horizontalScrollButton.setVisibility(View.INVISIBLE);
            }

            HorizontalScrollAdapter horizontalScrollAdapter=new HorizontalScrollAdapter(horizontalScrollModelList);

            LinearLayoutManager horizontalScrollLinearLayoutManager=new LinearLayoutManager(itemView.getContext());
            horizontalScrollLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);;

            horizontalScrollRecyclerView.setLayoutManager(horizontalScrollLinearLayoutManager);

            horizontalScrollRecyclerView.setAdapter(horizontalScrollAdapter);

            horizontalScrollAdapter.notifyDataSetChanged();

        }
    }

    public class GridViewProductViewHolder extends RecyclerView.ViewHolder{

        private ConstraintLayout gridProductConstraintLayout;
        private TextView gridViewProductLayoutTextView;
        private Button gridViewProductLayoutViewAllButton;
        private GridLayout gridViewProductGridLayout;


        public GridViewProductViewHolder(@NonNull View itemView) {
            super(itemView);

            gridProductConstraintLayout=itemView.findViewById(R.id.gridProductConstrainitLayout);
            gridViewProductLayoutTextView=itemView.findViewById(R.id.gridViewProductLayoutTitleTextView);
            gridViewProductLayoutViewAllButton=itemView.findViewById(R.id.gridViewProductLayoutViewAllButton);
            gridViewProductGridLayout =itemView.findViewById(R.id.gridViewProductGridLayout);

        }

        private void setGridViewProduct(final List<HorizontalScrollModel> horizontalGridScrollModelList, final String title, String gridLayoutColor){

            gridProductConstraintLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(gridLayoutColor)));

            gridViewProductLayoutTextView.setText(title);


            for(int i=0;i<4;i++){

            ImageView productImage = gridViewProductGridLayout.getChildAt(i).findViewById(R.id.horizontalScrollProductImageView);
            TextView productTitle = gridViewProductGridLayout.getChildAt(i).findViewById(R.id.horizontalScrollProductNameTextView);
            final TextView productDescription = gridViewProductGridLayout.getChildAt(i).findViewById(R.id.horizontalProductProcessorTextView);
            TextView productPrie = gridViewProductGridLayout.getChildAt(i).findViewById(R.id.horizontalScrollProductPriceTextView);

            Glide.with(itemView.getContext()).load(horizontalGridScrollModelList.get(i).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.ic_photo)).into(productImage);
            productTitle.setText(horizontalGridScrollModelList.get(i).getProductName());
            productPrie.setText(horizontalGridScrollModelList.get(i).getProductPrice());
            productDescription.setText(horizontalGridScrollModelList.get(i).getProductProcessor());

            gridViewProductGridLayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));

            if(!title.equals("")) {

                final int finalI = i;
                gridViewProductGridLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("PRODUCT_ID",horizontalGridScrollModelList.get(finalI).getProductId());
                        productDetailsIntent.putExtras(bundle);
                        itemView.getContext().startActivity(productDetailsIntent);
                    }
                });
            }

        }

            if(horizontalGridScrollModelList.size()>4){

                gridViewProductLayoutViewAllButton.setVisibility(View.VISIBLE);

                if(!title.equals("")) {
                    gridViewProductLayoutViewAllButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ViewAllActivity.horizontalScrollModelList = horizontalGridScrollModelList;

                            Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                            viewAllIntent.putExtra("layoutCode", 1);
                            viewAllIntent.putExtra("title", title);
                            itemView.getContext().startActivity(viewAllIntent);
                        }
                    });
                }

            }else{
                gridViewProductLayoutViewAllButton.setVisibility(View.INVISIBLE);
            }

        }
    }

}
