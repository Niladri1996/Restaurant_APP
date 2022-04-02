package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ReviewPage extends AppCompatActivity {
    String orderId;
    TextView txt_orderid,tv_submit;
    RatingBar rating;
    EditText ed_name,ed_address,ed_phone,ed_review;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_page);

        orderId=getIntent().getStringExtra("OrderId");
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        reference= FirebaseDatabase.getInstance().getReference().child("ReviewList");


        txt_orderid=findViewById(R.id.txt_orderid);
        rating=findViewById(R.id.rating);
        ed_name=findViewById(R.id.ed_name);
        ed_address=findViewById(R.id.ed_address);
        ed_phone=findViewById(R.id.ed_phone);
        ed_review=findViewById(R.id.ed_review);
        tv_submit=findViewById(R.id.tv_submit);


        txt_orderid.setText(orderId);

        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validation();
            }
        });


    }

    private void Validation() {
        if(TextUtils.isEmpty(ed_name.getText().toString()))
        {
            Alertbox();
            return;
        }
        else if(TextUtils.isEmpty(ed_address.getText().toString()))
        {
            Alertbox();
            return;
        }
        else if(TextUtils.isEmpty(ed_phone.getText().toString()))
        {
            Alertbox();
            return;
        }
        else if(TextUtils.isEmpty(ed_review.getText().toString()))
        {
            Alertbox();
            return;
        }
        else
        {
            submitForm();
        }
    }

    private void submitForm() {
       // Toast.makeText(ReviewPage.this, "Submit", Toast.LENGTH_SHORT).show();
        String rating0=""+rating.getRating();

        progressDialog.setMessage("Adding product....");
        progressDialog.show();
        HashMap<String,Object> map=new HashMap<>();
        map.put("OrderId",orderId);
        map.put("CustName",ed_name.getText().toString());
        map.put("CustAddress",ed_address.getText().toString());
        map.put("CustPhone",ed_phone.getText().toString());
        map.put("CustReview",ed_review.getText().toString());
        map.put("CustRating",rating0);

        reference.child(orderId).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(ReviewPage.this, "Thank you  , Come Again!!!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ReviewPage.this,Dashboard.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(ReviewPage.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

    private void Alertbox() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Fields cannot be left blank");
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                //Toast.makeText(context, "Delet item successfully of Position => "+position, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }
}