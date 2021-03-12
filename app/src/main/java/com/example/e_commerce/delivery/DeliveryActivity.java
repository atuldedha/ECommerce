package com.example.e_commerce.delivery;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;
import com.example.e_commerce.cart_fragment.CartAdapter;
import com.example.e_commerce.cart_fragment.CartItemModel;
import com.example.e_commerce.my_addresses.MyAddressesActivity;
import com.example.e_commerce.otp.OTPverificationActivity;

import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    public static List<CartItemModel> cartItemModelList;

    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddAddressButton;
    public static final int SELECT_ADDRESS=0;

    private TextView totalCartAmountTextView;

    private TextView shippingFullName, shippingAddress, shippingPinCode;

    private Button cartContinueButton;

    private Dialog progressDialog;

    private Dialog paymentMethodDialog;
    private ImageButton paytmImageButton, codImageButton;

    private String name, mobileNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        initWidgets();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        CartAdapter cartAdapter=new CartAdapter(cartItemModelList, totalCartAmountTextView, false);

        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddAddressButton.setVisibility(View.VISIBLE);
        changeOrAddAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressesIntent=new Intent(DeliveryActivity.this, MyAddressesActivity.class);
                addressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(addressesIntent);
            }
        });


        cartContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                paymentMethodDialog.show();


            }
        });

        codImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent codInetent = new Intent(DeliveryActivity.this, OTPverificationActivity.class);
                codInetent.putExtra("mobileNo", mobileNo.substring(0,10));
                startActivity(codInetent);

            }
        });

        /////Paytm functionality INcomplete;

//        paytmImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                paymentMethodDialog.dismiss();
//
//                progressDialog.show();
//
//                if (ContextCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
//                }
//
//            }
//        });

        ///Paytm functionality incomplet

    }

    private void initWidgets(){

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        deliveryRecyclerView=findViewById(R.id.deliveryRecyclerView);
        changeOrAddAddressButton=findViewById(R.id.changeAddAddressButton);

        totalCartAmountTextView = findViewById(R.id.totalCartAmountTextView);

        shippingFullName = findViewById(R.id.shippingFullName);
        shippingAddress = findViewById(R.id.shippingAddress);
        shippingPinCode = findViewById(R.id.shippingPinCode);

        cartContinueButton = findViewById(R.id.cartContinueButton);

        //////loading dialog

        progressDialog = new Dialog(this);
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setCancelable(false);

        progressDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);


        //////loading dialog;

        //////payment dialog

        paymentMethodDialog = new Dialog(this);
        paymentMethodDialog.setContentView(R.layout.payment_method_dialog);
        paymentMethodDialog.setCancelable(true);

        paymentMethodDialog.getWindow().setBackgroundDrawable(this.getDrawable(R.drawable.slider_background));
        paymentMethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        paytmImageButton = paymentMethodDialog.findViewById(R.id.paytmImageButton);
        codImageButton = paymentMethodDialog.findViewById(R.id.codImageButton);

        //////payment dialog;

    }

    @Override
    protected void onStart() {
        super.onStart();

        name = DBqueries.myAddressesModelList.get(DBqueries.selectedAddress).getName();
        mobileNo = DBqueries.myAddressesModelList.get(DBqueries.selectedAddress).getMobileNo();

        shippingFullName.setText(name + " Phone- "+ mobileNo);
        shippingAddress.setText(DBqueries.myAddressesModelList.get(DBqueries.selectedAddress).getAddress());
        shippingPinCode.setText(DBqueries.myAddressesModelList.get(DBqueries.selectedAddress).getPincode());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
