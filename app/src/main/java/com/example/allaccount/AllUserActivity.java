package com.example.allaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllUserActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseReference databaseReference;
    private List<ReadWriteRegisterUserDetails> readWriteRegisterUserDetailsList;
    private CustomAdapter customAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);

        databaseReference = FirebaseDatabase.getInstance().getReference("Register Data");


        readWriteRegisterUserDetailsList = new ArrayList<>();

        customAdapter = new CustomAdapter(AllUserActivity.this,readWriteRegisterUserDetailsList);

        progressBar = (ProgressBar) findViewById(R.id.progressBar_AllUserActivity_ID);

        listView =(ListView) findViewById(R.id.listView_ID);
    }

    @Override
    protected void onStart() {
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readWriteRegisterUserDetailsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ReadWriteRegisterUserDetails readWriteRegisterUserDetails = dataSnapshot.getValue(ReadWriteRegisterUserDetails.class);
                    readWriteRegisterUserDetailsList.add(readWriteRegisterUserDetails);

                }
                listView.setAdapter(customAdapter);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onStart();
    }
}