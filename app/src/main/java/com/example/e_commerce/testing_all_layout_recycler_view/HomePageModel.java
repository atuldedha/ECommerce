package com.example.e_commerce.testing_all_layout_recycler_view;

import com.example.e_commerce.banner_alider.SliderModel;
import com.example.e_commerce.horizontal_scroll.HorizontalScrollModel;
import com.example.e_commerce.my_wishlist.WishListModel;

import java.util.List;

public class HomePageModel {

    public static final int BANNER_SLIDER=0;

    public static final int STRIP_AD_BANNER=1;

    public static final int HORIZONTAL_PRODUCT_VIEW=2;

    public static final int GRID_VIEW_PRODUCT=3;

    private int type;
    private String backgroundColor;
    ////////Banner Slider Testing Using Recycler View

    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }



    ////////Banner Slider Testing Using Recycler View

    ///////Strip ad testing using recycler view

    private String resource;


    public HomePageModel(int type, String resource, String backgroundColor) {
        this.type = type;
        this.resource = resource;
        this.backgroundColor = backgroundColor;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    ///////Strip ad testing using recyclwe view

    ///////Horizontal product scroll work &//////Grid Product work

    private String title;
    private List<HorizontalScrollModel> horizontalScrollModelList;

    ///////Horizontal product scroll work

    private List<WishListModel> viewAllProductList;

    public HomePageModel(int type, String title, String backgroundColor , List<HorizontalScrollModel> horizontalScrollModelList,List<WishListModel> viewAllProductList) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;
        this.horizontalScrollModelList = horizontalScrollModelList;
        this.viewAllProductList=viewAllProductList;
    }

    public List<WishListModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishListModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }

    ///////Horizontal product scroll work

    //////Grid Product work

    public HomePageModel(int type, String title, String backgroundColor , List<HorizontalScrollModel> horizontalScrollModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;
        this.horizontalScrollModelList = horizontalScrollModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalScrollModel> getHorizontalScrollModelList() {
        return horizontalScrollModelList;
    }

    public void setHorizontalScrollModelList(List<HorizontalScrollModel> horizontalScrollModelList) {
        this.horizontalScrollModelList = horizontalScrollModelList;
    }

    //////Grid Product work

    // /////Horizontal product scroll work & //////Grid Product work




}
