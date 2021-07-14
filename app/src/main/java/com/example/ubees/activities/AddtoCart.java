package com.example.ubees.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ubees.R;
import com.example.ubees.model.Cart;
import com.example.ubees.model.Products;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class AddtoCart extends AppCompatActivity {
    ImageButton inc,dec;
    ImageView imageView;
    Button add;
    String user_name;
    TextView name,price,desc,quantity;
    int counter=0;
    Products products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);
        String data= getIntent().getStringExtra("Item");
        user_name= getIntent().getStringExtra("User");
        Gson gson = new Gson();
        products=gson.fromJson(data,Products.class);
        setup_views();
        setup_firebase();
    }

    private void setup_firebase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("cart");

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
        add=findViewById(R.id.add_cart);

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
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference db_ref=FirebaseDatabase.getInstance().getReference("cart");
                Cart cart=new Cart(products.getName(),quantity.getText().toString(),products.getPrice());
                db_ref.child(user_name).push().setValue(cart);
            }
        });
    }
}