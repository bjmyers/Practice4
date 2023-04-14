package com.example.practice4.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice4.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final Product product = products.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameView;
        private TextView mPriceView;
        private TextView mLocationView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameView = itemView.findViewById(R.id.product_name_view);
            mPriceView = itemView.findViewById(R.id.product_price_view);
            mLocationView = itemView.findViewById(R.id.product_location_view);
        }

        public void bind(Product product) {
            mNameView.setText("Product: "+ product.getName());
            mPriceView.setText("Price (units per ounce): "+ product.getPrice());
            mLocationView.setText("Location: "+ product.getLocation());
        }
    }

}
