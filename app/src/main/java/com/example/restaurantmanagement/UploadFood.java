package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class UploadFood extends AppCompatActivity {

    EditText ed_pname,ed_pprice;
    RelativeLayout btn_save;
    ImageView img_product;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    Uri resultUri;
    int PICK_IMAGE=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_food);
        firebaseAuth=   FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("Restaurant");

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        ed_pname=findViewById(R.id.ed_pname);
        ed_pprice=findViewById(R.id.ed_pprice);

        btn_save=findViewById(R.id.btn_save);
        img_product=findViewById(R.id.img_product);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inputdata();
            }
        });

        img_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void Inputdata() {
        if(resultUri==null)
        {
            Toast.makeText(this, "Image must be magnatory", Toast.LENGTH_SHORT).show();
        }

        else  if(TextUtils.isEmpty(ed_pname.getText().toString()))
        {
            Alert();
        }
        else if(TextUtils.isEmpty(ed_pprice.getText().toString()))
        {
            Alert();
        }

        else
        {
            AddProducts();
        }
    }

    private void AddProducts() {
        progressDialog.setMessage("Adding product....");
        progressDialog.show();

        final String timestamp=""+System.currentTimeMillis();
        String filepathname="product_image/"+""+timestamp;
        StorageReference storageReference= FirebaseStorage.getInstance().getReference(filepathname);
        storageReference.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful());
                Uri downloadimageurl=uriTask.getResult();
                String downloadimage=downloadimageurl.toString();
                if(uriTask.isSuccessful())
                {
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("ProductId",timestamp);
                    map.put("ProductName",ed_pname.getText().toString());
                    map.put("ProductPrice",ed_pprice.getText().toString());
                    map.put("Timestamp",timestamp);
                    map.put("ProductImage",downloadimage);

                    reference.child("Products")
                            .child("ProductInformation").child(timestamp).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadFood.this, "Product save successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(UploadFood.this,HomeActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadFood.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UploadFood.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Alert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Fields can't be empty!!");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void openGallery() {
        Intent gellaryintent=new Intent();
        gellaryintent.setAction(Intent.ACTION_GET_CONTENT);
        gellaryintent.setType("image/*");
        startActivityForResult(gellaryintent,PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK && data!=null)
        {
            resultUri=data.getData();
            img_product.setImageURI(resultUri);
        }
    }
}