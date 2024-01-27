package com.example.allaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText emailLoginEditText,passwordLoginEditText;
    private Button loginButton;
    private ProgressBar loginProgressBar;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = "Login";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        emailLoginEditText = (EditText) findViewById(R.id.email_Login_EditText_ID);
        passwordLoginEditText = (EditText) findViewById(R.id.password_Login_EditText_ID);
        loginProgressBar = (ProgressBar) findViewById(R.id.login_progressBar_ID);
        loginButton = (Button) findViewById(R.id.login_Button_ID);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = emailLoginEditText.getText().toString();
                String textPassword =passwordLoginEditText.getText().toString();

                if (textEmail.isEmpty()){
                    emailLoginEditText.setError("Email is required");
                    emailLoginEditText.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    emailLoginEditText.setError("Valid email is required");
                    emailLoginEditText.requestFocus();
                    return;
                }
                if (textPassword.isEmpty()){
                    passwordLoginEditText.setError("Please enter your password");
                    passwordLoginEditText.requestFocus();
                    return;
                }
                else {
                    loginProgressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPassword);
                }
            }

            private void loginUser(String textEmail, String textPassword) {

                firebaseAuth.signInWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this,"You are logged new",Toast.LENGTH_SHORT).show();

                            //Get instance of the current User
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            //open User Profile  Registered Successfully
                            Intent intent = new Intent(Login.this,UserProfileActivity.class);
                            //To prevent User returning back to Register Activity no pressing   back after Register
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                            //Check of the email verified before user can access their profile
                            if (firebaseUser.isEmailVerified()){
                                Toast.makeText(Login.this,"You are logged new",Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(Login.this,"Something went wrong! ",Toast.LENGTH_SHORT).show();

                            try {
                                throw task.getException();
                            }catch (FirebaseAuthInvalidUserException e){
                                emailLoginEditText.setError("User does not exists or is no longer valid. Please Register again");
                                loginProgressBar.setVisibility(View.GONE);
                                emailLoginEditText.requestFocus();
                                loginProgressBar.setVisibility(View.GONE);

                                return;
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                emailLoginEditText.setError("Invalid credentials. Kindly,check and re-enter");
                                emailLoginEditText.requestFocus();
                                loginProgressBar.setVisibility(View.GONE);

                                return;
                            }catch (Exception e){
                                Log.e(TAG,e.getMessage());
                                Toast.makeText(Login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                loginProgressBar.setVisibility(View.GONE);

                            }
                            loginProgressBar.setVisibility(View.GONE);

                        }
                        loginProgressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
}