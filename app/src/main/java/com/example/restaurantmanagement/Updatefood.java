package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Updatefood extends AppCompatActivity {
    String foodid;
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
        setContentView(R.layout.activity_updatefood);
        foodid=getIntent().getStringExtra("foodid");
      //  Toast.makeText(Updatefood.this, ""+foodid, Toast.LENGTH_SHORT).show();
        firebaseAuth=   FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("FoodMaster");
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        ed_pname=findViewById(R.id.ed_pname);
        ed_pprice=findViewById(R.id.ed_pprice);

        btn_save=findViewById(R.id.btn_save);
        img_product=findViewById(R.id.img_product);

        loadFood();

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inputdata();
            }
        });

    }

    private void Inputdata() {
        if(TextUtils.isEmpty(ed_pname.getText().toString()))
        {
            Alert();
        }
        else if(TextUtils.isEmpty(ed_pprice.getText().toString()))
        {
            Alert();
        }
        else
        {
            UpdateProducts();
        }
    }

    private void UpdateProducts() {
        progressDialog.setMessage("Updating Food...");
        progressDialog.show();

        HashMap<String,Object> map=new HashMap<>();
        map.put("FoodId",foodid);
        map.put("FoodName",ed_pname.getText().toString());
        map.put("FoodPrice",ed_pprice.getText().toString());
        map.put("Timestamp",foodid);
        // map.put("ProductImage",resultUri);

        reference.child("FoodsDetails")
               .child(foodid).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                Toast.makeText(Updatefood.this, "Food update successfully.....", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Updatefood.this,MenuActivity.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(Updatefood.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void loadFood() {
        reference.child("FoodsDetails").child(foodid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            String FoodId=snapshot.child("FoodId").getValue().toString();
            String FoodName=snapshot.child("FoodName").getValue().toString();
            String FoodPrice=snapshot.child("FoodPrice").getValue().toString();
            String Timestamp=snapshot.child("Timestamp").getValue().toString();
            String FoodImage=snapshot.child("FoodImage").getValue().toString();

            ed_pname.setText(FoodName);
            ed_pprice.setText(FoodPrice);
            Picasso.get().load(FoodImage).into(img_product);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}