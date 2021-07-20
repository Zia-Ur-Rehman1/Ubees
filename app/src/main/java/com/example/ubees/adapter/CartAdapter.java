package com.example.ubees.adapter;

import com.bumptech.glide.Glide;
import com.example.ubees.R;
import com.example.ubees.model.Cart;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    ArrayList<Cart> list=new ArrayList<>();
    Context context;

    public CartAdapter(Context context,ArrayList<Cart> list) {
        this.list = list;
        this.context = context;
    }


    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent , false);

        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.cart_p_name.setText(list.get(position).getProduct_name());
        holder.cart_p_price.setText(list.get(position).getProduct_price());
        holder.cart_p_quantity.setText(list.get(position).getProduct_quantity());
        Glide.with(context).load(list.get(position).getImgId()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CartViewHolder extends  RecyclerView.ViewHolder {
        TextView cart_p_name,cart_p_price,cart_p_quantity;
        ImageView imageView;
        public CartViewHolder( View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.cart_item_img);
            cart_p_name=(TextView) itemView.findViewById(R.id.cart_item_name);
            cart_p_price=(TextView) itemView.findViewById(R.id.cart_item_price);
            cart_p_quantity=(TextView) itemView.findViewById(R.id.cart_quantity);
        }
    }
}
