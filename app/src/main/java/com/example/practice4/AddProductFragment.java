package com.example.practice4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practice4.data.Product;
import com.example.practice4.data.ProductDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddProductFragment  extends Fragment {
    private EditText mNameField;
    private EditText mPriceField;
    private EditText mLocationField;
    private Button mAddProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.product_add_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNameField = view.findViewById(R.id.add_product_name);
        mPriceField = view.findViewById(R.id.add_product_price);
        mLocationField = view.findViewById(R.id.add_product_location);
        mAddProduct = view.findViewById(R.id.add_product_button);

        // Add listener for the button
        mAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the values from all of the fields
                final String name = mNameField.getText().toString();
                final String priceString = mPriceField.getText().toString();
                final String location = mLocationField.getText().toString();

                // Convert the price to a double, warning the user if it won't convert
                double price;
                try {
                    price = Double.valueOf(priceString);
                }
                catch (Exception e) {
                    // Failed to parse the price, let the user know
                    Toast.makeText(view.getContext(), "Add Activity Failed, unable to parse price",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                // Change is 0 because this is a new product
                final ProductDetails details = new ProductDetails(name, price, 0.0, location);

                // Want to make sure the product gets put into the database under a unique key
                // Use a UUID to ensure uniqueness
                final String id = UUID.randomUUID().toString();

                // Add the product details to the database
                final DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance()
                        .getReference(ProductListFragment.DATABASE_TITLE);
                firebaseDatabase.child(id).setValue(details);

                // Let the user know that the add was successful
                Toast.makeText(view.getContext(), "Added Product",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
