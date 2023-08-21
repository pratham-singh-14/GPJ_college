package com.user.gpjaunpur.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.user.gpjaunpur.MainActivity;
import com.user.gpjaunpur.R;

import maes.tech.intentanim.CustomIntent;

public class Login extends AppCompatActivity {
    private EditText login_email, login_password;
    private Button login_btn;
    private TextView forget_tv, signup_tv_btn;
    private String val_email, val_password;

    private FirebaseAuth auth;

    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        signup_tv_btn = findViewById(R.id.signupBtn);
        pd=new ProgressDialog(this);
        pd.setMessage("Please wait....");
        login_email = findViewById(R.id.log_email);
        login_password = findViewById(R.id.log_password);
        login_btn = findViewById(R.id.log_button);
        forget_tv = findViewById(R.id.forget);
        forget_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Forgetpassword.class));
                CustomIntent.customType(Login.this, "left-to-right");        //Animation  exchange for one activity to another activity
                }
             });
          signup_tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSign();

            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate_Data();


            }
        });

    }

    private void validate_Data() {
        val_email = login_email.getText().toString();
        val_password = login_password.getText().toString();
        if (val_email.isEmpty() || val_password.isEmpty()) {
            Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
        } else {
            login_user();
        }
    }


    private void login_user() {
        pd.show();
        pd.setCanceledOnTouchOutside(false);
        auth.signInWithEmailAndPassword(val_email, val_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    pd.dismiss();
                    openMain();
                } else {
                    pd.dismiss();
                    Toast.makeText(Login.this, "Incorrect email or password,please enter correct email and password", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();


            }
        });
    }

    private void openMain() {
        Intent intentm = new Intent(Login.this, MainActivity.class);
        startActivity(intentm);
        finish();
    }

    private void openSign() {
        startActivity(new Intent(Login.this, RegisterActivity.class));
        CustomIntent.customType(Login.this, "left-to-right");
        finish();
    }
}