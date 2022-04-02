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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ContactUs extends AppCompatActivity {
    EditText ed_name,ed_email,ed_phone,ed_msg;
    TextView tv_send;
    DatabaseReference reference;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        ed_email=findViewById(R.id.ed_email);
        ed_name=findViewById(R.id.ed_name);
        ed_phone=findViewById(R.id.ed_phone);
        ed_msg=findViewById(R.id.ed_msg);
        tv_send=findViewById(R.id.tv_send);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        reference= FirebaseDatabase.getInstance().getReference().child("ContactUs");



        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }

    private void validation() {
        if(TextUtils.isEmpty(ed_name.getText().toString()))
        {
            Alertbox();
            return;
        }
        else if(TextUtils.isEmpty(ed_email.getText().toString()))
        {
            Alertbox();
            return;
        }
        else if(TextUtils.isEmpty(ed_phone.getText().toString()))
        {
            Alertbox();
            return;
        }
        else if(TextUtils.isEmpty(ed_msg.getText().toString()))
        {
            Alertbox();
            return;
        }
        else
        {
           // Toast.makeText(ContactUs.this, "send now", Toast.LENGTH_SHORT).show();
            sendForm();


        }
    }

    private void sendForm() {
        progressDialog.setMessage("Adding product....");
        progressDialog.show();
        final String timestamp=""+System.currentTimeMillis();

        HashMap<String,Object> map=new HashMap<>();
        map.put("CustName",ed_name.getText().toString());
        map.put("CustEmail",ed_email.getText().toString());
        map.put("CustPhone",ed_phone.getText().toString());
        map.put("CustMessage",ed_msg.getText().toString());
        map.put("Timestamp",timestamp);
        reference.child(timestamp).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(ContactUs.this, "Thank you  For Contacting us ,We will send  reply in your mail", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ContactUs.this,Dashboard.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                progressDialog.dismiss();
                Toast.makeText(ContactUs.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();

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