package com.example.e_commerce.category_activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.banner_alider.SliderModel;
import com.example.e_commerce.horizontal_scroll.HorizontalScrollModel;
import com.example.e_commerce.my_wishlist.WishListModel;
import com.example.e_commerce.testing_all_layout_recycler_view.HomePageAdapter;
import com.example.e_commerce.testing_all_layout_recycler_view.HomePageModel;

import java.util.ArrayList;
import java.util.List;

import static com.example.e_commerce.DBqueries.gettingCategoriesDataList;
import static com.example.e_commerce.DBqueries.loadFragmentData;
import static com.example.e_commerce.DBqueries.loadedCategoriesNameList;

public class CategoryActivity extends AppCompatActivity {

    private List<HomePageModel> initialHomePageModelList = new ArrayList<>();

    private RecyclerView categoryRecyclerView;

    HomePageAdapter homePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title=getIntent().getStringExtra("categoryName");
        getSupportActionBar().setTitle(title);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryRecyclerView=findViewById(R.id.categoryRecyclerView);

        //////Slider Banner work

        List<SliderModel> sliderModelList=new ArrayList<>();

        ////Slider Banner work;


        //////Horizontal product scroll work

        List<HorizontalScrollModel> horizontalScrollModelList=new ArrayList<>();


        //////Horizontal product scroll work;

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


        /////////Main layout recycler view

        LinearLayoutManager testingLinearLayoutManager=new LinearLayoutManager(this);
        testingLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLinearLayoutManager);
        homePageAdapter=new HomePageAdapter(initialHomePageModelList);


        int listPosition=0;

        for(int i=0;i<loadedCategoriesNameList.size();i++){

            if(loadedCategoriesNameList.get(i).equals(title.toUpperCase())){
                listPosition=i;
            }

        }

        if(listPosition==0){

            loadedCategoriesNameList.add(title.toUpperCase());
            gettingCategoriesDataList.add(new ArrayList<HomePageModel>());
            loadFragmentData(categoryRecyclerView,this,loadedCategoriesNameList.size()-1,title);

        }else{

            homePageAdapter=new HomePageAdapter(gettingCategoriesDataList.get(listPosition));

        }


        categoryRecyclerView.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.mainSerachIcon){

            return true;

        }else if(id==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
