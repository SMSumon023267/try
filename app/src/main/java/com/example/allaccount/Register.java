package com.example.allaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private EditText nameEditText,emailEditText,dateEditText,passwordEditText;
    private Button registerButton ,viewbutton;
    private TextView loginHereTextView;
    private ProgressBar progressBar;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setStatusBarColor(ContextCompat.getColor(Register.this,R.color.white));
        
        nameEditText = (EditText) findViewById(R.id.nameRegisterEditText_ID);
        emailEditText = (EditText) findViewById(R.id.emailRegisterEditText_ID);
        dateEditText = (EditText) findViewById(R.id.dateRegisterEditText_ID);
        passwordEditText = (EditText) findViewById(R.id.passwordRegisterEditText_ID);
        viewbutton = (Button) findViewById(R.id.viewButton_ID);


        
        registerButton = (Button) findViewById(R.id.registerButton_ID);
        loginHereTextView = (TextView) findViewById(R.id.loginHere_ID);
        
        progressBar = (ProgressBar) findViewById(R.id.progressBarRegister_ID);


        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateEditText.setText(dayOfMonth+"/" +(month + 1) + "/" +year);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        viewbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this,AllUserActivity.class);
                startActivity(intent);
            }
        });
        
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameRegister = nameEditText.getText().toString().trim();
                String emailRegister = emailEditText.getText().toString().trim();
                String dateRegister = dateEditText.getText().toString().trim();
                String passwordRegister = passwordEditText.getText().toString().trim();
                
                if (nameRegister.isEmpty()){
                    nameEditText.setError("Enter your ful name");
                    nameEditText.requestFocus();
                    return;
                } else if (emailRegister.isEmpty()) {
                    emailEditText.setError("Enter your Valid Email");
                    emailEditText.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailRegister).matches()) {
                    emailEditText.setError("Enter your Valid Email");
                    emailEditText.requestFocus();
                    return;
                } else if (dateRegister.isEmpty()) {
                    dateEditText.setError("Current Date");
                    dateEditText.requestFocus();
                    return;
                } else if (passwordRegister.isEmpty()) {
                    passwordEditText.setError("Enter Password");
                    passwordEditText.requestFocus();
                    return;
                }else if (passwordRegister.length() < 6) {
                    passwordEditText.setError("Enter 6 text Password ");
                    passwordEditText.requestFocus();
                    return;
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(nameRegister,emailRegister,dateRegister,passwordRegister);
                }

            }

            private void registerUser(String nameRegister, String emailRegister, String dateRegister, String passwordRegister) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(emailRegister,passwordRegister).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this,"Register Successful",Toast.LENGTH_SHORT).show();

                            FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                            ReadWriteRegisterUserDetails WriteRegisterUserDetails =new ReadWriteRegisterUserDetails(nameRegister,emailRegister,dateRegister,passwordRegister);
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Register Data");
                            databaseReference.child(firebaseUser.getUid()).setValue(WriteRegisterUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Register.this,"User Registered Successfully.please verify email ",Toast.LENGTH_SHORT).show();
                                    //open User Profile  Registered Successfully
                                    Intent intent = new Intent(Register.this,AllUserActivity.class);
                                    //To prevent User returning back to Register Activity no pressing   back after Register
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }else {
                            Toast.makeText(Register.this,"Register Not Successful",Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });




            }
        });
        
        
        
        
    }
}