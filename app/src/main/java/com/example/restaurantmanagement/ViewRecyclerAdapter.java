package com.example.restaurantmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewRecyclerAdapter extends RecyclerView.Adapter<ViewRecyclerAdapter.ViewHolder> {

    ArrayList<FoodModel> foodModelArrayList=new ArrayList<>();
    Context context;
    double total_price=0.0;

    public ViewRecyclerAdapter(ArrayList<FoodModel> foodModelArrayList, Context context) {
        this.foodModelArrayList = foodModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart,parent,false);
        return new ViewRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRecyclerAdapter.ViewHolder holder, int position) {

        FoodModel model=foodModelArrayList.get(position);


            holder.rl_cart1.setEnabled(false);
            holder.tv_dish_name.setText(foodModelArrayList.get(position).getFoodName());
            holder.tv_sub_price.setText(foodModelArrayList.get(position).getFoodPrice());
            holder.tv_quantity.setText(foodModelArrayList.get(position).getCount()+"");


            total_price=(foodModelArrayList.get(position).getCount() * Double.parseDouble(foodModelArrayList.get(position).getFoodPrice()));
            holder.tv_total_price.setText(total_price+"");




    }

    @Override
    public int getItemCount() {
        return foodModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_dish_name,tv_total_price,tv_sub_price,tv_quantity;
        RelativeLayout rl_cart1;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_dish_name=itemView.findViewById(R.id.tv_dish_name);
            tv_total_price=itemView.findViewById(R.id.tv_total_price);
            tv_sub_price=itemView.findViewById(R.id.tv_sub_price);
            tv_quantity=itemView.findViewById(R.id.tv_quantity);
            rl_cart1=itemView.findViewById(R.id.rl_cart1);

        }
    }
}
