package com.example.ubees;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.BreakIterator;
import java.util.ArrayList;
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private ArrayList<Products> mList;
    private Context context;

    public ProductAdapter(Context context , ArrayList<Products> mList){

        this.context = context;
        this.mList = mList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent , false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).getName());
        Glide.with(context).load(mList.get(position).getImgId()).into(holder.imageView);

        holder.price.setText(mList.get(position).getPrice()+"");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json=gson.toJson(mList.get(position));
                Intent intent=new Intent(context.getApplicationContext(),AddtoCart.class);
                intent.putExtra("Item", json);
                context.startActivity(intent);
            }
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            textView=(TextView) itemView.findViewById(R.id.img_name);
            price=(TextView) itemView.findViewById(R.id.img_price);
            cardView=(CardView) itemView.findViewById(R.id.card_container);
        }
    }
}