package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ubees.model.Model;
import com.example.ubees.R;
import com.example.ubees.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

public class Add_Product extends AppCompatActivity {
    EditText name, desc, quantity, price;
    String status;
    Uri imageUri;
    Model model = new Model();
    String modelId;
    private Button uploadBtn,add;
    private ImageView imageView;
    int counter = 0;
    ImageButton inc, dec;
    RadioGroup radioGroup;
    RadioButton radioButton,r1,r2;
    String key;

    Products products;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference root = database.getReference("products");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);
        uploadBtn = findViewById(R.id.upload);
        imageView = findViewById(R.id.uploadimg);
        name = findViewById(R.id.p_name);
        desc = findViewById(R.id.p_desc);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.p_price);
        radioGroup = findViewById(R.id.radio1);
        quantity.setText(counter+"");
        r1=findViewById(R.id.radio_btnyes);
        r2=findViewById(R.id.radio_btnno);
        String data=getIntent().getStringExtra("Item");
        if(getIntent().getExtras()!=null){
            key=getIntent().getStringExtra("key");
            Gson gson = new Gson();
            products=gson.fromJson(data,Products.class);
            name.setText(products.getName());
            desc.setText(products.getDesc());
            quantity.setText(products.getQuantity());
            price.setText(products.getPrice());
            imageUri =  Uri.parse( products.getImgId());
            Glide.with(Add_Product.this).load(products.getImgId()).into(imageView);
            model.setImageUrl(products.getImgId());
            if(r1.getText().toString().equals(products.getStatus())){
                r1.setChecked(true);
            }
            else{
                r2.setChecked(true);
            }
//            Toast.makeText(Add_Product.this,data,Toast.LENGTH_SHORT).show();
        }
        imageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
//            onActivityResult(2,1,galleryIntent);
            startActivityForResult(galleryIntent , 2);
        });

        uploadBtn.setOnClickListener(v -> {
            if (imageUri != null){
                uploadToFirebase(imageUri);
            }else{
                Toast.makeText(Add_Product.this, "Please Select Image", Toast.LENGTH_SHORT).show();

            }
        });
        addProduct();
    }
    @Override
    public void onBackPressed(){
        Intent intent=new Intent(Add_Product.this,UserActivity.class);
        Add_Product.this.startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }
    private void uploadToFirebase(Uri uri){
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        model = new Model(uri.toString());
                        Log.i("ZIA", "onSuccess: "+model.getImageUrl());
                        modelId = root.push().getKey();
                        progressDialog.dismiss();
                        Toast.makeText(Add_Product.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        if(getIntent().getExtras()!=null){
                            StorageReference s_db= FirebaseStorage.getInstance().getReferenceFromUrl(products.getImgId());
                            s_db.delete().addOnSuccessListener(unused -> Log.i("ZIA", "onSuccess: "));
                        }
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress( UploadTask.TaskSnapshot snapshot) {
                double progress
                        = (100.0
                        * snapshot.getBytesTransferred()
                        / snapshot.getTotalByteCount());
                progressDialog.setMessage(
                        "Uploaded "
                                + (int)progress + "%");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Add_Product.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


// We can also chain the two calls together


    private void addProduct() {
        inc = findViewById(R.id.btnadd);
        dec = findViewById(R.id.btnsub);
        add = findViewById(R.id.btnAddProduct);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
                status = radioButton.getText().toString();
        });
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
//        Push Data to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("products");

        add.setOnClickListener(v -> {
            if(getIntent().getExtras()!=null){
                myRef.child(key).removeValue();
            }

            Products products = new Products(name.getText().toString(),
                                             desc.getText().toString(),
                                             status,
                                             (quantity.getText().toString()),
                                             (price.getText().toString()),model.getImageUrl() );

            myRef.push().setValue(products);

            Intent intent=new Intent(Add_Product.this, UserActivity.class);
            Add_Product.this.startActivity(intent);
            finish();
        });
    }


}