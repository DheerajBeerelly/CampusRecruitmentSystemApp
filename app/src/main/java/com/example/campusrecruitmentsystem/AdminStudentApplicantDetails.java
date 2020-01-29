package com.example.campusrecruitmentsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdminStudentApplicantDetails extends AppCompatActivity {


    TextView studentName, idNumber, email,dob ,contact,city,sex;
    String userName,id,companyName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference personalref,loginref;
    DataSnapshot valuepersonal,value;
    Button confirm,reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_student_applicant_details);
        studentName = findViewById(R.id.studentName);
        dob =findViewById(R.id.dob);
        city =findViewById(R.id.city);
        idNumber = findViewById(R.id.idNumber);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        confirm = findViewById(R.id.confirm);
        reject = findViewById(R.id.reject);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        userName = i.getStringExtra("username");
        final String password = i.getStringExtra("password");
        personalref= database.getReference("students/" + userName+"/profile");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminStudentApplicantDetails.this);
                builder.setMessage("Are you sure to Confirm")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                loginref= database.getReference("studentsLogin");
                loginref.child(userName).setValue(password);
                loginref = database.getReference("studentsToBeRegistered/"+userName);
                loginref.removeValue();
                Toast.makeText(AdminStudentApplicantDetails.this,"CONFIRMED",Toast.LENGTH_LONG ).show();
                            }
                        }).setNegativeButton("cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminStudentApplicantDetails.this);
                builder.setMessage("Are you sure to Reject")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loginref = database.getReference("studentsToBeRegistered/"+userName);
                                loginref.removeValue();
                                loginref = database.getReference("students/"+userName);
                                loginref.removeValue();
                                Toast.makeText(AdminStudentApplicantDetails.this, "REJECTED", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });






        personalref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                valuepersonal = dataSnapshot;
                try {
                    studentName.setText(valuepersonal.child("studentName").getValue().toString());
                    idNumber.setText(valuepersonal.child("idNumber").getValue().toString());
                    dob.setText(valuepersonal.child("dob").getValue().toString());
                    email.setText(valuepersonal.child("email").getValue().toString());
                    contact.setText(valuepersonal.child("contact").getValue().toString());
                    // sex.setText(valuepersonal.child("sex").getValue().toString());
                    city.setText(valuepersonal.child("city").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(AdminStudentApplicantDetails.this,e.toString(),Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });





    }
}
