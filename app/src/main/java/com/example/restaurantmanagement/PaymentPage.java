package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PaymentPage extends AppCompatActivity {

    String order_price,orderId;
    TextView tv_pay,txt_orderid;
    RadioGroup radioGroup;
  public static   RadioButton rd_button;
    RelativeLayout rl_2;
    int radio_id;
    ProgressDialog progressDialog;

    TextView tv_payment,btn_review;
    ImageView img_success;
    DatabaseReference reference;
    EditText ed_card_name,ed_card_no,ed_card_ex;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_page);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        tv_pay=findViewById(R.id.tv_pay);
        rl_2=findViewById(R.id.rl_2);
        radioGroup=findViewById(R.id.radioGroup);
        order_price=getIntent().getStringExtra("order_cost");
        orderId=getIntent().getStringExtra("Order_id");
        reference= FirebaseDatabase.getInstance().getReference().child("TransactionDetails");


        tv_pay.setText("PAY ("+order_price+ ")");


        txt_orderid=findViewById(R.id.txt_orderid);
        ed_card_name=findViewById(R.id.ed_card_name);
        ed_card_no=findViewById(R.id.ed_card_no);
        ed_card_ex=findViewById(R.id.ed_card_ex);


        txt_orderid.setText(orderId);


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Alertbox();
            }
        });


    }

    private void Alertbox() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Are you want to pay by "+rd_button.getText().toString()+" ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                //Toast.makeText(PaymentPage.this, "Pay now", Toast.LENGTH_SHORT).show();

               // openPopup();
                savetransaction();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void savetransaction() {
        progressDialog.setMessage("Save Transaction Details....");
        progressDialog.show();
      //  final String timestamp=""+System.currentTimeMillis();

        HashMap<String,Object> map=new HashMap<>();
        map.put("CardName",ed_card_name.getText().toString());
        map.put("CardNo",ed_card_no.getText().toString());
        map.put("CardDate",ed_card_ex.getText().toString());
        map.put("OrderId",orderId);


        reference.child(orderId).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    openPopup();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
           progressDialog.dismiss();
            }
        });
    }

    private void openPopup() {

        progressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentPage.this);
        builder.setTitle("");
        final View custom = getLayoutInflater().inflate(R.layout.payment_popup, null);

        tv_payment=custom.findViewById(R.id.tv_payment);
        img_success=custom.findViewById(R.id.img_success);
        btn_review=custom.findViewById(R.id.btn_review);

        builder.setView(custom);
        final AlertDialog dialog = builder.create();
        dialog.show();


        btn_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
                Toast.makeText(PaymentPage.this, "Order Id "+orderId, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(new Intent(PaymentPage.this,ReviewPage.class));
                intent.putExtra("OrderId",orderId);
                startActivity(intent);
            }

        });




    }


    public void check_btn(View view)
    {
       radio_id=radioGroup.getCheckedRadioButtonId();
        rd_button=findViewById(radio_id);

     Toast.makeText(PaymentPage.this, "You Select "+rd_button.getText().toString(), Toast.LENGTH_SHORT).show();

        if(rd_button.getText().toString().equals("By Cash"))
        {
            rl_2.setVisibility(View.GONE);
        }
        else
        {
            rl_2.setVisibility(View.VISIBLE);
        }


    }
}