package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionPage extends AppCompatActivity {
    RecyclerView recycler_transaction;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    TransactionRecyclerAdapter adapter;

    ArrayList<TransactionModel> transactionModelArrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_page);
        reference= FirebaseDatabase.getInstance().getReference().child("TransactionDetails");

        recycler_transaction=findViewById(R.id.recycler_transaction);
        recycler_transaction.setHasFixedSize(true);
        recycler_transaction.setLayoutManager(new LinearLayoutManager(this));

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        loadTransactionDetails();
    }

    private void loadTransactionDetails() {
        progressDialog.setMessage("Loading Orders...");
        progressDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                transactionModelArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    Log.d("jdhjd","dghdg");
                    TransactionModel model=ds.getValue(TransactionModel.class);
                    transactionModelArrayList.add(model);

                }

                prepard();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressDialog.dismiss();
                Toast.makeText(TransactionPage.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void prepard() {
        adapter=new TransactionRecyclerAdapter(transactionModelArrayList, TransactionPage.this);
        recycler_transaction.setAdapter(adapter);
        progressDialog.dismiss();
    }
}