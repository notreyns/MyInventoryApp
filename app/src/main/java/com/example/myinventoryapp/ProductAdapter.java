package com.example.myinventoryapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsViewHolder> {

    private Context mCtx;
    private List<Product> productList;

    public ProductAdapter(Context mCtx, List<Product> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_tasks, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        Product t = productList.get(position);
        holder.textViewName.setText(t.getName());
        holder.textViewPrice.setText(t.getPrice());
        holder.textViewQuantity.setText(t.getQuantity());
        holder.textViewSupplier.setText(t.getSupplier());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName, textViewPrice, textViewQuantity, textViewSupplier;
        //ImageView wallpaper;

        public ProductsViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewSupplier = itemView.findViewById(R.id.textViewSupplier);
            //wallpaper= itemView.findViewById(R.id.no_items_wallpaper);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Product product = productList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, ViewProductActivity.class);
            intent.putExtra("product", product);
            mCtx.startActivity(intent);
        }
    }
}