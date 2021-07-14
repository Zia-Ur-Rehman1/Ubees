package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class Login extends AppCompatActivity {
    String user_name_s,pass;
    EditText user_name,password;
    TextView register;
    FirebaseAuth firebaseAuth;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        register=findViewById(R.id.Register);
        login=findViewById(R.id.btnLogin);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(Login.this, UserActivity.class);
            startActivity(intent);
            finish();
        }
        register.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Register_User.class);
            startActivity(intent);
            finish();
        });
        login.setOnClickListener(v -> verify_user());
    }
    private void verify_user(){
        user_name=findViewById(R.id.user_name);
        password=findViewById(R.id.user_pass);
        user_name_s=user_name.getText().toString().trim();
        pass=password.getText().toString().trim();
        if(user_name_s.isEmpty() || pass.isEmpty() )
        {
            Toast.makeText(Login.this, "Username or Password can't be blank",
                           Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(user_name_s,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Successful",
                                           Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, UserActivity.class);
                            Login.this.startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Login.this, "Username or Password incorrect",
                                           Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }


}