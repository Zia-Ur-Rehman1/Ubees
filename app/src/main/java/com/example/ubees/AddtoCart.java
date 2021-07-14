package com.example.ubees;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class AddtoCart extends AppCompatActivity {
    ImageButton inc,dec;
    ImageView imageView;
    TextView name,price,desc,quantity;
    int counter=0;
    Products products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);
        String quizData= getIntent().getStringExtra("Item");
        Gson gson = new Gson();
        products=gson.fromJson(quizData,Products.class);
        setup_views();
        setup_firebase();
    }

    private void setup_firebase() {
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");

    }

    private void setup_views() {
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        desc=findViewById(R.id.desc);
        imageView=findViewById(R.id.img);
        inc=findViewById(R.id.btnadd);
        dec=findViewById(R.id.btnsub);
        quantity = findViewById(R.id.quantity);
        quantity.setText(counter+"");
        name.setText(products.getName());
        price.setText(products.getPrice());
        desc.setText(products.getDesc());
        Glide.with(this).load(products.getImgId()).into(imageView);
        inc.setOnClickListener(v -> {
            if (Integer.parseInt(quantity.getText().toString())>= 0) {
                counter = Integer.parseInt(quantity.getText().toString());
                counter++;
                quantity.setText(counter + "");
            }
        });
        dec.setOnClickListener(v -> {
            if (Integer.parseInt(quantity.getText().toString()) > 0) {

                counter = Integer.parseInt(quantity.getText().toString());
                counter--;
                quantity.setText(counter + "");
            }
        });

    }
}