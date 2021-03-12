package com.example.e_commerce.main_screen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.e_commerce.R;
import com.example.e_commerce.banner_alider.SliderModel;
import com.example.e_commerce.category_activity.CategoryAdapter;
import com.example.e_commerce.category_activity.CategoryModel;
import com.example.e_commerce.horizontal_scroll.HorizontalScrollModel;
import com.example.e_commerce.my_wishlist.WishListModel;
import com.example.e_commerce.testing_all_layout_recycler_view.HomePageAdapter;
import com.example.e_commerce.testing_all_layout_recycler_view.HomePageModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.e_commerce.DBqueries.categoryModelList;
import static com.example.e_commerce.DBqueries.gettingCategoriesDataList;
import static com.example.e_commerce.DBqueries.loadCategories;
import static com.example.e_commerce.DBqueries.loadFragmentData;
import static com.example.e_commerce.DBqueries.loadedCategoriesNameList;
import static com.example.e_commerce.main_screen.StarterActivity.drawer;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public static SwipeRefreshLayout swipeRefreshLayout;

    private HomePageAdapter homePageAdapter;

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;

    private ImageView showInternetConnectionImageView;

    ///////Main Layout Recycler View

    private RecyclerView homePageRecyclerView;

    //////Main LayoutRecycler view;

    //////checking Connection

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private Button retryButton;

    //////checking connection

    //////dummy list to show until data loads

    private List<CategoryModel> initialCategoryList = new ArrayList<>();
    private List<HomePageModel> initialHomePageModelList = new ArrayList<>();

    //////;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        showInternetConnectionImageView=view.findViewById(R.id.showInternetConnectionImageView);

        categoryRecyclerView=view.findViewById(R.id.categoryRecyclerView);

        homePageRecyclerView=view.findViewById(R.id.homePageRecyclerView);

        retryButton = view.findViewById(R.id.retryButton);

        /////category
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(linearLayoutManager);

        /////categories;

        /////main screen
        LinearLayoutManager testingLinearLayoutManager=new LinearLayoutManager(getContext());
        testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        homePageRecyclerView.setLayoutManager(testingLinearLayoutManager);

        ////main screen

        /////to show a fake list untill we retrieve the data from firebase

        initialCategoryList.add(new CategoryModel("null",""));
        initialCategoryList.add(new CategoryModel("null",""));
        initialCategoryList.add(new CategoryModel("null",""));
        initialCategoryList.add(new CategoryModel("null",""));
        initialCategoryList.add(new CategoryModel("null",""));
        initialCategoryList.add(new CategoryModel("null",""));
        initialCategoryList.add(new CategoryModel("null",""));

        //////Home page dummy lists to show untill data loads

        /////dummy Slider Mode lList

        List<SliderModel> dummySliderModelList = new ArrayList<>();

        dummySliderModelList.add(new SliderModel("null","#AB14C5"));
        dummySliderModelList.add(new SliderModel("null","#AB14C5"));
        dummySliderModelList.add(new SliderModel("null","#AB14C5"));
        dummySliderModelList.add(new SliderModel("null","#AB14C5"));
        dummySliderModelList.add(new SliderModel("null","#AB14C5"));
        dummySliderModelList.add(new SliderModel("null","#AB14C5"));

        /////dummy Slider Mode lList

        //////dummy Horizontal product scroll work

        List<HorizontalScrollModel> initialHorizontalScrollModelList = new ArrayList<>();

        initialHorizontalScrollModelList.add(new HorizontalScrollModel("","","","",""));
        initialHorizontalScrollModelList.add(new HorizontalScrollModel("","","","","" ));
        initialHorizontalScrollModelList.add(new HorizontalScrollModel("","","","",""));
        initialHorizontalScrollModelList.add(new HorizontalScrollModel("","","","",""));
        initialHorizontalScrollModelList.add(new HorizontalScrollModel("","","","",""));
        initialHorizontalScrollModelList.add(new HorizontalScrollModel("","","","",""));
        initialHorizontalScrollModelList.add(new HorizontalScrollModel("","","","",""));
        initialHorizontalScrollModelList.add(new HorizontalScrollModel("","","","",""));

        //////dummy Horizontal product scroll work;

        initialHomePageModelList.add(new HomePageModel(0,dummySliderModelList));
        initialHomePageModelList.add(new HomePageModel(1,"","#ffffff"));
        initialHomePageModelList.add(new HomePageModel(2,"","#ffffff",initialHorizontalScrollModelList,new ArrayList<WishListModel>()));
        initialHomePageModelList.add(new HomePageModel(3,"","#ffffff",initialHorizontalScrollModelList));


        //////////Home page dummy lists to show untill data loads;

        /////to show a fake list untill we retrieve the data from firebase

        categoryAdapter=new CategoryAdapter(initialCategoryList);


        ////////;

        ///////home page dummt list

        homePageAdapter = new HomePageAdapter(initialHomePageModelList);



        //////home page dummy list

        ///checking internet connection

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()==true) {

            showInternetConnectionImageView.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);

            drawer.setDrawerLockMode(0);

            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if(categoryModelList.size()==0){
                loadCategories(categoryRecyclerView,getContext());
            }else {
                categoryAdapter = new CategoryAdapter(categoryModelList);
                categoryAdapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(categoryAdapter);

            if(gettingCategoriesDataList.size()==0){

                loadedCategoriesNameList.add("HOME");
                gettingCategoriesDataList.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView,getContext(),0,"HOME");

            }else {

                homePageAdapter=new HomePageAdapter(gettingCategoriesDataList.get(0));
                homePageAdapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(homePageAdapter);

        }else{

            retryButton.setVisibility(View.VISIBLE);

            drawer.setDrawerLockMode(1);

            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);

            Glide.with(this).load(R.drawable.ic_photo).into(showInternetConnectionImageView);
            showInternetConnectionImageView.setVisibility(View.VISIBLE);

        }

        /////checking internet connection;

        /////refresh layout

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setRefreshing(true);

                loadPage();

            }
        });

        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadPage();

            }
        });

        /////;


        return  view;
    }

    private void loadPage(){

        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        gettingCategoriesDataList.clear();
        loadedCategoriesNameList.clear();
        categoryAdapter = new CategoryAdapter(initialCategoryList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        homePageAdapter = new HomePageAdapter(initialHomePageModelList);
        homePageRecyclerView.setAdapter(homePageAdapter);

        if(networkInfo != null && networkInfo.isConnected()==true) {

            showInternetConnectionImageView.setVisibility(View.GONE);
            retryButton.setVisibility(View.GONE);

            drawer.setDrawerLockMode(0);

            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            categoryRecyclerView.setAdapter(categoryAdapter);

            homePageRecyclerView.setAdapter(homePageAdapter);

            loadCategories(categoryRecyclerView,getContext());

            loadedCategoriesNameList.add("HOME");
            gettingCategoriesDataList.add(new ArrayList<HomePageModel>());
            loadFragmentData(homePageRecyclerView,getContext(),0,"HOME");


        }else{

            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();

            retryButton.setVisibility(View.VISIBLE);

            drawer.setDrawerLockMode(1);

            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);

            Glide.with(getContext()).load(R.drawable.ic_photo).into(showInternetConnectionImageView);
            showInternetConnectionImageView.setVisibility(View.VISIBLE);

            swipeRefreshLayout.setRefreshing(false);
        }

    }

}
