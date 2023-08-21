package com.user.gpjaunpur.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.user.gpjaunpur.MainActivity;
import com.user.gpjaunpur.R;
import com.user.gpjaunpur.splashscreen;

import java.util.HashMap;

import maes.tech.intentanim.CustomIntent;

public class RegisterActivity extends AppCompatActivity {

    private EditText regname,regemail,regpassword,regbranch;
    private Button register;
    private String name,email,password,branch;
    private FirebaseAuth auth;
    private DatabaseReference dbref,reference;
    private ProgressDialog pd;
    private TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();     //auth
        reference= FirebaseDatabase.getInstance().getReference();
        pd=new ProgressDialog(this);

        pd.setMessage("Please wait...");
       loginBtn=findViewById(R.id.loginBtn);
        regname=findViewById(R.id.regName);
        regpassword=findViewById(R.id.regPassword);
        regemail=findViewById(R.id.regEmail);
        regbranch=findViewById(R.id.regBranch);
        register=findViewById(R.id.register);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });



    }

    private void openLogin() {
        startActivity(new Intent(RegisterActivity.this,Login.class));
        CustomIntent.customType(RegisterActivity.this,"right-to-left");
          finish();
    }


    //auth
   /* @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() != null){
            openMain();
        }
    }

    private void openMain() {

        startActivity(new Intent(this, Login.class));
        finish();

    }*/

    private void validateData() {
        name=regname.getText().toString();
        email=regemail.getText().toString();
        password=regpassword.getText().toString();
        branch=regbranch.getText().toString();
        if(name.isEmpty()){
            regname.setError(" Enter name!");
            regname.requestFocus();
        }else if(email.isEmpty()){
            regemail.setError(" Enter email!");
            regemail.requestFocus();

        }else if(password.isEmpty()){
            regpassword.setError("Enter password");
            regpassword.requestFocus();

        }else if(branch.isEmpty()){
            regbranch.setError("Enter branch name!");
            regbranch.requestFocus();
        }else
        {
            Createuser();
        }
    }

    private void Createuser() {
        pd.show();
        pd.setCanceledOnTouchOutside(false);

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            uploadData();

                        }else{
                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this,""+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();

                }
            });


    }

    private void uploadData() {
        String key;
        dbref= reference.child("Users");
        key=dbref.push().getKey();

        HashMap<String,String> users=new HashMap<>();
        users.put("key",key);
        users.put("Name",name);
        users.put("Email",email);
        users.put("Password",password);
        users.put("Branch",branch);
        dbref.child(key).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();


                }else{
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "Something went wrong! please try again", Toast.LENGTH_SHORT).show();
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