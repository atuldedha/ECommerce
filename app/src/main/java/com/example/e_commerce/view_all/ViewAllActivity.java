package com.example.e_commerce.view_all;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;
import com.example.e_commerce.gridview_product.GridViewProductLAyoutAdapter;
import com.example.e_commerce.horizontal_scroll.HorizontalScrollModel;
import com.example.e_commerce.my_wishlist.WishListModel;
import com.example.e_commerce.my_wishlist.WishlistAdapter;

import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView viewAllRecyclerView;
    private GridView viewAllGridVew;
    private int layoutCode;

    public static List<HorizontalScrollModel> horizontalScrollModelList;

    public static List<WishListModel> wishListModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewAllRecyclerView=findViewById(R.id.viewAllRecyclerView);
        viewAllGridVew=findViewById(R.id.viewAllGridView);

        layoutCode=getIntent().getIntExtra("layoutCode",-1);

        if(layoutCode==0){

            viewAllRecyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            viewAllRecyclerView.setLayoutManager(linearLayoutManager);

            //List<WishListModel> wishListModelList=new ArrayList<>();

         /*   wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",1,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",3,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",2,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",4,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",5,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",1,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",3,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",2,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",4,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
            wishListModelList.add(new WishListModel(R.drawable.phone,"Nokia 1130",5,"4",980,"Rs 49999","Rs 59999","cash on delivery"));
          */

            WishlistAdapter wishlistAdapter=new WishlistAdapter(wishListModelList,false);

            viewAllRecyclerView.setAdapter(wishlistAdapter);

            wishlistAdapter.notifyDataSetChanged();

        }else if(layoutCode==1){

            viewAllGridVew.setVisibility(View.VISIBLE);



            GridViewProductLAyoutAdapter gridViewProductLAyoutAdapter=new GridViewProductLAyoutAdapter(horizontalScrollModelList);
            viewAllGridVew.setAdapter(gridViewProductLAyoutAdapter);

            gridViewProductLAyoutAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
