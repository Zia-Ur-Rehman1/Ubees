package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ubees.R;
import com.example.ubees.model.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Cart_Activity extends AppCompatActivity {
    String user_name;
    ArrayList<Cart> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if(getIntent().getExtras()!=null){
            user_name=getIntent().getStringExtra("User");
        }
        DatabaseReference db_ref= FirebaseDatabase.getInstance().getReference("cart").child(user_name);
        db_ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete( Task<DataSnapshot> task) {
                for(DataSnapshot s: task.getResult().getChildren()) {
                    list.add(s.getValue(Cart.class));
                }
                for(Cart l:list){
                    Toast.makeText(Cart_Activity.this,l.getProduct_name(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}