package com.example.practice4.data;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice4.R;

import java.util.List;

/**
 * Adapter for showing products in the price change list
 */
public class PriceChangeAdapter extends RecyclerView.Adapter<PriceChangeAdapter.ProductViewHolder>  {

    private List<Product> products;
    public PriceChangeAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public PriceChangeAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.price_change_item, parent, false);
        return new PriceChangeAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PriceChangeAdapter.ProductViewHolder holder, int position) {
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

        private View itemView;
        private TextView mNameView;
        private TextView mPriceView;
        private TextView mChangeView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mNameView = itemView.findViewById(R.id.change_name_view);
            mPriceView = itemView.findViewById(R.id.change_price_view);
            mChangeView = itemView.findViewById(R.id.price_change_text);
        }

        public void bind(Product product) {
            mNameView.setText("Product: "+ product.getName());
            mPriceView.setText("Price (units per ounce): "+ product.getPrice());

            // Build the string for the price change text, put a percent on the end
            final String priceChangeText = String.format("%.2f", product.getChange()) + "%";
            mChangeView.setText(priceChangeText);
            mChangeView.setTextColor(product.getChange() > 0 ?
                    itemView.getResources().getColor(R.color.green) :
                    itemView.getResources().getColor(R.color.red));
        }
    }
}
