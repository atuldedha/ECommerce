package com.example.e_commerce;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.add_address.AddAddressActivity;
import com.example.e_commerce.banner_alider.SliderModel;
import com.example.e_commerce.cart_fragment.CartItemModel;
import com.example.e_commerce.cart_fragment.MyCartFragment;
import com.example.e_commerce.category_activity.CategoryAdapter;
import com.example.e_commerce.category_activity.CategoryModel;
import com.example.e_commerce.delivery.DeliveryActivity;
import com.example.e_commerce.horizontal_scroll.HorizontalScrollModel;
import com.example.e_commerce.my_addresses.MyAddressesModel;
import com.example.e_commerce.my_wishlist.MyWishlistFragment;
import com.example.e_commerce.my_wishlist.WishListModel;
import com.example.e_commerce.product_details.ProductDetailsActivity;
import com.example.e_commerce.testing_all_layout_recycler_view.HomePageAdapter;
import com.example.e_commerce.testing_all_layout_recycler_view.HomePageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.e_commerce.main_screen.MainFragment.swipeRefreshLayout;

public class DBqueries {

    //////Main list(home page)

    //public static List<HomePageModel> homePageModelList=new ArrayList<>();

    public static List<List<HomePageModel>> gettingCategoriesDataList = new ArrayList<>();
    public static List<String> loadedCategoriesNameList=new ArrayList<>();

    //////main list(homepage);

    /////firebase

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    //////firebase;

    /////category List
    public static List<CategoryModel> categoryModelList=new ArrayList<>();
    /////category list;


    /////wishlist list

    public static List<String> wishlist = new ArrayList<>();

    public static List<WishListModel> wishListModelList = new ArrayList<>();

    /////wishlist list

    ////ratings list

    public static List<String> myRatedProductIDs = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();

    ////ratings list

    ////carts list

    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();

    ////carts list

    ///Addresses

    public static boolean addAddressSelected = false;
    public static List<MyAddressesModel> myAddressesModelList = new ArrayList<>();
    public static int selectedAddress = -1;

    ///Addresses

    /////query to get category items

    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context){

        categoryModelList.clear();

        firebaseFirestore.collection("Categories").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){

                                categoryModelList.add(new CategoryModel(queryDocumentSnapshot.get("icon").toString(),queryDocumentSnapshot.get("categoryName").toString()));

                            }
                            CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModelList);

                            categoryRecyclerView.setAdapter(categoryAdapter);

                            categoryAdapter.notifyDataSetChanged();

                        }else{

                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    /////query to get category items;

    /////query to load all the products layout

    public static void loadFragmentData(final RecyclerView homePageRecyclerView, final Context context, final int index, String categoryName){


        firebaseFirestore.collection("Categories").document(categoryName.toUpperCase())
                .collection("TOP_DEALS").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){

                            for(QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){

                                if((long) queryDocumentSnapshot.get("viewType")==0){

                                    List<SliderModel> sliderModelList=new ArrayList<>();

                                    long noOfBanners = (long)queryDocumentSnapshot.get("no_of_banner");

                                    for(long i=1 ; i<=noOfBanners ; i++){

                                        sliderModelList.add(new SliderModel(queryDocumentSnapshot.get("banner_"+i).toString(),
                                                queryDocumentSnapshot.get("banner_"+i+"_background").toString()));

                                    }

                                    gettingCategoriesDataList.get(index).add(new HomePageModel(0,sliderModelList));

                                }else if((long) queryDocumentSnapshot.get("viewType")==1){

                                    gettingCategoriesDataList.get(index).add(new HomePageModel(1,queryDocumentSnapshot.get("strip_ad_banner").toString(),
                                            queryDocumentSnapshot.get("background").toString()));

                                }else if((long) queryDocumentSnapshot.get("viewType")==2){

                                    List<WishListModel> viewAllProductList= new ArrayList<>();

                                    List<HorizontalScrollModel> horizontalScrollModelList=new ArrayList<>();

                                    long noOfProducts = (long)queryDocumentSnapshot.get("no_of_products");

                                    for(long i=1 ; i<=noOfProducts ; i++){

                                        horizontalScrollModelList.add(new HorizontalScrollModel(queryDocumentSnapshot.get("product_ID_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_image_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_title_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_description_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_price_"+i).toString()));

                                        viewAllProductList.add(new WishListModel(queryDocumentSnapshot.get("product_ID_"+i).toString(),
                                                queryDocumentSnapshot.get("product_image_"+i).toString(),
                                                queryDocumentSnapshot.get("product_fullTitle_"+i).toString(),
                                                (long) queryDocumentSnapshot.get("product_noOfCoupons_"+i),
                                                queryDocumentSnapshot.get("product_averageRating_"+i).toString(),
                                                (long)queryDocumentSnapshot.get("product_totalRating_"+i),
                                                queryDocumentSnapshot.get("product_price_"+i).toString(),
                                                queryDocumentSnapshot.get("product_cuttedPrice_"+i).toString(),
                                                (boolean)queryDocumentSnapshot.get("product_COD_"+i)));

                                    }

                                    gettingCategoriesDataList.get(index).add(new HomePageModel(2,queryDocumentSnapshot.get("layoutTitle").toString() , queryDocumentSnapshot.get("layoutBackground").toString() , horizontalScrollModelList,viewAllProductList));


                                }else if((long) queryDocumentSnapshot.get("viewType")==3){

                                    List<HorizontalScrollModel> horizontalGridProductModelList=new ArrayList<>();

                                    long noOfProducts = (long)queryDocumentSnapshot.get("no_of_products");

                                    for(long i=1 ; i<=noOfProducts ; i++){

                                        horizontalGridProductModelList.add(new HorizontalScrollModel(queryDocumentSnapshot.get("product_ID_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_image_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_title_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_description_"+i).toString()
                                                ,queryDocumentSnapshot.get("product_price_"+i).toString()));

                                    }

                                    gettingCategoriesDataList.get(index).add(new HomePageModel(3,queryDocumentSnapshot.get("layoutTitle").toString() , queryDocumentSnapshot.get("layoutBackground").toString() , horizontalGridProductModelList));

                                }


                            }

                            HomePageAdapter homePageAdapter = new HomePageAdapter(gettingCategoriesDataList.get(index));

                            homePageRecyclerView.setAdapter(homePageAdapter);

                            homePageAdapter.notifyDataSetChanged();

                            swipeRefreshLayout.setRefreshing(false);

                        }else{

                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    /////query to load all the product layouts;

    /////query to load wishlist

    public static void loadWishlist(final Context mContext, final Dialog dialog, final boolean loadProductData){

        wishlist.clear();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseFirestore.collection("USERS").document(currentUser.getUid())
                .collection("USER_DATA")
                .document("MY_WISHLIST").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    long listSize = (long) task.getResult().get("list_size");
                    for(long i=0; i< listSize; i++){

                        wishlist.add(task.getResult().get("product_ID_"+i).toString());

                        if(DBqueries.wishlist.contains(ProductDetailsActivity.docPath)){

                            ProductDetailsActivity.alreadyAddedToWishlist = true;

                            if (ProductDetailsActivity.addToWishlistFloatingButton != null) {

                                ProductDetailsActivity.addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE91E63")));

                            }
                        }else{

                            ProductDetailsActivity.alreadyAddedToWishlist = false;

                            if (ProductDetailsActivity.addToWishlistFloatingButton != null) {

                                ProductDetailsActivity.addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1ACAC")));

                            }

                        }

                        if(loadProductData) {

                            wishListModelList.clear();

                            final String productID = task.getResult().get("product_ID_"+i).toString();

                            firebaseFirestore.collection("Products").document(task.getResult().get("product_ID_" + i).toString())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        wishListModelList.add(new WishListModel(productID,task.getResult().get("product_image_1").toString(),
                                                task.getResult().get("product_title").toString(),
                                                (long) task.getResult().get("freeCoupons"),
                                                task.getResult().get("average_rating").toString(),
                                                (long) task.getResult().get("total_ratings"),
                                                task.getResult().get("product_price").toString(),
                                                task.getResult().get("product_cuttedPrice").toString(),
                                                (boolean) task.getResult().get("COD")));


                                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }

                }else{

                    String error = task.getException().getMessage();
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();

                }
                dialog.dismiss();
            }
        });

    }

    /////query to load wishlist

    /////query to remove product from wishlist

    public static void removeFromWishlist(final int index, final Context mContext){

        final String removesProductID = wishlist.get(index);

        wishlist.remove(index);

        HashMap<String, Object> updateWishlist = new HashMap<>();

        for(int i=0; i<wishlist.size(); i++) {

            updateWishlist.put("product_ID_"+i,wishlist.get(i));

        }

        updateWishlist.put("list_size",(long) wishlist.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("USER_DATA").document("MY_WISHLIST")
                .set(updateWishlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    if(wishListModelList.size() != 0){

                        wishListModelList.remove(index);
                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();

                    }

                    ProductDetailsActivity.alreadyAddedToWishlist = false;

                    Toast.makeText(mContext, "Removed Successfully", Toast.LENGTH_SHORT).show();

                }else{

                    if (ProductDetailsActivity.addToWishlistFloatingButton != null) {

                        ProductDetailsActivity.addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE91E63")));

                    }
                    wishlist.add(index,removesProductID);
                    String error = task.getException().getMessage();
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();

                }

                /*if (ProductDetailsActivity.addToWishlistFloatingButton != null) {

                    ProductDetailsActivity.addToWishlistFloatingButton.setEnabled(true);

                }*/
                ProductDetailsActivity.runWishlistQuery = false;

            }

        });

    }

    /////query to remove product from wishlist

    /////query to load ratings

    public static void loadRatings(final Context mContext){

        if(!ProductDetailsActivity.runRatingQuery) {

            ProductDetailsActivity.runRatingQuery = true;

            myRatedProductIDs.clear();
            myRating.clear();

            firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("USER_DATA").document("MY_RATINGS")
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        for (long i = 0; i < (long) task.getResult().get("list_size"); i++) {

                            myRatedProductIDs.add(task.getResult().get("product_ID_" + i).toString());
                            myRating.add((long) task.getResult().get("rating_" + i));

                            if (task.getResult().get("product_ID_" + i).toString().equals(ProductDetailsActivity.docPath)) {

                                ProductDetailsActivity.initialRating = Integer.parseInt(String.valueOf((long) task.getResult().get("rating_" + i))) - 1;

                                if(ProductDetailsActivity.yourRatingLinearLayout !=null) {

                                    ProductDetailsActivity.setRating(ProductDetailsActivity.initialRating);

                                }

                            }

                        }

                    } else {

                        String error = task.getException().getMessage();
                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();

                    }

                    ProductDetailsActivity.runRatingQuery = false;

                }
            });
        }
    }

    /////query to load ratings

    ////quwery to load cart items

    public static void loadCartItem(final Context mContext, final Dialog dialog, final boolean loadProductData, final TextView badgeCount, final TextView totalCartAmountTextView) {
        cartList.clear();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseFirestore.collection("USERS").document(currentUser.getUid())
                .collection("USER_DATA")
                .document("MY_CARTLIST").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    long listSize = (long) task.getResult().get("list_size");

                    for (long i = 0; i < listSize; i++) {

                        cartList.add(task.getResult().get("product_ID_" + i).toString());

                        if (DBqueries.cartList.contains(ProductDetailsActivity.docPath)) {

                            ProductDetailsActivity.alreadyAddedToCart = true;

                        } else {

                            ProductDetailsActivity.alreadyAddedToCart = false;

                        }

                        if(loadProductData) {

                            cartItemModelList.clear();

                            final String productID = task.getResult().get("product_ID_"+i).toString();

                            firebaseFirestore.collection("Products").document(task.getResult().get("product_ID_" + i).toString())
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        int index = 0;

                                        if(cartList.size() >= 2){

                                            index = cartList.size() - 2;

                                        }

                                        cartItemModelList.add(index,new CartItemModel(CartItemModel.CART_ITEM,productID,task.getResult().get("product_image_1").toString(),
                                                task.getResult().get("product_title").toString(),
                                                task.getResult().get("product_price").toString(),
                                                task.getResult().get("product_cuttedPrice").toString(),
                                                (long) task.getResult().get("freeCoupons"),
                                                (long) 1,(long) 0,(long) 0,
                                                (boolean) task.getResult().get("in_stock")));

                                        if (cartList.size() == 1){

                                            cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));

                                            LinearLayout parentLinearLayout = (LinearLayout) totalCartAmountTextView.getParent().getParent();

                                            parentLinearLayout.setVisibility(View.VISIBLE);

                                        }

                                        if (cartList.size() == 0){

                                            cartItemModelList.clear();  /////so that total amount list does not show up after removal of products;

                                        }

                                        MyCartFragment.cartAdapter.notifyDataSetChanged();
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }

                    if(cartList.size() != 0){
                        badgeCount.setVisibility(View.VISIBLE);
                    }else{
                        badgeCount.setVisibility(View.INVISIBLE);
                    }
                    if(DBqueries.cartList.size()<99){
                        badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                    }else{
                        badgeCount.setText("99");
                    }

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();
            }
        });
    }

    /////query to load cart items

    /////query to remove cart items

    public static void removeFromCart(final int index, final Context mContext, final TextView totalCartAmountTextView){

        final String removesProductID = cartList.get(index);

        cartList.remove(index);

        HashMap<String, Object> updateCartlist = new HashMap<>();

        for(int i=0; i<cartList.size(); i++) {

            updateCartlist.put("product_ID_"+i,cartList.get(i));

        }

        updateCartlist.put("list_size",(long) cartList.size());

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("USER_DATA").document("MY_CARTLIST")
                .set(updateCartlist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    if(cartItemModelList.size() != 0){

                        cartItemModelList.remove(index);
                        MyCartFragment.cartAdapter.notifyDataSetChanged();

                    }

                    if (cartList.size() == 0){

                        LinearLayout parentLinearLayout = (LinearLayout) totalCartAmountTextView.getParent().getParent();

                        parentLinearLayout.setVisibility(View.GONE);

                        cartItemModelList.clear();  /////so that total amount list does not show up after removal of products;


                    }

                    ProductDetailsActivity.alreadyAddedToCart = false;

                    Toast.makeText(mContext, "Removed Successfully", Toast.LENGTH_SHORT).show();

                }else{

                    cartList.add(index,removesProductID);
                    String error = task.getException().getMessage();
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();

                }
                ProductDetailsActivity.runCartQuery = false;

            }

        });
    }

    /////query to remove cart items

    ////query to load addresses

    public static void loadAddresses(final Context mContext, final Dialog dialog){

        myAddressesModelList.clear();

        firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("USER_DATA").document("MY_ADDRESSES")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    Intent deliveryIntent;

                    if ((long) task.getResult().get("list_size") == 0){

                        deliveryIntent = new Intent(mContext, AddAddressActivity.class);
                        deliveryIntent.putExtra("INTENT","deliveryIntent");

                    } else{

                        for(long i=1; i<= (long) task.getResult().get("list_size"); i++){

                            myAddressesModelList.add(new MyAddressesModel(task.getResult().get("fullname_"+i).toString(),
                                    task.getResult().get("address_"+i).toString(),
                                    task.getResult().get("pincode_"+i).toString(),
                                    (boolean)task.getResult().get("selected_"+i)
                                    , task.getResult().get("mobile_no_" + i).toString()));

                            if((boolean)task.getResult().get("selected_"+i)){

                                selectedAddress = Integer.parseInt(String.valueOf(i-1));

                            }

                        }

                        deliveryIntent = new Intent(mContext, DeliveryActivity.class);

                    }

                    mContext.startActivity(deliveryIntent);

                }else{

                    String error = task.getException().getMessage();
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();

                }

                dialog.dismiss();

            }
        });

    }

    ////query to load addresses

    /////clear lists after signing out

    public static void clearData(){

        gettingCategoriesDataList.clear();
        gettingCategoriesDataList.clear();
        categoryModelList.clear();
        wishlist.clear();
        wishListModelList.clear();
        cartList.clear();
        cartItemModelList.clear();


    }

    /////clear lists after signing out

}

