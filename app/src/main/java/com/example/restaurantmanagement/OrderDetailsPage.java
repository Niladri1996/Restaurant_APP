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

public class OrderDetailsPage extends AppCompatActivity {
    RecyclerView recycler_order;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    OrderRecyclerAdapter adapter;
    TextView tv_title;
    ArrayList<Order_Model> orderModelArrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_page);
        reference= FirebaseDatabase.getInstance().getReference().child("Order");
        recycler_order=findViewById(R.id.recycler_order);
        tv_title=findViewById(R.id.tv_title);
        recycler_order.setHasFixedSize(true);
        recycler_order.setLayoutManager(new LinearLayoutManager(this));
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        tv_title.setText("Order Details "+"("+LoginPage.type+")");

        loadorder();
    }

    private void loadorder() {
        progressDialog.setMessage("Loading Orders...");
        progressDialog.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                orderModelArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    Log.d("jdhjd","dghdg");
                    Order_Model model=ds.getValue(Order_Model.class);
                    orderModelArrayList.add(model);

                }

                prepard();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressDialog.dismiss();
                Toast.makeText(OrderDetailsPage.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void prepard() {
        adapter=new OrderRecyclerAdapter(orderModelArrayList, OrderDetailsPage.this);
        recycler_order.setAdapter(adapter);
        progressDialog.dismiss();
    }
}