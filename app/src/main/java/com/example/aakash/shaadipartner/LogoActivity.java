package com.example.aakash.shaadipartner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LogoActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginbtn;
    Button regbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        loginbtn = (Button) findViewById(R.id.loginBtn);
        regbtn = (Button) findViewById(R.id.regBtn);

        loginbtn.setOnClickListener(this);
        regbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view == loginbtn) {
//            finish();
            startActivity(new Intent(LogoActivity.this, LoginActivity.class));
            return;
        }

        if (view == regbtn) {
//            finish();
            startActivity(new Intent(LogoActivity.this, RegisterActivity.class));
            return;
        }

    }
}
