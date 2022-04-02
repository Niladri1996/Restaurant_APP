package com.example.restaurantmanagement;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodListRecyclerAdapter extends RecyclerView.Adapter<FoodListRecyclerAdapter.ViewHolder> {

    ArrayList<FoodModel> foodModelArrayList=new ArrayList<>();
    MenuActivity context;
    private  int count=0;


    public FoodListRecyclerAdapter(ArrayList<FoodModel> foodModelArrayList, MenuActivity context) {
        this.foodModelArrayList = foodModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list,parent,false);
        return new FoodListRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FoodModel model=foodModelArrayList.get(position);
        holder.txt_title.setText(foodModelArrayList.get(position).getFoodName());
        holder.txt_price.setText("Rs : "+foodModelArrayList.get(position).FoodPrice);
        Picasso.get().load(model.getFoodImage()).into(holder.img_item);

        if(LoginPage.type.contentEquals("admin"))
        {
            holder.img_delete.setVisibility(View.VISIBLE);
            holder.img_edit.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.img_delete.setVisibility(View.GONE);
            holder.img_edit.setVisibility(View.GONE);
        }

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Updatefood.class);
                intent.putExtra("foodid",foodModelArrayList.get(position).getFoodId());
                context.startActivity(intent);
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Are you want to sure to delete the products "+foodModelArrayList.get(position).getFoodName() )
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Toast.makeText(context, "delete after some time", Toast.LENGTH_SHORT).show();
                                deleteProduct(foodModelArrayList.get(position).getFoodId());

                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
            }
        });

        if(LoginPage.type.contentEquals("waiter"))
        {
            holder.rl_cart.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.rl_cart.setVisibility(View.GONE);
        }

        holder.txt_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rl_cart1.setVisibility(View.VISIBLE);
                holder.rl_cart.setVisibility(View.GONE);


            }
        });

        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foodModelArrayList.get(position).getCount()<50)
                {
                    foodModelArrayList.get(position).setCount(foodModelArrayList.get(position).getCount()+1);
                    holder.tv_quantity.setText(String.valueOf(foodModelArrayList.get(position).getCount()));
                    ((MenuActivity)context).getCalculation(position);

                }
            }
        });

        holder.tv_removed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(foodModelArrayList.get(position).getCount()>0)
                {
                    foodModelArrayList.get(position).setCount(foodModelArrayList.get(position).getCount()-1);
                    holder.tv_quantity.setText(String.valueOf(foodModelArrayList.get(position).getCount()));
                    ((MenuActivity)context).getCalculation(position);
                }
            }
        });





    }

    private void deleteProduct(String foodId) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        reference.child("FoodMaster").child("FoodsDetails").child(foodId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context, "Food item  deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodModelArrayList.size();
    }

    public void filtered(ArrayList<FoodModel> filterlist) {
        foodModelArrayList=filterlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_item,img_edit,img_delete;
        TextView txt_title,txt_Add,tv_removed,tv_quantity,tv_add;
        TextView txt_description;
        TextView txt_price;
        RelativeLayout rl_cart,rl_cart1;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_item=itemView.findViewById(R.id.img_item);
            txt_title=itemView.findViewById(R.id.txt_title);
            txt_description=itemView.findViewById(R.id.txt_description);
            txt_price=itemView.findViewById(R.id.txt_price);
            img_edit=itemView.findViewById(R.id.img_edit);
            img_delete=itemView.findViewById(R.id.img_delete);
            rl_cart=itemView.findViewById(R.id.rl_cart);
            rl_cart1=itemView.findViewById(R.id.rl_cart1);
            txt_Add=itemView.findViewById(R.id.txt_Add);
            tv_removed=itemView.findViewById(R.id.tv_removed);
            tv_quantity=itemView.findViewById(R.id.tv_quantity);
            tv_add=itemView.findViewById(R.id.tv_add);



        }
    }
}
