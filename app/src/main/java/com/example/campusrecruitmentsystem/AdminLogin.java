package com.example.campusrecruitmentsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {


    Button login;
    TextInputEditText userName,password;
    DataSnapshot value = null;
    String username,pass;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String savedUserName = "savedUserName";
    public static final String savedPassword = "savedPassword" ;
    public SharedPreferences sharedPreferences;
    private static final String TAG = null ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_login);

        login = findViewById(R.id.login);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(sharedPreferences.getBoolean("adminlogged" , false)){
            username = sharedPreferences.getString(savedUserName,username);
            openApplications();

        }

        DatabaseReference myRef = database.getReference("adminCredentials");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = userName.getText().toString();
                pass = password.getText().toString();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    if (value.child(username).getValue().toString().equals(pass)) {


                        editor.putString(savedUserName, username);
                        editor.putString(savedPassword, pass);
                        editor.putBoolean("adminlogged",true);
                        editor.commit();
                        openApplications();
                    }
                    else {
                        Toast.makeText(AdminLogin.this, "enter correct credentials", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(AdminLogin.this, "Username not exist",Toast.LENGTH_LONG).show();
                }

            }
        });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot;

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void openApplications(){
        Intent i = new Intent(this ,AdminNavigationFragment.class);
        i.putExtra("username", username);
        startActivity(i);
        finish();
    }
}
