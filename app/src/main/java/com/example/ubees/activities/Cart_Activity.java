package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubees.R;
import com.example.ubees.adapter.CartAdapter;
import com.example.ubees.adapter.ProductAdapter;
import com.example.ubees.model.Cart;
import com.example.ubees.model.Place_Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Iterator;


public class Cart_Activity extends AppCompatActivity {

    String user_name= FirebaseAuth.getInstance().getCurrentUser().getUid();
    ArrayList<Cart> list=new ArrayList<>();
    ArrayList<String> key=new ArrayList<>();
    Button checkout;
    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    TextView total_item,total_qunt,total_price;
    int item,quan,price=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        checkout=findViewById(R.id.checkout);
        total_item=findViewById(R.id.total_item);
        total_qunt=findViewById(R.id.total_quantity);
        total_price=findViewById(R.id.total_price);
        DatabaseReference db_ref= FirebaseDatabase.getInstance().getReference("cart").child(user_name);
        db_ref.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete( Task<DataSnapshot> task) {
                for(DataSnapshot s: task.getResult().getChildren()) {
                    list.add(s.getValue(Cart.class));
                    key.add(s.getKey());
                }
                for(int i=0; i<list.size();i++){
                    item=item+1;
                    quan=quan+Integer.parseInt(list.get(i).getProduct_quantity());
                    price=price+Integer.parseInt(list.get(i).getProduct_price());
                }
                total_item.setText("Items\n"+item);
                total_price.setText("Price\nRs: "+price);
                total_qunt.setText("Quantity"+quan);
                setViews();
            }
        });

        checkout.setOnClickListener(v -> {
            DatabaseReference db= FirebaseDatabase.getInstance().getReference("order");
            Place_Order place_order=new Place_Order(total_price.getText().toString(), "none","incomplete");
            db.child(user_name).setValue(place_order);
            Intent intent=new Intent(Cart_Activity.this,Order.class);
            intent.putExtra("total",total_price.getText().toString());
            Cart_Activity.this.startActivity(intent);
            finish();
        });

    }
    public void setViews(){
        cartAdapter=new CartAdapter(this, list,key);
        recyclerView=findViewById(R.id.cart_recycler);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
//      set recycler view
    }
}