package com.example.e_commerce.main_screen;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.login_and_register.LoginActivity;
import com.example.e_commerce.R;
import com.example.e_commerce.login_and_register.SigninFragment;
import com.example.e_commerce.login_and_register.SignupFragment;
import com.example.e_commerce.cart_fragment.MyCartFragment;
import com.example.e_commerce.my_account.MyAccountFragment;
import com.example.e_commerce.my_order.MyOrdersFragment;
import com.example.e_commerce.my_rewards.MyRewardsFragment;
import com.example.e_commerce.my_wishlist.MyWishlistFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.e_commerce.login_and_register.LoginActivity.setSignUpFragment;

public class StarterActivity extends AppCompatActivity {

    public static final int HOME_FRAGMENT = 0;
    public static final int CART_FRAGMENT = 1;
    public static final int ORDERS_FRAGMENT = 2;
    public static final int WISHLIST_FRAGMENT = 3;
    public static final int REWARDS_FRAGMENT = 4;
    public static final int ACCOUNT_FRAGMENT = 5;


    private Window window;

    private int currentFragment = -1;

    private FrameLayout mainFrameLayout;
    private Fragment naviFragment;

    NavigationView navigationView;
    public static DrawerLayout drawer;
    Toolbar toolbar;

    private ImageView actionBarLogo;


    private AppBarConfiguration mAppBarConfiguration;

    public static boolean showCart = false;

    private Dialog signInDialog;

    private FirebaseUser currentUser;

    private TextView badgeCount;

    private AppBarLayout.LayoutParams params;
    private int scrollflags;

    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_starter);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        scrollflags = params.getScrollFlags();

        actionBarLogo = findViewById(R.id.actionBarLogo);

        mainFrameLayout = findViewById(R.id.mainFrameLayout);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gallery, R.id.nav_home, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);*/


        signInDialog = new Dialog(StarterActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog_layout);
        signInDialog.setCancelable(true);

        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button dialogSignInButton = signInDialog.findViewById(R.id.dialogSignInButton);
        Button dialogSignUpButton = signInDialog.findViewById(R.id.dialogSignUpButton);

        final Intent registerIntent = new Intent(StarterActivity.this, LoginActivity.class);
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

        try {
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    drawer.closeDrawer(GravityCompat.START);

                    menuItem = item;

                    if (currentUser != null) {

                        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                            @Override
                            public void onDrawerClosed(View drawerView) {
                                super.onDrawerClosed(drawerView);

                                int id = menuItem.getItemId();
                                if (id == R.id.nav_myHome) {

                                    actionBarLogo.setVisibility(View.VISIBLE);

                                    invalidateOptionsMenu();
                                    setFragment(new MainFragment(), HOME_FRAGMENT);


                                } else if (id == R.id.nav_orders) {
                                    goToFragment("My Orders", new MyOrdersFragment(), ORDERS_FRAGMENT);
                                    Toast.makeText(StarterActivity.this, "hey there", Toast.LENGTH_SHORT).show();

                                } else if (id == R.id.nav_rewards) {

                                    goToFragment("My Rewrads", new MyRewardsFragment(), REWARDS_FRAGMENT);

                                } else if (id == R.id.nav_wishlist) {

                                    goToFragment("My WishList", new MyWishlistFragment(), WISHLIST_FRAGMENT);

                                } else if (id == R.id.nav_cart) {

                                    goToFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);


                                } else if (id == R.id.nav_account) {

                                    goToFragment("My Account", new MyAccountFragment(), ACCOUNT_FRAGMENT);

                                } else if (id == R.id.nav_sign_out) {

                                    FirebaseAuth.getInstance().signOut();

                                    DBqueries.clearData();

                                    Intent loginIntent = new Intent(StarterActivity.this, LoginActivity.class);
                                    startActivity(loginIntent);
                                    finish();

                                }

                            }
                        });

                        return true;

                    } else {

                        signInDialog.show();
                        return false;

                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            navigationView.getMenu().getItem(0).setChecked(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (showCart) {
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            goToFragment("My Cart", new MyCartFragment(), -2);
        } else {

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                    drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            setFragment(new MainFragment(), HOME_FRAGMENT);

        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {

            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);

        } else {

            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);

        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.starter, menu);

            MenuItem cartItem = menu.findItem(R.id.mainCartIcon);

            cartItem.setActionView(R.layout.badge_layout);

            badgeCount = (TextView) cartItem.getActionView().findViewById(R.id.badgeCount);

            if (currentUser != null) {

                if (DBqueries.cartList.size() == 0) {

                    DBqueries.loadCartItem(StarterActivity.this, new Dialog(StarterActivity.this), false, badgeCount, new TextView(StarterActivity.this));

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

                    if (currentUser == null) {

                        signInDialog.show();

                    } else {

                        goToFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);

                    }

                }
            });

        }


        return true;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mainSerachIcon) {

            return true;

        } else if (id == R.id.mainNotificationIcon) {
            return true;

        } else if (id == R.id.mainCartIcon) {

            if (currentUser == null) {
                signInDialog.show();
            } else {
                goToFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            }

            return true;

        } else if (id == android.R.id.home) {
            if (showCart) {
                showCart = false;
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {

                if (showCart) {
                    showCart = false;
                    finish();
                } else {
                    actionBarLogo.setVisibility(View.VISIBLE);

                    invalidateOptionsMenu();
                    setFragment(new MainFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }

            }

        }

    }

    private void setFragment(Fragment fragment, int fragmentNo) {

        if (fragmentNo != currentFragment) {

            if (fragmentNo == REWARDS_FRAGMENT) {

                window.setStatusBarColor(Color.parseColor("#5B04B1"));
                toolbar.setBackgroundColor(Color.parseColor("#5B04B1"));

            } else {

                window.setStatusBarColor(Color.parseColor("#A22BB6"));
                toolbar.setBackgroundColor(Color.parseColor("#A22BB6"));

            }

            currentFragment = fragmentNo;

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);

            fragmentTransaction.replace(mainFrameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }


    }

    private void goToFragment(String title, Fragment fragment, int fragmentNo) {
        actionBarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if(fragmentNo==CART_FRAGMENT || showCart){
            navigationView.getMenu().getItem(3).setChecked(true);
            params.setScrollFlags(0);
        }else{
            params.setScrollFlags(scrollflags);
        }
    }


}
