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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    EditText emailedt;
    EditText passwordedt;
    Button loginBtn;
    TextView regTxt;

    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        emailedt = (EditText) findViewById(R.id.email_editText);
        passwordedt = (EditText) findViewById(R.id.password_editText);

        loginBtn = (Button) findViewById(R.id.loginButton);

        progressDialog = new ProgressDialog(this);

        regTxt = (TextView) findViewById(R.id.signUpTextView);

        if (firebaseAuth.getCurrentUser() != null){
            //start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }


        loginBtn.setOnClickListener(this);

        regTxt.setOnClickListener(this);

    }

    private void userlogin(){
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
        progressDialog.setMessage("logging in user..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if (task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });

    }

    @Override
    public void onClick(View view) {
        if (view == loginBtn){
            userlogin();
        }

        if (view == regTxt){
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        }
    }
}
