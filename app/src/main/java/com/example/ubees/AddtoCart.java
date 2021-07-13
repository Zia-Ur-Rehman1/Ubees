package com.example.ubees;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

public class AddtoCart extends AppCompatActivity {
    Button add,sub;
    ImageView imageView;
    TextView name,price,desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);
        String quizData= getIntent().getStringExtra("Item");
        Gson gson = new Gson();
        Products products=gson.fromJson(quizData,Products.class);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        desc=findViewById(R.id.desc);
        Log.i("ZIA", "onCreate: "+products);
        name.setText(products.getName());
        price.setText(products.getPrice());
        desc.setText(products.getDesc());
    }
}