package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    TextView tv_title;
    FloatingActionButton btn_fab;
    RecyclerView recycler_product;
    ProgressDialog progressDialog;
    EditText ed_search;
    DatabaseReference reference;
   public static ArrayList<FoodModel> modelArrayList=new ArrayList<>();
    FoodListRecyclerAdapter adapter;
    RelativeLayout rl_popup;
    TextView txt_total_item,total_cost,tv_view;
    ImageView img_next;
    public static boolean flag=false;
    public  static  double total_price=0.0;
    public static  int total_count=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tv_title=findViewById(R.id.tv_title);
        btn_fab=findViewById(R.id.btn_fab);
        reference= FirebaseDatabase.getInstance().getReference().child("FoodMaster");
        ed_search=findViewById(R.id.ed_search);

        rl_popup=findViewById(R.id.rl_popup);
        txt_total_item=findViewById(R.id.txt_total_item);
        total_cost=findViewById(R.id.total_cost);
        tv_view=findViewById(R.id.tv_view);
        img_next=findViewById(R.id.img_next);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        recycler_product=findViewById(R.id.recycler_product);
        recycler_product.setHasFixedSize(true);
        recycler_product.setLayoutManager(new LinearLayoutManager(this));


        tv_title.setText("Food Menu "+"("+LoginPage.type+")");

        showFoodList();

       ed_search.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               filter(s.toString());
           }
       });

        if(LoginPage.type.contentEquals("admin"))
        {
            btn_fab.setVisibility(View.VISIBLE);
        }
        else
        {
            btn_fab.setVisibility(View.GONE);
        }

        btn_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MenuActivity.this,FoodUpload.class);
                startActivity(intent);
            }
        });

        if(LoginPage.type.contentEquals("waiter"))
        {
            rl_popup.setVisibility(View.VISIBLE);

        }


        tv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,CartViewPage.class);
                startActivity(intent);
            }
        });
        img_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(MenuActivity.this,CartViewPage.class);
                startActivity(intent);
            }
        });

    }








    private void filter(String text) {
        ArrayList<FoodModel> filterlist=new ArrayList<>();
        for(FoodModel item:modelArrayList)
        {
            if(item.getFoodName().toLowerCase().contains(text.toLowerCase()))
            {
                filterlist.add(item);
            }
        }
        adapter.filtered(filterlist);
    }

    private void showFoodList() {
        progressDialog.setMessage("Loading Products...");
        progressDialog.show();

        reference.child("FoodsDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("ssss",snapshot.toString());

                modelArrayList.clear();
                for (DataSnapshot ds:snapshot.getChildren())
                {
                    Log.d("jdhjd","dghdg");
                    FoodModel model_product=ds.getValue(FoodModel.class);
                    modelArrayList.add(model_product);

                }

                prepard();
                //getCalculation(position, count);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MenuActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void prepard() {
        adapter=new FoodListRecyclerAdapter(modelArrayList, MenuActivity.this);
        recycler_product.setAdapter(adapter);
        progressDialog.dismiss();
    }

    public void getCalculation(int position) {


        total_price=0.0;
        total_count=0;
        for (int i=0;i<modelArrayList.size();i++)
        {
            total_price=total_price+( modelArrayList.get(i).getCount()*Double.parseDouble(modelArrayList.get(i).getFoodPrice()));
            total_count=total_count+modelArrayList.get(i).getCount();
        }
        total_cost.setText("Rs. "+total_price);
        txt_total_item.setText(total_count+ " item");
        flag=true;


    }




}