package com.example.restaurantmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {


   TextView tv_mark;
   TextView tv_title;
   ImageView img_menu,img_order,img_contact,img_feedback,img_logout,img_transaction;
   LinearLayout ll_5,ll_6;


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final AlertDialog.Builder builder=new AlertDialog.Builder(Dashboard.this);
        builder.setTitle("Logout");
        builder.setMessage("Are you want to Close this application ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Toast.makeText(context, "delete after some time", Toast.LENGTH_SHORT).show();

                        //finish();
                        System.exit(0);

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        tv_mark=findViewById(R.id.tv_mark);
        tv_title=findViewById(R.id.tv_title);
        img_menu=findViewById(R.id.img_menu);
        img_order=findViewById(R.id.img_order);
        img_contact=findViewById(R.id.img_contact);
        img_feedback=findViewById(R.id.img_feedback);
        img_logout=findViewById(R.id.img_logout);
        img_transaction=findViewById(R.id.img_transaction);

        ll_5=findViewById(R.id.ll_5);
        ll_6=findViewById(R.id.ll_6);

        tv_mark.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv_mark.setSelected(true);

        tv_title.setText("Dashboard "+"("+LoginPage.type+")");

        if(LoginPage.type.contentEquals("admin"))
        {
            ll_5.setVisibility(View.VISIBLE);
            ll_6.setVisibility(View.VISIBLE);
        }


        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,MenuActivity.class);
                startActivity(intent);
            }
        });

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });

        img_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,OrderDetailsPage.class);
                startActivity(intent);
            }
        });


        img_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,AllFeedbackPage.class);
                startActivity(intent);
            }
        });


        img_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,ContactUs.class);
                startActivity(intent);
            }
        });

        img_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Dashboard.this,TransactionPage.class);
                startActivity(intent);
            }
        });





    }
}