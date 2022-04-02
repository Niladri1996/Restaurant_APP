package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class CartViewPage extends AppCompatActivity  {
    TextView tv_title,tv_place_order;
    Spinner spinner_1;
    RelativeLayout rl_2,rl_1,rl_porder;
    String[] table_no = { "A001", "A002", "A003", "A004", "A005","A006", "A007", "A008"  };
    TextView tv_all_price;
    RecyclerView recycler_cart;
    ViewRecyclerAdapter adapter;
    ArrayList<FoodModel> arrayList=new ArrayList<>();
    String total_price,tableno;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view_page);
        tv_title=findViewById(R.id.tv_title);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        spinner_1=findViewById(R.id.spinner_1);
        recycler_cart=findViewById(R.id.recycler_cart);
        recycler_cart.setHasFixedSize(true);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        rl_2=findViewById(R.id.rl_2);
        rl_1=findViewById(R.id.rl_1);
        tv_all_price=findViewById(R.id.tv_all_price);
        tv_place_order=findViewById(R.id.tv_place_order);
        rl_porder=findViewById(R.id.rl_porder);
        reference= FirebaseDatabase.getInstance().getReference();

        for (int i=0;i<MenuActivity.modelArrayList.size();i++)
        {
            if(MenuActivity.modelArrayList.get(i).getCount()>0)
            {
                arrayList.add(MenuActivity.modelArrayList.get(i));
            }
        }

        if(MenuActivity.flag==true)
        {

            rl_2.setVisibility(View.GONE);
            rl_1.setVisibility(View.VISIBLE);

        }
        else
        {
            rl_2.setVisibility(View.VISIBLE);
            rl_porder.setVisibility(View.GONE);
            rl_1.setVisibility(View.GONE);

        }
        if(LoginPage.type.contentEquals("waiter"))
        {
            tv_title.setText("View Order "+"("+LoginPage.type+")");
        }

        ShowCart();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, table_no);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_1.setAdapter(adapter);


        spinner_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tableno=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        total_price=MenuActivity.total_price+"";

        tv_all_price.setText(total_price);

        tv_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AlertBox();
            }
        });
    }
    private void confirmorder() {
        progressDialog.setMessage("Order is processing....");
        progressDialog.show();
        final String timestamp=""+System.currentTimeMillis();
        HashMap<String,Object> map=new HashMap<>();
        map.put("timestamp",timestamp);
        map.put("orderId",timestamp);
        map.put("tableNo",tableno);
        map.put("totalcost",total_price);
        map.put("orderStatus","Processing");

        reference.child("Order").child(timestamp).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    for (int i=0;i<arrayList.size();i++)
                    {
                        String iemid=arrayList.get(i).getFoodId();
                        String item_name=arrayList.get(i).getFoodName();
                        String item_quantity=arrayList.get(i).getCount()+"";
                        String total_price=arrayList.get(i).getFoodPrice();

                        HashMap<String,Object> map1=new HashMap<>();
                        map1.put("iemid",iemid);
                        map1.put("itemname",item_name);
                        map1.put("itemquantity",item_quantity);
                        map1.put("totalprice",total_price);
                        reference.child("Order").child(timestamp).child("Item").child(iemid).setValue(map1);
                        progressDialog.dismiss();
                        Toast.makeText(CartViewPage.this, "Oder placed successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(CartViewPage.this,Dashboard.class);
                        startActivity(intent);

                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(CartViewPage.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void AlertBox() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you sure to place this order?");
        builder.setTitle("Order Conformation!");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

               confirmorder();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void ShowCart() {


            adapter=new ViewRecyclerAdapter(arrayList,CartViewPage.this);
            recycler_cart.setAdapter(adapter);

    }


}
