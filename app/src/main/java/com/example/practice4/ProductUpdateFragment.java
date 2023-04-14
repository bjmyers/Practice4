package com.example.practice4;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice4.data.Product;
import com.example.practice4.data.ProductAdapter;
import com.example.practice4.data.ProductDetails;
import com.example.practice4.data.ProductSpacingDecorator;
import com.example.practice4.data.ProductUpdateAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductUpdateFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DatabaseReference firebaseDatabase;
    private ProductUpdateAdapter productAdapter;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.product_update_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Setup the recycler view
        mRecyclerView = getView().findViewById(R.id.product_update_recycler_view);
        mRecyclerView.addItemDecoration(new ProductSpacingDecorator(0));
        layoutManager = new LinearLayoutManager(this.getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Load data into the recycler view using the database
        firebaseDatabase = FirebaseDatabase.getInstance().getReference(ProductListFragment.DATABASE_TITLE);
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Product> productList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Need to grab product details and id separately, then combine into a Product
                    final ProductDetails details = dataSnapshot.getValue(ProductDetails.class);
                    final Product product = new Product(dataSnapshot.getKey(), details);
                    productList.add(product);
                }
                productAdapter = new ProductUpdateAdapter(productList);
                mRecyclerView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProductListActivity", "Error retrieving products from database",
                        error.toException());
            }
        });
    }
}

