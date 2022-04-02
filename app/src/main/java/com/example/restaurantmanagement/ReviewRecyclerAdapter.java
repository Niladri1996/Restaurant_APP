package com.example.restaurantmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewRecyclerAdapter  extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder> {

    ArrayList<Review_Model> review_modelArrayList=new ArrayList<>();
    Context context;

    public ReviewRecyclerAdapter(ArrayList<Review_Model> review_modelArrayList, Context context) {
        this.review_modelArrayList = review_modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_review,parent,false);
        return new ReviewRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerAdapter.ViewHolder holder, int position) {

        Review_Model model=review_modelArrayList.get(position);
        holder.txt_order.setText(review_modelArrayList.get(position).getOrderId());
        holder.txt_cust_address.setText(review_modelArrayList.get(position).getCustAddress());
        holder.txt_cust_review.setText(review_modelArrayList.get(position).getCustReview());
        holder.txt_cust_phone.setText(review_modelArrayList.get(position).getCustPhone());
        holder.rating.setRating(Float.parseFloat(review_modelArrayList.get(position).getCustRating()));


    }

    @Override
    public int getItemCount() {
        return review_modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_order,txt_cust_phone,txt_cust_address,txt_cust_review;
        RatingBar rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_order=itemView.findViewById(R.id.txt_order);
            txt_cust_phone=itemView.findViewById(R.id.txt_cust_phone);
            txt_cust_address=itemView.findViewById(R.id.txt_cust_address);
            txt_cust_review=itemView.findViewById(R.id.txt_cust_review);
            rating=itemView.findViewById(R.id.rating);

        }
    }
}
