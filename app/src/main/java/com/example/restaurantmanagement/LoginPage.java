package com.example.restaurantmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    EditText ed_username,ed_password;
    RelativeLayout rl_login;
    DatabaseReference reference;
    String username,password;
    public static String type;
    boolean flag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ed_password=findViewById(R.id.ed_password);
        ed_username=findViewById(R.id.ed_username);
        rl_login=findViewById(R.id.rl_login);

        reference= FirebaseDatabase.getInstance().getReference();
       // CheckLoginType();

        rl_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Login();
            }
        });

    }

    private void Login() {
        if(ed_username.getText().toString().isEmpty())
        {
            Toast.makeText(LoginPage.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        }

    else if( ed_password.getText().toString().isEmpty())
        {
            Toast.makeText(LoginPage.this, "Fields can't be empty", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CheckLoginType();
        }
    }

    private void CheckLoginType() {
       reference.child("UserMaster").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Log.d("ssss",snapshot.toString());
                for (DataSnapshot ds:snapshot.getChildren())
                {
                   UserMasterModel masterModel=new UserMasterModel();
                    username=masterModel.setUsername(ds.child("username").getValue().toString());
                    password=masterModel.setPassword(ds.child("password").getValue().toString());
                    type=masterModel.setType(ds.child("type").getValue().toString());

                    if(ed_username.getText().toString().contentEquals(username) && ed_password.getText().toString().contentEquals(password))
                    {
                       // Toast.makeText(LoginPage.this, "login successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginPage.this,Dashboard.class);
                        intent.putExtra("TypeUser",type);
                        startActivity(intent);
                        break;
                    }
                    else
                    {
                        //Toast.makeText(LoginPage.this, "username & password does't match", Toast.LENGTH_SHORT).show();
                    }

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}