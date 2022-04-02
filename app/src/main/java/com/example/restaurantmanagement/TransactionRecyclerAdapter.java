package com.example.restaurantmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionRecyclerAdapter extends RecyclerView.Adapter<TransactionRecyclerAdapter.ViewHolder> {

    ArrayList<TransactionModel> transactionModelArrayList=new ArrayList<>();
    Context context;

    public TransactionRecyclerAdapter(ArrayList<TransactionModel> transactionModelArrayList, Context context) {
        this.transactionModelArrayList = transactionModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaction,parent,false);
        return new TransactionRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionRecyclerAdapter.ViewHolder holder, int position) {

        TransactionModel model=transactionModelArrayList.get(position);

        holder.txt_order.setText(transactionModelArrayList.get(position).getOrderId());
        holder.txt_card_date.setText(transactionModelArrayList.get(position).getCardDate());
        holder.txt_card_name.setText(transactionModelArrayList.get(position).getCardName());
        holder.txt_card_no.setText(transactionModelArrayList.get(position).getCardNo());


    }

    @Override
    public int getItemCount() {
        return transactionModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_order,txt_card_name,txt_card_no,txt_card_date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_order=itemView.findViewById(R.id.txt_order);
            txt_card_name=itemView.findViewById(R.id.txt_card_name);
            txt_card_no=itemView.findViewById(R.id.txt_card_no);
            txt_card_date=itemView.findViewById(R.id.txt_card_date);
        }
    }
}
