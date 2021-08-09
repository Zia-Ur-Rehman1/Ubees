package com.example.ubees.adapter;

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


import com.bumptech.glide.Glide;
import com.example.ubees.R;
import com.example.ubees.activities.AddtoCart;
import com.example.ubees.activities.Add_Product;
import com.example.ubees.activities.UserActivity;
import com.example.ubees.model.Products;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<Products> mList;
    private Context context;
    ArrayList<String> key;
    String userName= FirebaseAuth.getInstance().getCurrentUser().getUid();


    public ProductAdapter(Context context , ArrayList<Products> mList,ArrayList<String> key){

        this.context = context;
        this.mList = mList;
        this.key=key;
    }


    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        if(userName.equals("qdWQbUI3YhPU2jzmcwHTKMdLDxl1")) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.update.setVisibility(View.VISIBLE);
        }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json=gson.toJson(mList.get(position));
        holder.textView.setText(mList.get(position).getName());
        Glide.with(context).load(mList.get(position).getImgId()).into(holder.imageView);
        holder.price.setText(mList.get(position).getPrice());
        holder.update.setOnClickListener(v -> {
            Intent intent=new Intent(context.getApplicationContext(), Add_Product.class);
            intent.putExtra("key",key.get(position));
            intent.putExtra("Item", json);
            context.startActivity(intent);
            ((UserActivity)context).finish();
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db= FirebaseDatabase.getInstance().getReference("products");
                db.child(key.get(position)).removeValue();
                DatabaseReference db_cart= FirebaseDatabase.getInstance().getReference("cart").child(userName);
                db_cart.child(key.get(position)).removeValue();
                StorageReference s_db= FirebaseStorage.getInstance().getReferenceFromUrl(mList.get(position).getImgId());
                s_db.delete().addOnSuccessListener(unused -> Toast.makeText(context, "Data Removed", Toast.LENGTH_SHORT).show());
                ((UserActivity)context).recreate();

            }
        });
        holder.cardView.setOnClickListener(v -> {
            Intent intent=new Intent(context.getApplicationContext(), AddtoCart.class);
            intent.putExtra("key",key.get(position));
            intent.putExtra("Item", json);
            context.startActivity(intent);
            ((UserActivity)context).finish();
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView textView;
        TextView price;
        ImageView update,delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView =(ImageView) itemView.findViewById(R.id.product_img);
            textView=(TextView) itemView.findViewById(R.id.img_name);
            price=(TextView) itemView.findViewById(R.id.img_price);
            cardView=(CardView) itemView.findViewById(R.id.card_container);
            update=(ImageView) itemView.findViewById(R.id.update_product);
            delete=(ImageView) itemView.findViewById(R.id.delete_product);
        }
    }
}