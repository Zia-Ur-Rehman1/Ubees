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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Products> list = new ArrayList<>();
    ProductAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fetchData();


    }

    private void fetchData() {
        DatabaseReference root = FirebaseDatabase.getInstance().getReference("products");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        root.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot s : task.getResult().getChildren()) {
                        list.add(s.getValue(Products.class));
                    }
                    setfirebase();
                }
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