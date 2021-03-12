package com.example.e_commerce.my_addresses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.DBqueries;
import com.example.e_commerce.R;

import java.util.List;

import static com.example.e_commerce.delivery.DeliveryActivity.SELECT_ADDRESS;
import static com.example.e_commerce.my_account.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.e_commerce.my_addresses.MyAddressesActivity.refreshItems;

public class MyAddressesAdapter extends RecyclerView.Adapter<MyAddressesAdapter.ViewHolder> {

    private List<MyAddressesModel> myAddressesModelList;
    private int MODE;
    private int preselectedPosition;

    public MyAddressesAdapter(List<MyAddressesModel> myAddressesModelList,int MODE) {
        this.myAddressesModelList = myAddressesModelList;
        this.MODE=MODE;
        preselectedPosition = DBqueries.selectedAddress;
    }

    @NonNull
    @Override
    public MyAddressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout,parent,false);

       return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAddressesAdapter.ViewHolder holder, int position) {

        String name=myAddressesModelList.get(position).getName();
        String mobileNo = myAddressesModelList.get(position).getMobileNo();
        String address=myAddressesModelList.get(position).getAddress();
        String pincode=myAddressesModelList.get(position).getPincode();
        boolean selected=myAddressesModelList.get(position).isSelected();

        holder.setAddress(name,address,pincode,selected,position, mobileNo);

    }

    @Override
    public int getItemCount() {
        return myAddressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView address;
        private TextView pincode;
        private ImageView selectIconImageBiew;
        private LinearLayout editAddressLinearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.address);
            pincode=itemView.findViewById(R.id.pincode);
            selectIconImageBiew=itemView.findViewById(R.id.selectIconImageView);
            editAddressLinearLayout=itemView.findViewById(R.id.editAddressLinearLayout);

        }

        private void setAddress(String nameText, String addressText, String pincodeText, boolean selected, final int position, String mobileNo){

            name.setText(nameText + " Phone- " + mobileNo);
            address.setText(addressText);
            pincode.setText(pincodeText);

            if(MODE==SELECT_ADDRESS){

                selectIconImageBiew.setImageResource(R.drawable.ic_check_black_24dp);
                if(selected){
                    selectIconImageBiew.setVisibility(View.VISIBLE);
                    preselectedPosition=position;
                }else{
                    selectIconImageBiew.setVisibility(View.INVISIBLE);
                }
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(preselectedPosition!=position){
                            myAddressesModelList.get(position).setSelected(true);
                            myAddressesModelList.get(preselectedPosition).setSelected(false);

                            refreshItems(preselectedPosition,position);

                            preselectedPosition=position;

                            DBqueries.selectedAddress = position;
                        }

                    }
                });

            }else if(MODE==MANAGE_ADDRESS){

                editAddressLinearLayout.setVisibility(View.GONE);
                selectIconImageBiew.setImageResource(R.drawable.ic_more_vert_black_24dp);
                selectIconImageBiew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editAddressLinearLayout.setVisibility(View.VISIBLE);
                        refreshItems(preselectedPosition,preselectedPosition);
                        preselectedPosition=position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        refreshItems(preselectedPosition,preselectedPosition);
                        preselectedPosition=-1;

                    }
                });

            }

        }
    }
}
