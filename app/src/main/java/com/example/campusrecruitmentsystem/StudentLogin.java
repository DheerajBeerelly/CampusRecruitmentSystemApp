package com.example.campusrecruitmentsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLogin extends AppCompatActivity {

    private static final String TAG = null ;
    Button login;
    TextInputEditText userName,password;
    TextView signUp;
    DataSnapshot value = null;
    String username,pass;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String savedUserName = "savedUserName";
    public static final String savedPassword = "savedPassword" ;
    public SharedPreferences sharedPreferences;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_login);
        login = findViewById(R.id.login);
        userName = findViewById(R.id.username);
        password = findViewById(R.id.password);
        signUp = findViewById(R.id.signUp);



        //Hyperlink for the signup page
        String text = "If not registered SignUp!";
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                openSignUp();
            }
        };
        ss.setSpan(clickableSpan1, 18 , 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signUp.setText(ss);
        signUp.setMovementMethod(LinkMovementMethod.getInstance());

        DatabaseReference myRef = database.getReference("studentsLogin");

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);





        if(sharedPreferences.getBoolean("studentlogged" , false)){
            username = sharedPreferences.getString(savedUserName,username);
            openApplications();
        }




        //when clicked login
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
                        editor.putBoolean("studentlogged",true);
                        editor.commit();
                        openApplications();
                    }
                    else {
                        Toast.makeText(StudentLogin.this, "enter correct credentials", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(StudentLogin.this, "Username not exist",Toast.LENGTH_LONG).show();
                }

            }
        });

        // Read from the database
        //
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
    public void openSignUp(){
        Intent i = new Intent(this,StudentSignUp.class );
        startActivity(i);
    }
    public void openApplications(){
            Intent i = new Intent(this, StudentNavigationFragment.class);
            i.putExtra("username", username);
            startActivity(i);
            finish();
    }

}
