package com.example.ubees.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubees.R;
import com.example.ubees.adapter.CartAdapter;
import com.example.ubees.adapter.OrderAdapter;
import com.example.ubees.model.Cart;
import com.example.ubees.model.Place_Order;
import com.example.ubees.model.Products;
import com.example.ubees.model.User;
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
    User user;
    String user_name ;
    Place_Order order;
    Button checkout;
    OrderAdapter orderAdapter;
    RecyclerView recyclerView;
    TextView userName,city;
    EditText status;
    EditText refrenceNumber;
    DatabaseReference db_order = FirebaseDatabase.getInstance().getReference("order");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        refrenceNumber=findViewById(R.id.refrence);
        userName=findViewById(R.id.orderUserName);
        city=findViewById(R.id.orderuserCity);
        status=findViewById(R.id.orderStatus);
        checkout=findViewById(R.id.checkout);
        if(getIntent().getStringExtra("key")!=null){
            user_name= getIntent().getStringExtra("key");
            checkout.setVisibility(View.GONE);
            refrenceNumber.setFocusable(false);
            status.setFocusableInTouchMode(true);
        }
        else{
            user_name= FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        checkout.setOnClickListener(v -> {
            Toast.makeText(Order.this,"Payment Confirmation",Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(Order.this,UserActivity.class);
                Place_Order place_order=new Place_Order(refrenceNumber.getText().toString(),"Payment Confirmation");
                db_order.child(user_name).setValue(place_order);
                checkout.setVisibility(View.GONE);
                refrenceNumber.setFocusable(false);

        });
        fetchData();

    }

    private void fetchData() {
        db_order.get().addOnCompleteListener(task -> {
            for (DataSnapshot s : task.getResult().getChildren()) {
                if(s.getKey().equals(user_name)){
                    order=s.getValue(Place_Order.class);
                    break;
                }
            }
        });

//        Set Recycler View
        DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference("cart").child(user_name);
        db_ref.get().addOnCompleteListener(task -> {
            for (DataSnapshot s : task.getResult().getChildren()) {
                list.add(s.getValue(Cart.class));
            }
        });
        DatabaseReference db_user = FirebaseDatabase.getInstance().getReference("users");
        db_user.get().addOnCompleteListener(task -> {
            for (DataSnapshot s : task.getResult().getChildren()) {
                if(s.getKey().equals(user_name)){
                    user=s.getValue(User.class);
                    break;
                }
            }
            setUpView();
        });
    }

    private void setUpView() {
        orderAdapter=new OrderAdapter(this,list);
        recyclerView=findViewById(R.id.orderRecycler);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        userName.setText("User Name\n"+user.getF_name());
        city.setText("City\n"+user.getCity());
        status.setText("Status\n"+order.getStatus());
        if(!order.getRefrence_num().equals("none")){
            checkout.setVisibility(View.GONE);
            refrenceNumber.setFocusable(false);
            refrenceNumber.setText(order.getRefrence_num());

        }
    }


}