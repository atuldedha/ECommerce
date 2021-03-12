package com.example.e_commerce.product_details;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;
import com.example.e_commerce.cart_fragment.CartItemModel;
import com.example.e_commerce.delivery.DeliveryActivity;
import com.example.e_commerce.login_and_register.LoginActivity;
import com.example.e_commerce.login_and_register.SigninFragment;
import com.example.e_commerce.login_and_register.SignupFragment;
import com.example.e_commerce.main_screen.StarterActivity;
import com.example.e_commerce.my_rewards.RewardsAdapter;
import com.example.e_commerce.my_rewards.RewardsModel;
import com.example.e_commerce.my_wishlist.WishListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.e_commerce.login_and_register.LoginActivity.setSignUpFragment;
import static com.example.e_commerce.main_screen.StarterActivity.showCart;

public class ProductDetailsActivity extends AppCompatActivity {


    private TextView productTitleTextView;
    private TextView productRatingMiniTextView;
    private TextView productTotalRatingMiniTextView;
    private TextView productPriceTextView;
    private TextView productCuttedPriceTextView;
    private TextView codIndicatorTextView;

    private ImageView codIndicatorImageView;

    private Button couponRedemptionButton;
    private LinearLayout couponRedemptionLinearLayout;

    private TextView rewardWithProductTitleTextView;
    private TextView rewardWithProductBodyTextView;

    //////product description

    private ViewPager productImagesViewPager;
    private TabLayout viewPagerItems;

    private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

    private ConstraintLayout productDetailsOnlyConstraintLayout;
    private ConstraintLayout productDescriptionTabConstraintLaout;

    private TextView productDetailsOnlyBodyTextView;

    private String productDecriptionBody;
    private String productOtherDetails;


    //////product description

    //////coupon dialog

    public static TextView dialogCouponTitle;
    public static TextView dialogCouponDate;
    public static TextView dialogCouponBody;

    private static RecyclerView dialogCouponRecyclerView;
    private static LinearLayout showSelectedCouponLinearLayout;

    ///////coupon dialod

    /////rating layout

    public static LinearLayout yourRatingLinearLayout;
    private LinearLayout totalRatingNumberLinearLayout;
    private LinearLayout ratingProgressBarLinearLayout;

    private TextView totalRatingTextView;
    private TextView sumOfRatingsTextView;
    private TextView averageRatingTextView;

    public static int initialRating;

    public static boolean runRatingQuery = false;

    /////rating layout

    //////addd to cart and buy now

    private Button buyNNowButton;
    private LinearLayout addToCartButtonLinearLayout;

    public static boolean alreadyAddedToCart = false;

    public static boolean runCartQuery = false;

    public static MenuItem cartItem;

    /////add to cart and buy now;

    /////firebase

    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser currentUser;
    private DocumentSnapshot documentSnapshot;

    public static String docPath;

    /////firebase

    /////signin dialog

    private Dialog signInDialog;

    /////signin dialog

    /////loading dialog

    private Dialog progressDialog;

    /////loading dialog

    private ViewPager productDetaileViewPager;
    private TabLayout productDetailsTabLayout;

    /////wishlist

    public static boolean alreadyAddedToWishlist = false;
    public static FloatingActionButton addToWishlistFloatingButton;

    public static boolean runWishlistQuery = false;

    /////wishlist;

    private TextView badgeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.productImagesViewPager);
        viewPagerItems = findViewById(R.id.viewPagerItems);
        addToWishlistFloatingButton = findViewById(R.id.addToWishlistFloatingButton);

        productDetaileViewPager = findViewById(R.id.productDetailsViewPager);
        productDetailsTabLayout = findViewById(R.id.productDetailsTabLayout);

        buyNNowButton = findViewById(R.id.buyNowButton);
        addToCartButtonLinearLayout = findViewById(R.id.addToCartButtonLinearLayout);

        couponRedemptionButton = findViewById(R.id.couponRedemptionButton);

        couponRedemptionLinearLayout = findViewById(R.id.couponRedemptionLinearLayout);

        productTitleTextView = findViewById(R.id.productTitleTextView);
        productRatingMiniTextView = findViewById(R.id.productRatingMiniTextView);
        productTotalRatingMiniTextView = findViewById(R.id.productTotalRatingMiniTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productCuttedPriceTextView = findViewById(R.id.cuttedPriceTextView);
        codIndicatorTextView = findViewById(R.id.codIndicatorTextView);

        codIndicatorImageView = findViewById(R.id.codIndicatorImageView);

        rewardWithProductTitleTextView = findViewById(R.id.rewardWithProductTitleTextView);
        rewardWithProductBodyTextView = findViewById(R.id.rewardWithProductBodyTextView);

        productDetailsOnlyConstraintLayout = findViewById(R.id.productDetailsOnlyConstraintLayout);

        productDescriptionTabConstraintLaout = findViewById(R.id.productDescriptionTabConstraintLayout);

        showSelectedCouponLinearLayout = findViewById(R.id.showSelectedCouponLinearLayout);

        productDetailsOnlyBodyTextView = findViewById(R.id.productDetailsOnlyBodyTextView);

        totalRatingTextView = findViewById(R.id.totalRatingTextView);
        sumOfRatingsTextView = findViewById(R.id.sumOfRatingsTextView);

        totalRatingNumberLinearLayout = findViewById(R.id.totalRatingNumberLinearLayout);

        ratingProgressBarLinearLayout = findViewById(R.id.ratingProgressBarLinearLayout);

        averageRatingTextView = findViewById(R.id.productAverageRatingTextView);

        yourRatingLinearLayout = findViewById(R.id.yourRatingLinearLayout);

        initialRating = -1;

        firebaseFirestore = FirebaseFirestore.getInstance();

        final List<String> productImagesList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        docPath = bundle.getString("PRODUCT_ID");

        //////loading dialog

        progressDialog = new Dialog(ProductDetailsActivity.this);
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setCancelable(false);

        progressDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        progressDialog.show();

        //////loading dialog;

        CollectionReference cRef = firebaseFirestore.collection("Products");

        Task<DocumentSnapshot> dRef = cRef.document(docPath).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    documentSnapshot = task.getResult();

                    for (long i = 1; i < (long) documentSnapshot.get("no_of_images") + 1; i++) {

                        productImagesList.add(documentSnapshot.get("product_image_" + i).toString());

                    }

                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImagesList);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitleTextView.setText(documentSnapshot.get("product_title").toString());
                    productRatingMiniTextView.setText(documentSnapshot.get("average_rating").toString());
                    productTotalRatingMiniTextView.setText("(" + (long) documentSnapshot.get("total_ratings") + ") total ratings");
                    productPriceTextView.setText(documentSnapshot.get("product_price").toString());
                    productCuttedPriceTextView.setText(documentSnapshot.get("product_cuttedPrice").toString());
                    if ((boolean) documentSnapshot.get("COD")) {

                        codIndicatorImageView.setVisibility(View.VISIBLE);
                        codIndicatorTextView.setVisibility(View.VISIBLE);

                    } else {

                        codIndicatorImageView.setVisibility(View.INVISIBLE);
                        codIndicatorTextView.setVisibility(View.INVISIBLE);

                    }

                    rewardWithProductTitleTextView.setText((long) documentSnapshot.get("freeCoupons") + documentSnapshot.get("freeCouponTitle").toString());
                    rewardWithProductBodyTextView.setText(documentSnapshot.get("freeCouponBody").toString());

                    if ((boolean) documentSnapshot.get("use_tab_layout")) {

                        productDescriptionTabConstraintLaout.setVisibility(View.VISIBLE);
                        productDetailsOnlyConstraintLayout.setVisibility(View.GONE);

                        productDecriptionBody = documentSnapshot.get("product_description").toString();

                        productOtherDetails = documentSnapshot.get("product_other_details").toString();

                        productSpecificationModelList = new ArrayList<>();

                        long totalSpecTitle = (long) documentSnapshot.get("total_specification_title") + 1;

                        for (long i = 1; i < totalSpecTitle; i++) {

                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_" + i).toString()));

                            for (long j = 1; j < (long) documentSnapshot.get("spec_title_" + i + "_total_fields") + 1; j++) {

                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_" + i + "_field_" + j + "_name").toString(), documentSnapshot.get("spec_title_" + i + "_field_" + j + "_value").toString()));

                            }
                        }

                    } else {

                        productDescriptionTabConstraintLaout.setVisibility(View.GONE);
                        productDetailsOnlyConstraintLayout.setVisibility(View.VISIBLE);

                        productDetailsOnlyBodyTextView.setText(documentSnapshot.get("product_other_details").toString());

                    }

                    totalRatingTextView.setText((long) documentSnapshot.get("total_ratings") + " ratings");

                    for (int i = 0; i < 5; i++) {

                        TextView ratings = (TextView) totalRatingNumberLinearLayout.getChildAt(i);
                        String star = String.valueOf(documentSnapshot.get(5 - i + "_star"));
                        ratings.setText(star);

                        ProgressBar progressBar = (ProgressBar) ratingProgressBarLinearLayout.getChildAt(i);
                        int max = Integer.parseInt(String.valueOf(documentSnapshot.get("total_ratings")));
                        progressBar.setMax(max);
                        int progress = Integer.parseInt(String.valueOf(documentSnapshot.get(5 - i + "_star")));
                        progressBar.setProgress(progress);

                    }

                    sumOfRatingsTextView.setText(String.valueOf((long) documentSnapshot.get("total_ratings")));

                    averageRatingTextView.setText(documentSnapshot.get("average_rating").toString());

                    productDetaileViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDecriptionBody, productOtherDetails, productSpecificationModelList));

                    if (currentUser != null) {

                        if (DBqueries.myRating.size() == 0) {

                            DBqueries.loadRatings(ProductDetailsActivity.this);

                        }

                        if (DBqueries.cartList.size() == 0) {

                            DBqueries.loadCartItem(ProductDetailsActivity.this, progressDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));

                        }

                        if (DBqueries.wishlist.size() == 0) {

                            DBqueries.loadWishlist(ProductDetailsActivity.this, progressDialog, false);

                        } else {

                            progressDialog.dismiss();

                        }

                    } else {

                        progressDialog.dismiss();

                    }

                    if (DBqueries.myRatedProductIDs.contains(docPath)) {

                        int index = DBqueries.myRatedProductIDs.indexOf(docPath);
                        initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index))) - 1;

                        setRating(initialRating);

                    }

                    if (DBqueries.cartList.contains(docPath)) {

                        alreadyAddedToCart = true;

                    } else {

                        alreadyAddedToCart = false;

                    }

                    if (DBqueries.wishlist.contains(docPath)) {

                        alreadyAddedToWishlist = true;
                        addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE91E63")));

                    } else {

                        alreadyAddedToWishlist = false;
                        addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1ACAC")));

                    }

                    if ((boolean) documentSnapshot.get("in_stock")) {

                        ///cart button only works when product is in stock

                        addToCartButtonLinearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (currentUser == null) {

                                    signInDialog.show();

                                } else {

                                    if (!runCartQuery) {

                                        runCartQuery = true;

                                        if (alreadyAddedToCart) {

                                            runCartQuery = false;
                                            Toast.makeText(ProductDetailsActivity.this, "Item already added in the cart", Toast.LENGTH_SHORT).show();

                                        } else {

                                            HashMap<String, Object> addProduct = new HashMap<>();

                                            addProduct.put("product_ID_" + String.valueOf(DBqueries.cartList.size()), docPath);
                                            addProduct.put("list_size", (long) (DBqueries.cartList.size() + 1));

                                            firebaseFirestore.collection("USERS").document(currentUser.getUid())
                                                    .collection("USER_DATA").document("MY_CARTLIST")
                                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {


                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {

                                                        if (DBqueries.cartItemModelList.size() != 0) {

                                                            DBqueries.cartItemModelList.add(0, new CartItemModel(CartItemModel.CART_ITEM, docPath, documentSnapshot.get("product_image_1").toString(),
                                                                    documentSnapshot.get("product_title").toString(),
                                                                    documentSnapshot.get("product_price").toString(),
                                                                    documentSnapshot.get("product_cuttedPrice").toString(),
                                                                    (long) documentSnapshot.get("freeCoupons"),
                                                                    (long) 1, (long) 0, (long) 0,
                                                                    (boolean) documentSnapshot.get("in_stock")));

                                                        }
                                                        alreadyAddedToCart = true;
                                                        Toast.makeText(ProductDetailsActivity.this, "Added to Cart successsfully", Toast.LENGTH_SHORT).show();

                                                        DBqueries.cartList.add(docPath);

                                                        invalidateOptionsMenu();        ////to refresh action view and get corretc count of items;

                                                        runCartQuery = false;

                                                    } else {

                                                        runCartQuery = false;
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                                                    }
                                                }

                                            });


                                        }
                                    }

                                }

                            }
                        });

                        ///cart button only works when product is in stock

                    } else {

                        buyNNowButton.setVisibility(View.GONE);
                        TextView textView = (TextView) addToCartButtonLinearLayout.getChildAt(0);
                        textView.setText("Out of Stock");
                        textView.setTextColor(getResources().getColor(R.color.colorRed));
                        textView.setCompoundDrawables(null, null, null, null);


                    }

                } else {

                    progressDialog.dismiss();
                    String error = task.getException().getMessage();
                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                }

            }
        });

        viewPagerItems.setupWithViewPager(productImagesViewPager, true);

        addToWishlistFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {

                    //addToWishlistFloatingButton.setEnabled(false);

                    if (!runWishlistQuery) {

                        runWishlistQuery = true;

                        if (alreadyAddedToWishlist) {

                            int index = DBqueries.wishlist.indexOf(docPath);
                            DBqueries.removeFromWishlist(index, ProductDetailsActivity.this);
                            addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1ACAC")));
                            Toast.makeText(ProductDetailsActivity.this, "Removed from Wishlist", Toast.LENGTH_SHORT).show();

                        } else {

                            HashMap<String, Object> addProduct = new HashMap<>();

                            addProduct.put("product_ID_" + String.valueOf(DBqueries.wishlist.size()), docPath);
                            addProduct.put("list_size", (long) (DBqueries.wishlist.size() + 1));

                            firebaseFirestore.collection("USERS").document(currentUser.getUid())
                                    .collection("USER_DATA").document("MY_WISHLIST")
                                    .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {


                                        if (DBqueries.wishListModelList.size() != 0) {

                                            DBqueries.wishListModelList.add(new WishListModel(docPath, documentSnapshot.get("product_image_1").toString(),
                                                    documentSnapshot.get("product_title").toString(),
                                                    (long) documentSnapshot.get("freeCoupons"),
                                                    documentSnapshot.get("average_rating").toString(),
                                                    (long) documentSnapshot.get("total_ratings"),
                                                    documentSnapshot.get("product_price").toString(),
                                                    documentSnapshot.get("product_cuttedPrice").toString(),
                                                    (boolean) documentSnapshot.get("COD")));

                                        }

                                        alreadyAddedToWishlist = true;
                                        addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE91E63")));
                                        Toast.makeText(ProductDetailsActivity.this, "Added to WishList", Toast.LENGTH_SHORT).show();

                                        DBqueries.wishlist.add(docPath);


                                    } else {

                                        addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1ACAC")));

                                        String error = task.getException().getMessage();
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();

                                    }
                                    runWishlistQuery = false;
                                }
                            });


                        }
                    }

                } else {
                    signInDialog.show();
                }
            }
        });


        productDetaileViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetaileViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //////rating layout


        for (int i = 0; i < yourRatingLinearLayout.getChildCount(); i++) {

            final int starPosition = i;

            yourRatingLinearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (currentUser == null) {

                        signInDialog.show();

                    } else {

                        if (runRatingQuery != true) {

                            runRatingQuery = true;
                            setRating(starPosition);

                            Map<String, Object> updateRating = new HashMap<>();

                            if (DBqueries.myRatedProductIDs.contains(docPath)) {

                                long previousStar = (long) documentSnapshot.get((initialRating + 1) + "_star");
                                updateRating.put((initialRating + 1) + "_star", previousStar - 1);
                                long currentStar = (long) documentSnapshot.get(starPosition + 1 + "_star");
                                updateRating.put(starPosition + 1 + "_star", currentStar + 1);
                                updateRating.put("average_rating", String.valueOf(calculateAverageRating((long) starPosition + 1)));


                            } else {

                                long ratedStar = (long) documentSnapshot.get(starPosition + 1 + "_star");
                                updateRating.put(starPosition + 1 + "_star", ratedStar + 1);
                                String average = String.valueOf(calculateAverageRating((long) (starPosition + 1)));
                                updateRating.put("average_rating", average);
                                long total = (long) documentSnapshot.get("total_ratings");
                                updateRating.put("total_ratings", total + 1);

                            }

                            DocumentReference docRef = firebaseFirestore.collection("Products").document(docPath);

                            docRef.update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Log.d("Success", "Successful");

                                    } else {
                                        String error = task.getException().toString();
                                        Log.d("error", "" + error);
                                        Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }


                    }
                }
            });

        }

        //////rating layout

        ///// buy now

        buyNNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                if (currentUser != null) {

                    DeliveryActivity.cartItemModelList = new ArrayList<>();

                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.CART_ITEM, docPath, documentSnapshot.get("product_image_1").toString(),
                            documentSnapshot.get("product_title").toString(),
                            documentSnapshot.get("product_price").toString(),
                            documentSnapshot.get("product_cuttedPrice").toString(),
                            (long) documentSnapshot.get("freeCoupons"),
                            (long) 1, (long) 0, (long) 0,
                            (boolean) documentSnapshot.get("in_stock")));

                    DeliveryActivity.cartItemModelList.add(new CartItemModel(CartItemModel.TOTAL_AMOUNT));


                    if (DBqueries.myAddressesModelList.size() == 0){

                        DBqueries.loadAddresses(ProductDetailsActivity.this, progressDialog);

                    }else{

                        progressDialog.dismiss();

                        Intent deliveryIntent = new Intent(ProductDetailsActivity.this, DeliveryActivity.class);
                        startActivity(deliveryIntent);

                    }
                } else {
                    signInDialog.show();
                }
            }
        });


        ///// buy now;

        couponRedemptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog couponRedeemDialog = new Dialog(ProductDetailsActivity.this);
                couponRedeemDialog.setContentView(R.layout.coupon_redeem_dialog_layout);
                couponRedeemDialog.setCancelable(true);

                couponRedeemDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                ImageView dialogShowCouponImageView = couponRedeemDialog.findViewById(R.id.dialogShowCouponImageView);
                dialogCouponRecyclerView = couponRedeemDialog.findViewById(R.id.dialogCouponRecyclerView);
                showSelectedCouponLinearLayout = couponRedeemDialog.findViewById(R.id.showSelectedCouponLinearLayout);

                dialogCouponTitle = couponRedeemDialog.findViewById(R.id.rewardDiscountCouponTextView);
                dialogCouponDate = couponRedeemDialog.findViewById(R.id.rewardCouponValidityTextView);
                dialogCouponBody = couponRedeemDialog.findViewById(R.id.rewardBodyTextView);

                TextView dialogOriginalPrice = couponRedeemDialog.findViewById(R.id.dialogOriginalPriceTextView);
                TextView dialogDiscountPrice = couponRedeemDialog.findViewById(R.id.dialogDiscountedPriceTextView);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                dialogCouponRecyclerView.setLayoutManager(linearLayoutManager);

                List<RewardsModel> rewardsModelList = new ArrayList<>();

                rewardsModelList.add(new RewardsModel("Cashback", "25 Jul,2020", "Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
                rewardsModelList.add(new RewardsModel("Dicount", "25 Jul,2020", "Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
                rewardsModelList.add(new RewardsModel("Buy 1 get 1 free", "25 Jul,2020", "Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
                rewardsModelList.add(new RewardsModel("Cashback", "25 Jul,2020", "Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
                rewardsModelList.add(new RewardsModel("Discount", "25 Jul,2020", "Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
                rewardsModelList.add(new RewardsModel("Cashback", "25 Jul,2020", "Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
                rewardsModelList.add(new RewardsModel("Buy 1 get 1 free", "25 Jul,2020", "Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));
                rewardsModelList.add(new RewardsModel("Cashback", "25 Jul,2020", "Get 20% Cashback on any product above Rs 4000/- and below Rs 5000/-"));

                RewardsAdapter rewardsAdapter = new RewardsAdapter(rewardsModelList, true);
                dialogCouponRecyclerView.setAdapter(rewardsAdapter);

                rewardsAdapter.notifyDataSetChanged();

                dialogShowCouponImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialogrecyclerView();
                    }
                });

                couponRedeemDialog.show();
            }
        });

        ///////sign in dialog

        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog_layout);
        signInDialog.setCancelable(true);

        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInButton = signInDialog.findViewById(R.id.dialogSignInButton);
        Button dialogSignUpButton = signInDialog.findViewById(R.id.dialogSignUpButton);

        final Intent registerIntent = new Intent(ProductDetailsActivity.this, LoginActivity.class);


        dialogSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SigninFragment.disableCloseButton = true;
                SignupFragment.disableCloseButton = true;
                signInDialog.dismiss();
                setSignUpFragment = false;
                startActivity(registerIntent);
            }
        });

        dialogSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupFragment.disableCloseButton = true;
                SigninFragment.disableCloseButton = true;
                signInDialog.dismiss();
                setSignUpFragment = true;
                startActivity(registerIntent);
            }
        });

        //////sign in dialog;


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {

            couponRedemptionLinearLayout.setVisibility(View.GONE);

        } else {

            couponRedemptionLinearLayout.setVisibility(View.VISIBLE);

        }

        if (currentUser != null) {

            if (DBqueries.myRating.size() == 0) {

                DBqueries.loadRatings(ProductDetailsActivity.this);

            }

            if (DBqueries.wishlist.size() == 0) {

                DBqueries.loadWishlist(ProductDetailsActivity.this, progressDialog, false);

            } else {

                progressDialog.dismiss();

            }

        } else {
            progressDialog.dismiss();
        }

        if (DBqueries.myRatedProductIDs.contains(docPath)) {

            int index = DBqueries.myRatedProductIDs.indexOf(docPath);
            initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index))) - 1;

            setRating(initialRating);

        }

        if (DBqueries.cartList.contains(docPath)) {

            alreadyAddedToCart = true;

        } else {

            alreadyAddedToCart = false;

        }

        if (DBqueries.wishlist.contains(docPath)) {

            alreadyAddedToWishlist = true;
            addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE91E63")));

        } else {

            alreadyAddedToWishlist = false;
            addToWishlistFloatingButton.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1ACAC")));

        }

        invalidateOptionsMenu();

    }

    public static void showDialogrecyclerView() {

        if (dialogCouponRecyclerView.getVisibility() == View.GONE) {

            dialogCouponRecyclerView.setVisibility(View.VISIBLE);
            showSelectedCouponLinearLayout.setVisibility(View.GONE);

        } else {

            dialogCouponRecyclerView.setVisibility(View.GONE);
            showSelectedCouponLinearLayout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart, menu);

        cartItem = menu.findItem(R.id.mainCartIcon);


        MenuItem cartItem = menu.findItem(R.id.mainCartIcon);

        cartItem.setActionView(R.layout.badge_layout);

        badgeCount = (TextView) cartItem.getActionView().findViewById(R.id.badgeCount);

        if (currentUser != null) {

            if (DBqueries.cartList.size() == 0) {

                DBqueries.loadCartItem(ProductDetailsActivity.this, progressDialog, false, badgeCount, new TextView(ProductDetailsActivity.this));

            } else {

                badgeCount.setVisibility(View.VISIBLE);

                if (DBqueries.cartList.size() < 99) {
                    badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                } else {
                    badgeCount.setText("99");
                }
            }

        }

        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentUser != null) {

                    Intent cartIntent = new Intent(ProductDetailsActivity.this, StarterActivity.class);
                    showCart = true;
                    startActivity(cartIntent);

                } else {

                    signInDialog.show();

                }

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mainSerachIcon) {

            return true;

        } else if (id == android.R.id.home) {
            finish();
            return true;

        } else if (id == R.id.mainCartIcon) {

            if (currentUser != null) {

                Intent cartIntent = new Intent(ProductDetailsActivity.this, StarterActivity.class);
                showCart = true;
                startActivity(cartIntent);
                return true;

            } else {

                signInDialog.show();

            }

        }

        return super.onOptionsItemSelected(item);
    }

    ////rating

    public static void setRating(int starPosition) {

        for (int i = 0; i < yourRatingLinearLayout.getChildCount(); i++) {

            ImageView starImageView = (ImageView) yourRatingLinearLayout.getChildAt(i);
            starImageView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#CDC8C8")));

            if (i <= starPosition) {

                starImageView.setImageTintList(ColorStateList.valueOf(Color.parseColor("#F4E87E")));
            }

        }

    }

    private float calculateAverageRating(long currentUserRating) {

        long totalStars = 0;

        for (int i = 0; i < 5; i++) {

            TextView textView = (TextView) yourRatingLinearLayout.getChildAt(i);

            totalStars = totalStars + Long.valueOf(textView.getText().toString());

        }

        totalStars += currentUserRating;

        long averageRating = totalStars / (((long) documentSnapshot.get("total_ratings")) + 1);

        return averageRating;

    }

    ////rating

}
