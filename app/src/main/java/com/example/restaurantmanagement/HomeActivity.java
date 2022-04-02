package com.example.restaurantmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    FloatingActionButton btn_fab;
    RecyclerView recycler_product;
    ProgressDialog progressDialog;
    EditText ed_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ed_search=findViewById(R.id.ed_search);
        btn_fab=findViewById(R.id.btn_fab);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        recycler_product=findViewById(R.id.recycler_product);
        recycler_product.setHasFixedSize(true);
        recycler_product.setLayoutManager(new LinearLayoutManager(this));


        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(HomeActivity.this,UploadFood.class);
                startActivity(intent);
            }
        });
    }
}