package com.example.ubees.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubees.R;
import com.example.ubees.model.Cart;
import com.example.ubees.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Order extends AppCompatActivity {
    ArrayList<Cart> list = new ArrayList<>();
    String user_name = FirebaseAuth.getInstance().getCurrentUser().getUid();
    TextView total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        DatabaseReference db=FirebaseDatabase.getInstance().getReference("order").child(user_name);
        total=findViewById(R.id.final_price);
        String price= getIntent().getStringExtra("total");
        total.setText("Total Rs:"+price);
    }
}