package com.example.ubees.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.ubees.R;
import com.example.ubees.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    EditText name, city, addr, ph;
    String userName= FirebaseAuth.getInstance().getCurrentUser().getUid();
    String admin= FirebaseAuth.getInstance().getCurrentUser().getEmail();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
    Button edit,update;
    User user;
    ImageButton orderDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        name=findViewById(R.id.profile_name);
        city=findViewById(R.id.profile_city);
        addr=findViewById(R.id.profile_addr);
        ph=findViewById(R.id.profile_ph);
        edit=findViewById(R.id.edit);
        update=findViewById(R.id.update);
        orderDetail=findViewById(R.id.orderDetail);

        fetchData();
        if(admin.equals("ubaidurrehman9898@gmail.com")){
            orderDetail.setVisibility(View.VISIBLE);
        }
        edit.setOnClickListener(v -> {
            name.setFocusableInTouchMode(true);
            city.setFocusableInTouchMode(true);
            addr.setFocusableInTouchMode(true);
            ph.setFocusableInTouchMode(true);
            update.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        });
    update.setOnClickListener(v -> {
    if(!name.getText().toString().equals(user.getUser_name()) ||
        !city.getText().toString().equals(user.getCity())|| !addr.getText().toString().equals(user.getAddress())
            || !ph.getText().toString().equals(user.getPhone())
    ){
        myRef.child(userName).child("user_name").setValue(name.getText().toString());
        myRef.child(userName).child("city").setValue(city.getText().toString());
        myRef.child(userName).child("address").setValue(addr.getText().toString());
        myRef.child(userName).child("phone").setValue(ph.getText().toString());
        Toast.makeText(ProfileActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ProfileActivity.this, UserActivity.class);
        ProfileActivity.this.startActivity(intent);
        finish();
        }
    });
    orderDetail.setOnClickListener(v -> {
        Intent intent =new Intent(ProfileActivity.this, OrderPlaced.class);
        startActivity(intent);
        finish();
    });
    }


    @Override
    public void onBackPressed(){
        Intent intent=new Intent(ProfileActivity.this, UserActivity.class);
        ProfileActivity.this.startActivity(intent);
        finish();
    }

    private void fetchData() {
    myRef.get().addOnCompleteListener(task -> {
        if (task.isSuccessful()) {
            for (DataSnapshot s : task.getResult().getChildren()) {
                if(s.getKey().equals(userName)){
                    user=s.getValue(User.class);
                    name.setText(user.getUser_name());
                    city.setText(user.getCity());
                    addr.setText(user.getAddress());
                    ph.setText(user.getPhone());
                    break;
                }
            }

        } else {
            Toast.makeText(ProfileActivity.this, "" +"User Offline", Toast.LENGTH_SHORT).show();
            fetchData();
        }
    });
    }

}