package com.example.productshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.productshop.Product;
import com.example.productshop.R;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Product> {
    public ListAdapter(@NonNull Context context, List<Product> productList) {
        super(context, R.layout.list_item, productList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        Product product = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        ImageView imageView = view.findViewById(R.id.itemIV);
        TextView productNameTV = view.findViewById(R.id.itemNameTV);
        TextView productPriceTV = view.findViewById(R.id.itemPriceTV);

        imageView.setImageBitmap(product.getImage());
        productNameTV.setText(product.getName());
        productPriceTV.setText(product.getPrice());
        return view;
    }
}
