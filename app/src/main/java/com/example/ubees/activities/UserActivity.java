package com.example.ubees.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
    ProductAdapter adapter;
    FirebaseAuth firebaseAuth;
    ImageView cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        firebaseAuth=FirebaseAuth.getInstance();
            cart=findViewById(R.id.cart);
            cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(UserActivity.this,Cart_Activity.class);
                    UserActivity.this.startActivity(intent);
                }
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
                    }
                    setView();
                }
                else{
                    Toast.makeText(UserActivity.this,"Database Error",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void setView() {
        if(list.toString().trim().length()>0){
            adapter=new ProductAdapter(this,list);
            recyclerView=findViewById(R.id.recyle_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
}