package com.example.restaurantmanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.ViewHolder> {

    ArrayList<Order_Model>  orderModelArrayList=new ArrayList<>();
    Context context;

    public OrderRecyclerAdapter(ArrayList<Order_Model> orderModelArrayList, Context context) {
        this.orderModelArrayList = orderModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order,parent,false);
        return new OrderRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Order_Model model=orderModelArrayList.get(position);
        holder.txt_order.setText(orderModelArrayList.get(position).getOrderId());
        holder.txt_cost.setText(orderModelArrayList.get(position).getTotalcost());
        holder.txt_tableno.setText(orderModelArrayList.get(position).getTableNo());


        holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ItemDetails.class);
                intent.putExtra("Order_id",orderModelArrayList.get(position).getOrderId());
                intent.putExtra("Order_status",orderModelArrayList.get(position).getOrderStatus());
                intent.putExtra("order_cost",orderModelArrayList.get(position).getTotalcost());
                intent.putExtra("Table_no",orderModelArrayList.get(position).getTableNo());
                context.startActivity(intent);
            }
        });

        if(LoginPage.type.contentEquals("cook"))
        {
            holder.tv_edit.setVisibility(View.VISIBLE);
        }
        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,EditOrderStatus.class);
                intent.putExtra("Order_id",orderModelArrayList.get(position).getOrderId());
                context.startActivity(intent);
            }
        });

        if(orderModelArrayList.get(position).getOrderStatus().contentEquals("Pending"))
        {
            holder.txt_status.setText(orderModelArrayList.get(position).getOrderStatus());
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.Pending));
        }
        else if(orderModelArrayList.get(position).getOrderStatus().contentEquals("Start"))
        {
            holder.txt_status.setText(orderModelArrayList.get(position).getOrderStatus());
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.Start));

        }
        else if(orderModelArrayList.get(position).getOrderStatus().contentEquals("Processing"))
        {
            holder.txt_status.setText(orderModelArrayList.get(position).getOrderStatus());
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.Processing));

        }
        else if(orderModelArrayList.get(position).getOrderStatus().contentEquals("Ready"))
        {
            holder.txt_status.setText(orderModelArrayList.get(position).getOrderStatus());
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.Ready));

        }
        else if(orderModelArrayList.get(position).getOrderStatus().contentEquals("Complete"))
        {
            holder.txt_status.setText(orderModelArrayList.get(position).getOrderStatus());
            holder.txt_status.setTextColor(context.getResources().getColor(R.color.Complete));

        }
        else
        {
            holder.txt_status.setText(orderModelArrayList.get(position).getOrderStatus());
        }



    }

    @Override
    public int getItemCount() {
        return orderModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_order,txt_tableno,txt_status,txt_cost,tv_edit;
        RelativeLayout rl_main;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_order=itemView.findViewById(R.id.txt_order);
            txt_tableno=itemView.findViewById(R.id.txt_tableno);
            txt_status=itemView.findViewById(R.id.txt_status);
            txt_cost=itemView.findViewById(R.id.txt_cost);
            tv_edit=itemView.findViewById(R.id.tv_edit);
            rl_main=itemView.findViewById(R.id.rl_main);
        }
    }
}
