package com.example.aakash.shaadipartner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText emailedt;
    EditText passwordedt;
    Button regButton;
    TextView signIn;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();  //intializes firebase object

        progressDialog = new ProgressDialog(this);

        emailedt = (EditText) findViewById(R.id.email_editText);
        passwordedt = (EditText) findViewById(R.id.password_editText);

        regButton = (Button) findViewById(R.id.registerButton);

        signIn = (TextView) findViewById(R.id.signInTextView);

        regButton.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    private void registerUser(){
        String email = emailedt.getText().toString().trim();
        String password = passwordedt.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter Email", Toast.LENGTH_SHORT).show();
            //stopping further executon
            return;
        }

        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            //stopping further execution
            return;
        }

        //if validations are ok
        //we will show a progressbar
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            //user is successfully registered and logged in
                            //we will start the profile activity here
                            Toast.makeText(RegisterActivity.this, "User, registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == regButton){
            registerUser();
        }

        if (view == signIn){
            //direct to login activity
//            finish();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        }
    }
}
