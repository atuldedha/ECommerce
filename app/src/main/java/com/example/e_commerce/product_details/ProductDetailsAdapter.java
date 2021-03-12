package com.example.e_commerce.product_details;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ProductDetailsAdapter extends FragmentPagerAdapter {

    private int totalTabs;
    private String productDescription;
    private String productOtherDetails;
    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductDetailsAdapter(@NonNull FragmentManager fm,int totalTabs, String productDescription, String productOtherDetails, List<ProductSpecificationModel> productSpecificationModelList) {
        super(fm);
        this.totalTabs = totalTabs;
        this.productDescription = productDescription;
        this.productOtherDetails = productOtherDetails;
        this.productSpecificationModelList = productSpecificationModelList;
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                ProductDescriptionAndOtherFragment productDescriptionAndOtherFragment_1=new ProductDescriptionAndOtherFragment();
                productDescriptionAndOtherFragment_1.body=productDescription;

                return productDescriptionAndOtherFragment_1;

            case 1:
                ProductSpecificationsFragment productSpecificationsFragment=new ProductSpecificationsFragment();
                productSpecificationsFragment.productSpecificationModelList = productSpecificationModelList;

                return productSpecificationsFragment;

            case 2:
                ProductDescriptionAndOtherFragment productDescriptionAndOtherFragment_2=new ProductDescriptionAndOtherFragment();
                productDescriptionAndOtherFragment_2.body = productOtherDetails;

                return productDescriptionAndOtherFragment_2;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
