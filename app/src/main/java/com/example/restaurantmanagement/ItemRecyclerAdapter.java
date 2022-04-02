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

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {

    ArrayList<Item_Model>item_modelArrayList=new ArrayList<>();
    Context context;

    public ItemRecyclerAdapter(ArrayList<Item_Model> item_modelArrayList, Context context) {
        this.item_modelArrayList = item_modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item,parent,false);
        return new ItemRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.ViewHolder holder, int position) {

        Item_Model model=item_modelArrayList.get(position);
        holder.txt_item.setText(item_modelArrayList.get(position).getIemid());
        holder.txt_item_name.setText(item_modelArrayList.get(position).getItemname());
        holder.tv_item_price.setText(item_modelArrayList.get(position).getTotalprice());
        holder.txt_quantity.setText(item_modelArrayList.get(position).getItemquantity());





    }

    @Override
    public int getItemCount() {
        return item_modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_item,txt_item_name,tv_item_price,txt_quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_item=itemView.findViewById(R.id.txt_item);
            txt_item_name=itemView.findViewById(R.id.txt_item_name);
            tv_item_price=itemView.findViewById(R.id.tv_item_price);
            txt_quantity=itemView.findViewById(R.id.txt_quantity);
        }
    }
}
