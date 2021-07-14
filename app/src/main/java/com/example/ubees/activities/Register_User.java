package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ubees.R;
import com.example.ubees.model.Products;
import com.example.ubees.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_User extends AppCompatActivity {
    EditText f_name,l_name,email,pass,city,address,phone;
    TextView login;
    FirebaseAuth firebaseAuth;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(Register_User.this, UserActivity.class);
            startActivity(intent);
            finish();
        }
        firebaseAuth=FirebaseAuth.getInstance();
        login=findViewById(R.id.login);
        login.setOnClickListener(v -> {
            Intent intent= new Intent(Register_User.this,Login.class);
            Register_User.this.startActivity(intent);
        });
        add=findViewById(R.id.btnRegister);
        add.setOnClickListener(v -> getData());

    }

    private void getData() {
    f_name=findViewById(R.id.f_name);
    l_name=findViewById(R.id.l_name);
    email=findViewById(R.id.email);
    pass=findViewById(R.id.user_pass);
    city=findViewById(R.id.city);
    address=findViewById(R.id.address);
    phone=findViewById(R.id.phone);
    if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
        Toast.makeText(Register_User.this,"Please enter valid EmailAddress",Toast.LENGTH_SHORT).show();
        return;
    }

//        Push Data to firebase
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User("0",email.getText().toString(),
                                                 f_name.getText().toString(),
                                                 l_name.getText().toString(),
                                                 city.getText().toString(),
                                                 address.getText().toString(),
                                                 phone.getText().toString());
                            myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                            Toast.makeText(Register_User.this, "Register Successful",
                                           Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Register_User.this, Login.class);
                            Register_User.this.startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register_User.this, "Authentication failed.",
                                           Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}