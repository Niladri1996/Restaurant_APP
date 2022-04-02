package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditOrderStatus extends AppCompatActivity {
    TextView tv_title,tv_orderid,tv_tableno,tv_cost;
    EditText ed_status;
    RelativeLayout btn_save;
    DatabaseReference reference;
    ProgressDialog progressDialog;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_status);

        tv_title=findViewById(R.id.tv_title);
        tv_orderid=findViewById(R.id.tv_orderid);
        tv_tableno=findViewById(R.id.tv_tableno);
        tv_cost=findViewById(R.id.tv_cost);
        btn_save=findViewById(R.id.btn_save);
        ed_status=findViewById(R.id.ed_status);
        orderId=getIntent().getStringExtra("Order_id");
       // Toast.makeText(EditOrderStatus.this, "order id "+orderId, Toast.LENGTH_SHORT).show();

        reference= FirebaseDatabase.getInstance().getReference().child("Order");

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        tv_title.setText("Update Order Status "+"("+LoginPage.type+")");

        loadorder();


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Inputdata();

            }
        });


    }

    private void loadorder() {
        reference.child(orderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String orderId=snapshot.child("orderId").getValue().toString();
                String tableNo=snapshot.child("tableNo").getValue().toString();
                String totalcost=snapshot.child("totalcost").getValue().toString();
                String orderStatus=snapshot.child("orderStatus").getValue().toString();

                ed_status.setText(orderStatus);
                tv_orderid.setText(orderId);
                tv_cost.setText(totalcost);
                tv_tableno.setText(tableNo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void Inputdata() {
        if(TextUtils.isEmpty(ed_status.getText().toString()))
        {
            Alert();

        }
        else
        {
            updateStatus();
        }
    }

    private void updateStatus() {
        progressDialog.setMessage("Updating Status ....");
        progressDialog.show();
        HashMap<String,Object> map=new HashMap<>();
        map.put("orderStatus",ed_status.getText().toString());
        reference.child(orderId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(EditOrderStatus.this, "Status update successfully.....", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditOrderStatus.this,OrderDetailsPage.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EditOrderStatus.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Alert() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Order Status can't be empty!!");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}