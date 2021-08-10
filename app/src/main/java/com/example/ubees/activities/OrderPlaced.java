package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ubees.R;
import com.example.ubees.adapter.CartAdapter;
import com.example.ubees.adapter.PlacedOrderAdapter;
import com.example.ubees.model.Place_Order;
import com.example.ubees.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderPlaced extends AppCompatActivity {
    ArrayList<Place_Order> list=new ArrayList<>();
    ArrayList<String> key= new ArrayList<>();
    RecyclerView recyclerView;
    PlacedOrderAdapter placedOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        DatabaseReference db= FirebaseDatabase.getInstance().getReference("order");

        db.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(DataSnapshot s: task.getResult().getChildren()) {
                    list.add(s.getValue(Place_Order.class));
                    key.add(s.getKey());
                }
                setUpView();
            }
        });
    }

    private void setUpView() {
        placedOrderAdapter=new PlacedOrderAdapter(this, list, key);
        recyclerView=findViewById(R.id.placedOrder_recycler);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setAdapter(placedOrderAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

}