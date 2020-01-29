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

public class StudentSignUp extends AppCompatActivity {
    private TextInputEditText studentName;
    private TextInputEditText idNumber;
    private TextInputEditText dob;
    private TextInputEditText city;
    private TextInputEditText email;
    private TextInputEditText contact;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private Button registerSignUp;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        studentName = findViewById(R.id.studentname);
        idNumber = findViewById(R.id.idNumber);
        dob = findViewById(R.id.dob);
        city = findViewById(R.id.city);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        registerSignUp = findViewById(R.id.register);

        //when clicked on register button validate and save data to database
        registerSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean valid = validate(studentName.getText().toString(),
                        idNumber.getText().toString(), email.getText().toString(),contact.getText().toString(),
                        password.getText().toString(),confirmPassword.getText().toString(),dob.getText().toString()
                ,city.getText().toString());


                if(valid) {
                    DatabaseReference compRef = database.getReference("students/" + idNumber.getText().toString()+"/profile");
                    compRef.child("studentName").setValue(studentName.getText().toString());
                    compRef.child("idNumber").setValue(idNumber.getText().toString());
                    compRef.child("email").setValue(email.getText().toString());
                    compRef.child("contact").setValue(contact.getText().toString());
                    compRef.child("password").setValue(password.getText().toString());
                    compRef.child("dob").setValue(dob.getText().toString());
                    compRef.child("city").setValue(city.getText().toString());
                    compRef = database.getReference("studentsToBeRegistered");
                    compRef.child(idNumber.getText().toString()).setValue(password.getText().toString());
                    Toast.makeText(StudentSignUp.this,"Registration Sucessfull \n wait for Confirmation",Toast.LENGTH_LONG).show();
                    openlogin();
                }
                else{
                    Toast.makeText(StudentSignUp.this,"Correct the details",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void openlogin(){
        Intent i = new Intent(this,StudentLogin.class );
        startActivity(i);
        finish();
    }
    public boolean validate(String name, String idNumber, String email, String contact,
                            String password , String confirmPassword, String dob, String city){
        if(name.length()==0 ){
            studentName.requestFocus();
            studentName.setError("Field cannot be empty");
            return false;
        }
        else if(!name.matches("[a-zA-Z]+")){
            studentName.requestFocus();
            studentName.setError("Enter Alphabets only");
            return false;
        }
        else if(idNumber.length()==0){
            this.idNumber.requestFocus();
            this.idNumber.setError("Field cannot be empty");
            return false;
        }
        else if(email.length()==0){
            this.email.requestFocus();
            this.email.setError("Field cannot be empty");
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
        else if(city.length()==0){
            this.city.requestFocus();
            this.city.setError("Field cannot be empty");
            return false;
        }
        else if(dob.length()==0){
            this.dob.requestFocus();
            this.dob.setError("Field cannot be empty");
            return false;
        }
        else{
            return true;
        }
    }
}
