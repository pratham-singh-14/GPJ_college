package com.user.gpjaunpur.authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.user.gpjaunpur.MainActivity;
import com.user.gpjaunpur.R;

import maes.tech.intentanim.CustomIntent;

public class Forgetpassword extends AppCompatActivity {
    private EditText forgetemail;
    private Button forgetbutton;
    private String email;
    private FirebaseAuth auth;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        auth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        pd.setMessage("Please wait..");
        forgetbutton=findViewById(R.id.forgetbutton);
        forgetemail=findViewById(R.id.forgetemail);
        forgetemail=findViewById(R.id.forgetemail);
        forgetbutton=findViewById(R.id.forgetbutton);
        forgetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void validateData() {
       email=forgetemail.getText().toString();
       if(email.isEmpty()){
           forgetemail.setError("Enter email!");
           forgetemail.requestFocus();
       }else{
           pd.show();
           pd.setCanceledOnTouchOutside(false);
           forgetpass();
       }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        CustomIntent.customType(Forgetpassword.this,"right-to-left");
    }

    private void forgetpass() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(Forgetpassword.this,  "please check your email! ", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Forgetpassword.this,Login.class));
                    CustomIntent.customType(Forgetpassword.this,"right-to-left");
                    finish();
                }else {
                    pd.dismiss();
                    Toast.makeText(Forgetpassword.this, "Something went wrong! Please try again later", Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();


            }
        });


    }
}