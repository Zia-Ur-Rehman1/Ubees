package com.example.ubees.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ubees.R;
import com.example.ubees.model.Cart;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{
    ArrayList<Cart> list=new ArrayList<>();
    int price,quantity;
    int total=0;
    Context context;

    public OrderAdapter(Context context,ArrayList<Cart> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.order_item, parent , false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.order_p_name.setText(list.get(position).getProduct_name());
        price= Integer.parseInt((list.get(position).getProduct_price()));
        quantity= Integer.parseInt((list.get(position).getProduct_quantity()));
        total=total+price*quantity;
        holder.order_p_price.setText("Rs: "+quantity*price);
        holder.order_p_quantity.setText(list.get(position).getProduct_quantity());
        if(list.size()==position+1){
            holder.grandTotal.setVisibility(View.VISIBLE);
            holder.totalPrice.setVisibility(View.VISIBLE);
            holder.line.setVisibility(View.VISIBLE);
            holder.totalPrice.setText("Rs: "+total);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrderViewHolder extends  RecyclerView.ViewHolder {
        TextView order_p_name,order_p_price,order_p_quantity,grandTotal,totalPrice;
        View line;
        public OrderViewHolder( View itemView) {
            super(itemView);
            order_p_name=(TextView) itemView.findViewById(R.id.order_item_name);
            order_p_price=(TextView) itemView.findViewById(R.id.order_item_price);
            order_p_quantity=(TextView) itemView.findViewById(R.id.order_item_qty);
            grandTotal=(TextView) itemView.findViewById(R.id.grandTotal);
            totalPrice=(TextView) itemView.findViewById(R.id.totalPrice);
            line=(View) itemView.findViewById(R.id.line1);
        }
    }
}
