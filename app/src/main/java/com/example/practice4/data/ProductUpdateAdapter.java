package com.example.practice4.data;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice4.ProductListFragment;
import com.example.practice4.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ProductUpdateAdapter extends RecyclerView.Adapter<ProductUpdateAdapter.ProductViewHolder>  {

    private List<Product> products;
    public ProductUpdateAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public ProductUpdateAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_update_item, parent, false);
        return new ProductUpdateAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductUpdateAdapter.ProductViewHolder holder, int position) {
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private Product product;
        private Button mDeleteButton;
        private TextView mNameView;
        private EditText mEditPrice;
        private Button mUpdatePriceButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            mDeleteButton = itemView.findViewById(R.id.delete_product_button);
            mNameView = itemView.findViewById(R.id.update_product_name);
            mEditPrice = itemView.findViewById(R.id.update_product_price);
            mUpdatePriceButton = itemView.findViewById(R.id.update_product_button);

            // Create the listener for the update price button
            mUpdatePriceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the updated price
                    final String newPriceString = mEditPrice.getText().toString();
                    double newPrice;
                    try {
                        newPrice = Double.valueOf(newPriceString);
                    }
                    catch (Exception e) {
                        Toast.makeText(itemView.getContext(), "Unable to parse new price",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    // Get the database
                    final DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance()
                            .getReference(ProductListFragment.DATABASE_TITLE);
                    // Update the price of this product, by looking up its ID
                    firebaseDatabase.child(product.getId()).child("price").setValue(newPrice);
                    Toast.makeText(itemView.getContext(), "Price updated successfully",
                            Toast.LENGTH_LONG).show();
                }
            });

            // Create the listener for the delete button
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Ask the user for confirmation that they really want to delete
                    final AlertDialog.Builder builder =
                            new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle("Confirmation Needed");
                    builder.setMessage("Are you sure you want to delete " + product.getName());
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Really delete it
                            deleteProduct();
                            return;
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Don't delete it
                            return;
                        }
                    });
                    builder.show();
                }
            });
        }

        public void bind(Product product) {
            this.product = product;
            mNameView.setText(product.getName());
            mEditPrice.setText(String.valueOf(product.getPrice()));
        }

        public void deleteProduct() {
            // Get the database
            final DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance()
                    .getReference(ProductListFragment.DATABASE_TITLE);
            // Find the item in the database using its ID, then delete it
            firebaseDatabase.child(product.getId()).removeValue();
            Toast.makeText(itemView.getContext(), "Product Deleted Successfully",
                    Toast.LENGTH_LONG).show();

            // Remove the product from this recycler view as well
            final int position = getAdapterPosition();
            products.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, products.size());
        }
    }
}
