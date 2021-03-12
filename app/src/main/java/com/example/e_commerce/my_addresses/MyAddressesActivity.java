package com.example.e_commerce.my_addresses;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;
import com.example.e_commerce.add_address.AddAddressActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static com.example.e_commerce.delivery.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {

    private RecyclerView myAddressesRecyclerView;
    static MyAddressesAdapter myAddressesAdapter;
    private Button deliverHereButton;

    private LinearLayout addNewAddressLinearLayout;

    private TextView savedAddressesTextView;

    private int previousAddress;

    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");

        //////loading dialog

        progressDialog = new Dialog(this);
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setCancelable(false);

        progressDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        //////loading dialog;

        addNewAddressLinearLayout = findViewById(R.id.addNewAddressLinearLayout);

        myAddressesRecyclerView=findViewById(R.id.addressesRecyclerView);

        deliverHereButton=findViewById(R.id.deliverHereButton);

        savedAddressesTextView = findViewById(R.id.savedAddressesTextView);

        previousAddress = DBqueries.selectedAddress;

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        myAddressesRecyclerView.setLayoutManager(linearLayoutManager);

        int mode=getIntent().getIntExtra("MODE",-1);

        if(mode==SELECT_ADDRESS){
            deliverHereButton.setVisibility(View.VISIBLE);
        }else{
            deliverHereButton.setVisibility(View.INVISIBLE);
        }

        deliverHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBqueries.selectedAddress != previousAddress){

                    final int previousAddressIndex = previousAddress;

                    progressDialog.show();

                    Map<String, Object> updateSelection = new HashMap<>();
                    updateSelection.put("selected_"+ String.valueOf(previousAddress+1), false);
                    updateSelection.put("selected_"+ String.valueOf(DBqueries.selectedAddress+1), true);

                    previousAddress = DBqueries.selectedAddress;

                    FirebaseFirestore.getInstance().collection("USERS")
                            .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("USER_DATA").document("MY_ADDRESSES")
                            .update(updateSelection).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                finish();

                            }else{

                                previousAddress = previousAddressIndex;

                                String error = task.getException().getMessage();
                                Toast.makeText(MyAddressesActivity.this, error, Toast.LENGTH_SHORT).show();

                            }

                            progressDialog.dismiss();
                        }
                    });

                }else{
                    finish();
                }

            }
        });

        myAddressesAdapter=new MyAddressesAdapter(DBqueries.myAddressesModelList, mode);

        myAddressesRecyclerView.setAdapter(myAddressesAdapter);

        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        myAddressesAdapter.notifyDataSetChanged();

        addNewAddressLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addAddressIntent = new Intent(MyAddressesActivity.this, AddAddressActivity.class);
                addAddressIntent.putExtra("INTENT","addAddressIntent");
                startActivity(addAddressIntent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        savedAddressesTextView.setText(String.valueOf(DBqueries.myAddressesModelList.size())+" saves Addresses");

    }

    public static void refreshItems(int deselect, int select){

        myAddressesAdapter.notifyItemChanged(deselect);
        myAddressesAdapter.notifyItemChanged(select);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            if(DBqueries.selectedAddress != previousAddress){

                DBqueries.myAddressesModelList.get(DBqueries.selectedAddress).setSelected(false);

                DBqueries.myAddressesModelList.get(previousAddress).setSelected(true);

                DBqueries.selectedAddress = previousAddress;

            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if(DBqueries.selectedAddress != previousAddress){

            DBqueries.myAddressesModelList.get(DBqueries.selectedAddress).setSelected(false);

            DBqueries.myAddressesModelList.get(previousAddress).setSelected(true);

            DBqueries.selectedAddress = previousAddress;

        }
        super.onBackPressed();
    }
}
