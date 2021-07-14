package com.example.ubees.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.ubees.model.Model;
import com.example.ubees.R;
import com.example.ubees.model.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MainActivity extends AppCompatActivity {
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
    RadioButton radioButton;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference root = database.getReference("products");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uploadBtn = findViewById(R.id.upload);
        imageView = findViewById(R.id.uploadimg);


        imageView.setOnClickListener(v -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent , 2);
        });

        uploadBtn.setOnClickListener(v -> {
            if (imageUri != null){
                uploadToFirebase(imageUri);
            }else{
                Toast.makeText(MainActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
            }
        });
        addProduct();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                        Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
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
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
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
        name = findViewById(R.id.p_name);
        desc = findViewById(R.id.p_desc);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.p_price);
        radioGroup = findViewById(R.id.radio1);
        inc = findViewById(R.id.btnadd);
        dec = findViewById(R.id.btnsub);
        add = findViewById(R.id.btnAddProduct);
        quantity.setText(counter+"");
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
            Products products = new Products(name.getText().toString(),
                                             desc.getText().toString(),
                                             status,
                                             (quantity.getText().toString()),
                                             (price.getText().toString()), model.getImageUrl());

            myRef.push().setValue(products);
            Intent intent=new Intent(MainActivity.this,UserActivity.class);
            MainActivity.this.startActivity(intent);

        });
    }


}