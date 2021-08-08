package com.example.ubees.adapter;

import com.bumptech.glide.Glide;
import com.example.ubees.R;
import com.example.ubees.activities.AddtoCart;
import com.example.ubees.activities.Cart_Activity;
import com.example.ubees.activities.UserActivity;
import com.example.ubees.model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    ArrayList<Cart> list=new ArrayList<>();
    ArrayList<String> key=new ArrayList<>();
    Context context;
    String userName= FirebaseAuth.getInstance().getCurrentUser().getUid();
    public CartAdapter(Context context,ArrayList<Cart> list,ArrayList<String> key) {
        this.list = list;
        this.context = context;
        this.key=key;

    }


    public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent , false);

        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CartViewHolder holder, int position) {
        holder.cart_p_name.setText(list.get(position).getProduct_name());
        holder.cart_p_price.setText("Rs:"+list.get(position).getProduct_price());
        holder.cart_p_quantity.setText(list.get(position).getProduct_quantity());
        Glide.with(context).load(list.get(position).getImgId()).into(holder.imageView);
        holder.delete.setOnClickListener(v -> {
            DatabaseReference db= FirebaseDatabase.getInstance().getReference("cart").child(userName);
            db.child(key.get(position)).removeValue();
            ((Cart_Activity)context).recreate();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CartViewHolder extends  RecyclerView.ViewHolder {
        TextView cart_p_name,cart_p_price,cart_p_quantity;
        CardView cardView;

        ImageView imageView,delete;
        public CartViewHolder( View itemView) {
            super(itemView);
            imageView =(ImageView) itemView.findViewById(R.id.cart_item_img);
            delete =(ImageView) itemView.findViewById(R.id.delete_item);
            cart_p_name=(TextView) itemView.findViewById(R.id.cart_item_name);
            cardView=(CardView) itemView.findViewById(R.id.cart_card);
            cart_p_price=(TextView) itemView.findViewById(R.id.cart_item_price);
            cart_p_quantity=(TextView) itemView.findViewById(R.id.cart_quantity);
        }
    }
}
