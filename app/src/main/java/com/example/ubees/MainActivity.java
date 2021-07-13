package com.example.ubees;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
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
    int status = 0;
    Uri imageUri;
    String modelId;
    private Button uploadBtn, showAllBtn;
    private ImageView imageView;
    private ProgressBar progressBar;
    Button add;
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
        showAllBtn = findViewById(R.id.showall);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.uploadimg);
        progressBar.setVisibility(View.INVISIBLE);

        showAllBtn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this , MainActivity.class)));

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

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri1) {

                Model model = new Model(uri1.toString());
                modelId = root.push().getKey();
//                root.child(modelId).setValue(model);
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                imageView.setImageResource(R.drawable.ic_img_upload);
            }
        })).addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE)).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
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

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            if (radioButton.getText() == "Available") {
                status = Integer.parseInt(radioButton.getText().toString());
            }
        });
        inc.setOnClickListener(v -> {
            if (quantity.getText().toString().trim().length() > 0) {
                counter = Integer.parseInt(quantity.getText().toString());
                counter++;
                quantity.setText(counter + "");
            } else {
                counter++;
                quantity.setText(counter + "");
            }
        });
        dec.setOnClickListener(v -> {
            if (quantity.getText().toString().trim().length() > 0 && Integer.parseInt(quantity.getText().toString()) > 0) {

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
                                             Integer.parseInt(quantity.getText().toString()),
                                             Integer.parseInt(price.getText().toString()),modelId);

            myRef.push().setValue(products);
        });
    }


}