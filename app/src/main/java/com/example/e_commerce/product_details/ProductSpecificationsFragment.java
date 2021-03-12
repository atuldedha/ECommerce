package com.example.e_commerce.product_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerce.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductSpecificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductSpecificationsFragment extends Fragment {

    public List<ProductSpecificationModel> productSpecificationModelList;

    private RecyclerView productSpecificationFragmentRecyclerView;

    public ProductSpecificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_product_specifications, container, false);

       productSpecificationFragmentRecyclerView=view.findViewById(R.id.productSpecificationFragmentRecyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productSpecificationFragmentRecyclerView.setLayoutManager(linearLayoutManager);


        /*productSpecificationModelList.add(new ProductSpecificationModel("Ram","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel("Ram","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel("Ram","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel("Ram","4GB"));
        productSpecificationModelList.add(new ProductSpecificationModel("Ram","4GB"));
*/
        ProductSpecificationAdapter productSpecificationAdapter=new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationAdapter.notifyDataSetChanged();
        productSpecificationFragmentRecyclerView.setAdapter(productSpecificationAdapter);
        return view;
    }
}
