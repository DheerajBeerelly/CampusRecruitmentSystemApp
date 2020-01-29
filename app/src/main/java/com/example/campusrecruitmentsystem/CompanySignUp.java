package com.example.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompanySignUp extends AppCompatActivity {
    private TextInputEditText companyName;
    private TextInputEditText address;
    private TextInputEditText city;
    private TextInputEditText contact;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private Button registerSignUp;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_activity_sign_up);


        companyName = findViewById(R.id.companyname);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        contact = findViewById(R.id.contact);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        registerSignUp = findViewById(R.id.register);


        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //when clicked on register button validate and save data to database
        registerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean valid = validate(companyName.getText().toString(),
                        address.getText().toString(),city.getText().toString(),contact.getText().toString(),
                        password.getText().toString(),confirmPassword.getText().toString());


                if(valid) {
                    DatabaseReference compRef = database.getReference("companies/" + companyName.getText().toString()+"/profile");
                    compRef.child("companyName").setValue(companyName.getText().toString());
                    compRef.child("address").setValue(address.getText().toString());
                    compRef.child("city").setValue(city.getText().toString());
                    compRef.child("contact").setValue(contact.getText().toString());
                    compRef.child("password").setValue(password.getText().toString());
                    compRef = database.getReference("companiesToBeRegistered");
                    compRef.child(companyName.getText().toString()).setValue(password.getText().toString());
                    Toast.makeText(CompanySignUp.this,"Registration Sucessfull \n wait for Confirmation",Toast.LENGTH_LONG).show();
                    openlogin();
                }
                else{
                    Toast.makeText(CompanySignUp.this,"Correct the details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void openlogin(){
        Intent i = new Intent(this,CompanyLogin.class );
        startActivity(i);
        finish();
    }
    public boolean validate(String name, String address, String city, String contact, String password , String confirmPassword){
        if(name.length()==0 ){
            companyName.requestFocus();
            companyName.setError("Field cannot be empty");
            return false;
        }
        else if(!name.matches("[a-zA-Z]+")){
            companyName.requestFocus();
            companyName.setError("Enter Alphabets only");
            return false;
        }
        else if(address.length()==0){
            this.address.requestFocus();
            this.address.setError("Field cannot be empty");
            return false;
        }
        else if(city.length()==0){
            this.city.requestFocus();
            this.city.setError("Field cannot be empty");
            return false;
        }
        else if(!(contact.length()==10)){
            this.contact.requestFocus();
            this.contact.setError("Enter correct Number");
            return false;
        }
        else if(password.length()==0){
            this.password.requestFocus();
            this.password.setError("Field cannot be empty");
            return false;
        }
        else if(confirmPassword.length()==0){
            this.confirmPassword.requestFocus();
            this.confirmPassword.setError("Field cannot be empty");
            return false;
        }
        else if(!password.equals(confirmPassword)){
            this.confirmPassword.requestFocus();
            this.confirmPassword.setError("password did not match");
            return false;
        }
        else{
            return true;
        }
    }
}
