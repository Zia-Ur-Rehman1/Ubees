package com.example.ubees.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ubees.R;
import com.example.ubees.activities.Order;
import com.example.ubees.model.Place_Order;

import java.util.ArrayList;

public class PlacedOrderAdapter  extends RecyclerView.Adapter<PlacedOrderAdapter.OrderPlaceViewHolder> {
    Context context;
    ArrayList<Place_Order> list=new ArrayList<Place_Order>();
    ArrayList<String> key= new ArrayList<>();
    public PlacedOrderAdapter(Context context, ArrayList<Place_Order> list,ArrayList<String> key) {
        this.context = context;
        this.list = list;
        this.key=key;
    }

    @NonNull
    @Override
    public OrderPlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.placed_order_item, parent , false);
        return new OrderPlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPlaceViewHolder holder, int position) {
        holder.status.setText(list.get(position).getStatus());
        holder.refNum.setText(list.get(position).getRefrence_num());
        holder.orderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Order.class);
                intent.putExtra("key",key.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class OrderPlaceViewHolder extends  RecyclerView.ViewHolder {
        TextView status,refNum;
        CardView orderCard;
        public OrderPlaceViewHolder( View itemView) {
            super(itemView);
            status=(TextView) itemView.findViewById(R.id.placedOrderStatus);
            refNum=(TextView) itemView.findViewById(R.id.placedRefrenceNumber);
            orderCard=(CardView) itemView.findViewById(R.id.orderCard);
        }
    }
}
