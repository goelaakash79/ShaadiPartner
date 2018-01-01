package com.example.aakash.shaadipartner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Button logoutBtn;
    TextView userEmail;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    Button saveInfoBtn;
    EditText userNameedt;
    EditText addressEdt;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logoutBtn = (Button) findViewById(R.id.logoutButton);
        userEmail = (TextView) findViewById(R.id.userEmailTextView);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            //start login activity
            finish();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        userEmail.setText("Welcome " + user.getEmail());

        logoutBtn.setOnClickListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        userNameedt = (EditText) findViewById(R.id.info_name_editText);
        addressEdt = (EditText) findViewById(R.id.info_address_editText);
        saveInfoBtn = (Button) findViewById(R.id.save_info_button);

        saveInfoBtn.setOnClickListener(this);
    }

    private void saveUserInfo(){
        //we need a java object, so we make UserInformation class
        String name = userNameedt.getText().toString().trim();
        String address = addressEdt.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name, address);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "Information Saved..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == logoutBtn){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            Toast.makeText(this, "User is Logged Out", Toast.LENGTH_SHORT).show();
        }

        if (view == saveInfoBtn){
            saveUserInfo();
        }
    }
}
