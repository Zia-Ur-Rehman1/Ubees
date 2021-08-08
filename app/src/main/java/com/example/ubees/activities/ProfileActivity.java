package com.example.ubees.activities;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    EditText name, city, addr, ph;
    String userName= FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
    Button edit,update;
    User user;
    ImageButton order;

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
        order=findViewById(R.id.profileOrder);
        fetchData();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setFocusableInTouchMode(true);
                city.setFocusableInTouchMode(true);
                addr.setFocusableInTouchMode(true);
                ph.setFocusableInTouchMode(true);
                update.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);
            }
        });
    update.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if(!name.getText().toString().equals(user.getUser_name()) ||
            !city.getText().toString().equals(user.getCity())|| !addr.getText().toString().equals(user.getAddress())
                || !ph.getText().toString().equals(user.getPhone())
        ){
            myRef.child(userName).child("user_name").setValue(name.getText().toString());
            myRef.child(userName).child("city").setValue(city.getText().toString());
            myRef.child(userName).child("address").setValue(addr.getText().toString());
            myRef.child(userName).child("phone").setValue(ph.getText().toString());
            }
        }
    });
    order.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(ProfileActivity.this,OrderDetails.class);
            startActivity(intent);
            finish();
        }
    });
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(ProfileActivity.this, UserActivity.class);
        ProfileActivity.this.startActivity(intent);
        finish();
    }

    private void fetchData() {
    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
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
        }
    });
    }

}