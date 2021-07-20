package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ubees.adapter.ProductAdapter;
import com.example.ubees.R;
import com.example.ubees.model.Products;
import com.example.ubees.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Products> list = new ArrayList<>();
    ArrayList<String> key = new ArrayList<>();
    ProductAdapter adapter;
    ImageView cart,add_product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
            cart=findViewById(R.id.cart);
            add_product=findViewById(R.id.add_product);
            add_product.setOnClickListener(v -> {
                Intent intent=new Intent(UserActivity.this,Add_Product.class);
                UserActivity.this.startActivity(intent);
                recreate();
            });
            cart.setOnClickListener(v -> {
                Intent intent=new Intent(UserActivity.this,Cart_Activity.class);
                UserActivity.this.startActivity(intent);
            });
        fetchData();

    }


    private void fetchData() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference("products");

        root.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot s : task.getResult().getChildren()) {
                        list.add(s.getValue(Products.class));
                        key.add(s.getKey());
                    }
                    UserActivity.this.setView();
                } else {
                    Toast.makeText(UserActivity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    UserActivity.this.fetchData();
                }
            }
        });
    }

    private void setView() {
        if(list.toString().trim().length()>0){
            adapter=new ProductAdapter(this,list,key);
            recyclerView=findViewById(R.id.recyle_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
}