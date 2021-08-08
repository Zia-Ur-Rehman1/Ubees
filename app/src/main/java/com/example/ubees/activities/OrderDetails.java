package com.example.ubees.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ubees.R;

public class OrderDetails extends AppCompatActivity {
    Button viewOrder,updateOrder,confirmOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        viewOrder=findViewById(R.id.viewOrder);
        updateOrder=findViewById(R.id.updateOrder);
        confirmOrder=findViewById(R.id.confirmOrder);
        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this,Cart_Activity.class);
                startActivity(intent);

            }
        });
        updateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this,UserActivity.class);
                startActivity(intent);

            }
        });
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetails.this,Order.class);
                startActivity(intent);
            }
        });
    }
}