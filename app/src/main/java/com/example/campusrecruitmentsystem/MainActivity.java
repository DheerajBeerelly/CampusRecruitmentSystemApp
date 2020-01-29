package com.example.campusrecruitmentsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.campusrecruitmentsystem.CompanyLogin.savedUserName;
//import static com.example.crt.CompanyLogin.java.savedUserName;


public class MainActivity extends AppCompatActivity {

    Button adminLogin , companyLogin , studentLogin;
    SharedPreferences sharedPreferences;
    String username;
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("logged" , false)){
            username = sharedPreferences.getString(savedUserName,username);
           openApplications();
        }
        if(sharedPreferences.getBoolean("studentlogged" , false)){
            username = sharedPreferences.getString(savedUserName,username);
            openApplications1();
        }
        if(sharedPreferences.getBoolean("adminlogged" , false)){
            username = sharedPreferences.getString(savedUserName,username);
            openApplications2();
        }

        adminLogin = findViewById(R.id.adminLogin);
        companyLogin = findViewById(R.id.companyLogin);
        studentLogin = findViewById(R.id.studentLogin);

        companyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CompanyLogin.class);
                startActivity(i);

            }
        });
        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, StudentLogin.class);
                startActivity(i);

            }
        });
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AdminLogin.class);
                startActivity(i);
            }
        });
    }
    public void openApplications(){
        Intent i = new Intent(this ,CompanyNavigationFragment.class);
        i.putExtra("username", username);
        startActivity(i);
        finish();
    }
    public void openApplications1(){
        Intent i = new Intent(this ,StudentNavigationFragment.class);
        i.putExtra("username", username);
        startActivity(i);
        finish();
    }
    public void openApplications2(){
        Intent i = new Intent(this ,AdminNavigationFragment.class);
        i.putExtra("username", username);
        startActivity(i);
        finish();
    }
}
