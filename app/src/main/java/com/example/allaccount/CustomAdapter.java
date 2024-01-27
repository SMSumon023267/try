package com.example.allaccount;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<ReadWriteRegisterUserDetails> {
    private Activity context;
    private List<ReadWriteRegisterUserDetails>readWriteRegisterUserDetailsList;

    public CustomAdapter(Activity context,List<ReadWriteRegisterUserDetails> readWriteRegisterUserDetailsList) {
        super(context, R.layout.sample_layout, readWriteRegisterUserDetailsList);
        this.context = context;
        this.readWriteRegisterUserDetailsList = readWriteRegisterUserDetailsList;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sample_layout,null,true);

        ReadWriteRegisterUserDetails ReadWriteRegisterUserDetails = readWriteRegisterUserDetailsList.get(position);
        TextView t1 = view.findViewById(R.id.nameSampleLayoutTextView_ID);
        TextView t2 = view.findViewById(R.id.dateSampleLayoutTextView_ID);


        Button viewButton = view.findViewById(R.id.viewButton_ID);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UserProfileActivity.class);
                context.startActivity(intent);
            }
        });
        Button editButton = view.findViewById(R.id.editButton_ID);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Login.class);
                context.startActivity(intent);
            }
        });

        t1.setText("Name : " + ReadWriteRegisterUserDetails.getNameRegister());
        t2.setText(ReadWriteRegisterUserDetails.getDateRegister());

        return view;
    }
}
