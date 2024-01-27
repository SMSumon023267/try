package com.example.allaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity {

    private TextView textView;
    private String name;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        textView = (TextView) findViewById(R.id.viewTextView_ID);
        progressBar = (ProgressBar) findViewById(R.id.userProfileProgressBar_ID);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            Toast.makeText(UserProfileActivity.this,"Something wont wrong! User's details are not available at the moment  " ,Toast.LENGTH_SHORT).show();
        }else {
            showUserProfile(firebaseUser);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    private void showUserProfile(FirebaseUser firebaseUser) {

        String userID = firebaseUser.getUid();
        //Extraction User Reference from DataBase for "Registered Users"
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register Data");
        databaseReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteRegisterUserDetails readUserDetails = snapshot.getValue(ReadWriteRegisterUserDetails.class);
                if (readUserDetails !=null){
                    name = firebaseUser.getEmail();
                    textView.setText(name);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this,"No Data",Toast.LENGTH_SHORT).show();

            }
        });
    }
}