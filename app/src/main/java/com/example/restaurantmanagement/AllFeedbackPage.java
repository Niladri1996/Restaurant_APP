package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllFeedbackPage extends AppCompatActivity {
    RecyclerView recycler_review;
    ProgressDialog progressDialog;
    DatabaseReference reference;
    ArrayList<Review_Model> review_modelArrayList=new ArrayList<>();
    ReviewRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_feedback_page);
        reference= FirebaseDatabase.getInstance().getReference().child("ReviewList");
        recycler_review=findViewById(R.id.recycler_review);
        recycler_review.setHasFixedSize(true);
        recycler_review.setLayoutManager(new LinearLayoutManager(this));


        loadreview();
    }

    private void loadreview() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                review_modelArrayList.clear();
                for(DataSnapshot ds:snapshot.getChildren())
                {
                    Review_Model model_review=ds.getValue(Review_Model.class);
                    review_modelArrayList.add(model_review);
                }

                adapter=new ReviewRecyclerAdapter(review_modelArrayList,AllFeedbackPage.this);
                recycler_review.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}