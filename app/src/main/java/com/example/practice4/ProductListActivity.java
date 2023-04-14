package com.example.practice4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.practice4.data.Product;
import com.example.practice4.data.ProductAdapter;
import com.example.practice4.data.ProductSpacingDecorator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private final static String DATABASE_TITLE = "Products";

    private RecyclerView mRecyclerView;
    private DatabaseReference firebaseDatabase;
    private ProductAdapter productAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        mRecyclerView = findViewById(R.id.product_recycler_view);
        mRecyclerView.addItemDecoration(new ProductSpacingDecorator(0));
        layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        firebaseDatabase = FirebaseDatabase.getInstance().getReference(DATABASE_TITLE);

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final List<Product> productList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    final Product product = dataSnapshot.getValue(Product.class);
                    productList.add(product);
                }
                productAdapter = new ProductAdapter(productList);
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