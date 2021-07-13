package com.example.ubees;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<Products> list = new ArrayList<>();
    private ProductAdapter adapter;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("products");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fetchData();
        setfirebase();


    }

    private void fetchData() {
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                for(DataSnapshot s: snapshot.getChildren()){
                    list.add(s.getValue(Products.class));
                }

            }
            @Override
            public void onCancelled( DatabaseError error) {

            }
        });

    }

    private void setfirebase() {
        if(list.toString().trim().length()>0){
            adapter=new ProductAdapter(this,list);
            recyclerView=findViewById(R.id.recyle_view);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
}