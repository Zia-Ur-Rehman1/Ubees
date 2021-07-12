package com.example.ubees;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText name,desc,quantity,price;
    int status=0;

    Button add;
    int counter=0;
    ImageButton inc,dec;
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addProduct();

// We can also chain the two calls together

    }

    private void addProduct() {
        name=findViewById(R.id.p_name);
        desc=findViewById(R.id.p_desc);
        quantity=findViewById(R.id.quantity);
        price=findViewById(R.id.p_price);
        radioGroup=findViewById(R.id.radio1);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            if(radioButton.getText()=="Available"){
            status=Integer.parseInt(radioButton.getText().toString());
            }
        });
        inc=findViewById(R.id.btnadd);
        dec=findViewById(R.id.btnsub);
        inc.setOnClickListener(v -> {
            if(quantity.getText().toString().trim().length()>0){
                counter=Integer.parseInt(quantity.getText().toString());
                counter++;
                quantity.setText(counter+"");
            }
            else{
                counter++;
                quantity.setText(counter+"");
            }
        });
        dec.setOnClickListener(v -> {
            if(quantity.getText().toString().trim().length()>0 && Integer.parseInt(quantity.getText().toString())>0){
                counter=Integer.parseInt(quantity.getText().toString());
                counter--;
                quantity.setText(counter+"");
            }
        });
        add=findViewById(R.id.btnAddProduct);
        add.setOnClickListener(v -> {
            Products products=new Products(name.getText().toString(),desc.getText().toString(),status,Integer.parseInt(quantity.getText().toString()),Integer.parseInt(price.getText().toString()));
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("products");
            myRef.push().setValue(products);
        });
    }


}