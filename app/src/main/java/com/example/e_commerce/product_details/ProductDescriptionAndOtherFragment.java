package com.example.e_commerce.product_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.e_commerce.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDescriptionAndOtherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDescriptionAndOtherFragment extends Fragment {

    private TextView productDescriptionFragmentTextView;

    public String body;


    public ProductDescriptionAndOtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description_and_other, container, false);

        productDescriptionFragmentTextView = view.findViewById(R.id.productDescriptionFragmentTextView);

        productDescriptionFragmentTextView.setText(body);

        return view;
    }
}
