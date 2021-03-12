package com.example.e_commerce.add_address;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;
import com.example.e_commerce.delivery.DeliveryActivity;
import com.example.e_commerce.my_addresses.MyAddressesActivity;
import com.example.e_commerce.my_addresses.MyAddressesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    private EditText cityEditText, localityEditText, buildingEditText, pincodeEditText, landmarkEditText, nameEditText, numberEditText, alternateNumberEditText;

    private Spinner stateSpinner;

    private Button saveButton;

    private String[] stateList;
    private String selectedState;

    FirebaseFirestore firebaseFirestore;

    private String fullAddress;

    /////loading dialog

    private Dialog progressDialog;

    /////loading dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a new Address");

        initWidgets();

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, stateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(spinnerAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedState = stateList[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkAllInputs()) {

                    progressDialog.show();

                    Map<String, Object> addAddress = new HashMap<>();

                    fullAddress = buildingEditText.getText().toString() + " " + localityEditText.getText().toString() +
                            " " + landmarkEditText.getText().toString() + " " + cityEditText.getText().toString() + " " + selectedState;

                    addAddress.put("list_size", (long) DBqueries.myAddressesModelList.size() + 1);

                    if(TextUtils.isEmpty(alternateNumberEditText.getText())) {

                        addAddress.put("mobile_no_" + String.valueOf((long) DBqueries.myAddressesModelList.size() + 1), numberEditText.getText().toString());

                    }else{

                        addAddress.put("mbile_no_" + String.valueOf((long) DBqueries.myAddressesModelList.size() + 1), numberEditText.getText().toString() + " or " + alternateNumberEditText.getText().toString());

                    }

                    addAddress.put("fullname_" + String.valueOf((long) DBqueries.myAddressesModelList.size() + 1), nameEditText.getText().toString());
                    addAddress.put("address_" +  String.valueOf((long) DBqueries.myAddressesModelList.size() + 1), fullAddress);
                    addAddress.put("pincode_" +  String.valueOf((long) DBqueries.myAddressesModelList.size() + 1), pincodeEditText.getText().toString());
                    addAddress.put("selected_" +  String.valueOf((long) DBqueries.myAddressesModelList.size() + 1), true);

                    if(DBqueries.myAddressesModelList.size() > 0) {

                        addAddress.put("selected_" + DBqueries.selectedAddress + 1, false);

                    }

                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("USER_DATA").document("MY_ADDRESSES")
                            .update(addAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                if(DBqueries.myAddressesModelList.size() > 0) {

                                    DBqueries.myAddressesModelList.get(DBqueries.selectedAddress).setSelected(false);

                                }

                                if(TextUtils.isEmpty(alternateNumberEditText.getText())) {

                                    DBqueries.myAddressesModelList.add(new MyAddressesModel(nameEditText.getText().toString(),fullAddress, pincodeEditText.getText().toString()
                                            , true, numberEditText.getText().toString()));

                                }else{

                                    DBqueries.myAddressesModelList.add(new MyAddressesModel(nameEditText.getText().toString(),fullAddress, pincodeEditText.getText().toString()
                                            , true, numberEditText.getText().toString() + " or " + alternateNumberEditText.getText().toString()));

                                }


                                if(getIntent().getStringExtra("INTENT").equals("deliveryIntent")) {
                                    Intent deliveryInetent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                                    startActivity(deliveryInetent);
                                }else{
                                    MyAddressesActivity.refreshItems(DBqueries.selectedAddress, DBqueries.myAddressesModelList.size()-1);
                                }

                                DBqueries.selectedAddress = DBqueries.myAddressesModelList.size()-1;

                                finish();


                            } else {

                                String error = task.getException().getMessage();
                                Toast.makeText(AddAddressActivity.this, error, Toast.LENGTH_SHORT).show();
                            }

                            progressDialog.dismiss();
                        }
                    });


                }

            }
        });

    }

    private void initWidgets() {

        saveButton = findViewById(R.id.saveButtton);

        cityEditText = findViewById(R.id.cityEditText);
        localityEditText = findViewById(R.id.localityEditText);
        buildingEditText = findViewById(R.id.buildingEditText);
        pincodeEditText = findViewById(R.id.pincodeEditText);
        landmarkEditText = findViewById(R.id.landmarkEditText);
        nameEditText = findViewById(R.id.nameEditText);
        numberEditText = findViewById(R.id.numberEditText);
        alternateNumberEditText = findViewById(R.id.alternateNumberEditText);

        stateSpinner = findViewById(R.id.stateSpinner);

        firebaseFirestore = FirebaseFirestore.getInstance();

        //////loading dialog

        progressDialog = new Dialog(this);
        progressDialog.setContentView(R.layout.progress_dialog_layout);
        progressDialog.setCancelable(false);

        progressDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //////loading dialog;

        stateList = getResources().getStringArray(R.array.india_states);

    }

    private boolean checkAllInputs() {

        if (!TextUtils.isEmpty(cityEditText.getText())) {
            if (!TextUtils.isEmpty(localityEditText.getText())) {
                if (!TextUtils.isEmpty(buildingEditText.getText())) {
                    if (!TextUtils.isEmpty(pincodeEditText.getText()) && pincodeEditText.getText().length() ==6) {
                        if (!TextUtils.isEmpty(nameEditText.getText())) {
                            if (!TextUtils.isEmpty(numberEditText.getText()) && numberEditText.getText().length() ==10) {
                                return true;
                            } else {
                                numberEditText.requestFocus();
                                Toast.makeText(this, "Please provide a valid mobile number", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                        } else {
                            nameEditText.requestFocus();
                            return false;
                        }
                    } else {
                        pincodeEditText.requestFocus();
                        Toast.makeText(this, "Please provide a valid pincode", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    buildingEditText.requestFocus();
                    return false;
                }
            } else {
                localityEditText.requestFocus();
                return false;
            }
        } else {
            cityEditText.requestFocus();
            return false;
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
