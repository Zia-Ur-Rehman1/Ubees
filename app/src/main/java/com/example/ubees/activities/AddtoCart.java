package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class AddtoCart extends AppCompatActivity {
    ImageButton inc,dec;
    ImageView imageView,back;
    Button add;
    Cart cart = new Cart();
    String user_name = FirebaseAuth.getInstance().getCurrentUser().getUid();
    ArrayList<Cart> carts=new ArrayList<>();
    TextView name,price,desc,quantity;
    DatabaseReference db_ref;
    int counter=0;
    Products products;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addto_cart);
        String data= getIntent().getStringExtra("Item");
        key= getIntent().getStringExtra("key");

        Gson gson = new Gson();
        products=gson.fromJson(data,Products.class);
        back=findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Intent intent=new Intent(AddtoCart.this,UserActivity.class);
           startActivity(intent);
            finish();
        });
        quantity = findViewById(R.id.quantity);
        db_ref=FirebaseDatabase.getInstance().getReference("cart").child(user_name);
        db_ref.get().addOnCompleteListener(task -> {
            for (DataSnapshot s: task.getResult().getChildren()){
                if(key.equals(s.getKey())){
                 cart=s.getValue(Cart.class);
                 quantity.setText(cart.getProduct_quantity());
                    return;
                }
            }
            quantity.setText(""+counter);
            return;
        });
        setup_views();

    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(AddtoCart.this,UserActivity.class);
        AddtoCart.this.startActivity(intent);
        finish();
    }

    private void setup_views() {
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        desc=findViewById(R.id.desc);
        imageView=findViewById(R.id.add_to_cart_img);
        inc=findViewById(R.id.btnadd);
        dec=findViewById(R.id.btnsub);
        name.setText(products.getName());
        price.setText("Rs "+products.getPrice());
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
                Cart cart=new Cart (products.getImgId(),products.getName(),quantity.getText().toString(),products.getPrice());
                db_ref.child(key).setValue(cart);
                Intent intent=new Intent(AddtoCart.this,UserActivity.class);
                AddtoCart.this.startActivity(intent);
                finish();
            }
        });
    }
}