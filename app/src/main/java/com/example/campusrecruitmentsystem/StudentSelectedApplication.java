package com.example.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StudentSelectedApplication extends AppCompatActivity {

    TextView jobDescription, jobRequirement, keyResponsibilities, criteria, salaryRange ,jobTitle;
    TextView studentName, idNumber, email,dob ,contact,city,sex;
    TextView sscPercentage , sscBoard, sscYear;
    TextView higherPercentage , higherBoard , higherYear;
    TextView courseName,board,sem1,sem2,sem3,sem4,sem5,sem6,sem7,sem8,
            sem1Year,sem2Year,sem3Year,sem4Year,sem5Year,sem6Year,sem7Year,sem8Year;
    String userName,id,selectedJob,companyName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference jobRef,sscRef,higherRef,gradRef, personalref;
    DataSnapshot valuessc,valuehigher,valuegrad,valuepersonal,value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_selected_application);

        jobTitle = findViewById(R.id.jobTitle);
        jobDescription = findViewById(R.id.jobDescription);
        jobRequirement = findViewById(R.id.jobRequirements);
        keyResponsibilities = findViewById(R.id.keyResponsibilities);
        criteria = findViewById(R.id.criteria);
        salaryRange = findViewById(R.id.salaryRange);
        studentName = findViewById(R.id.studentName);
        dob =findViewById(R.id.dob);
        city =findViewById(R.id.city);
        idNumber = findViewById(R.id.idNumber);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        sscPercentage = findViewById(R.id.sscPercentage);
        sscBoard = findViewById(R.id.sscBoard);
        sscYear = findViewById(R.id.sscYear);
        higherPercentage = findViewById(R.id.higherPercentage);
        higherBoard = findViewById(R.id.higherBoard);
        higherYear = findViewById(R.id.higherYear);
        courseName = findViewById(R.id.courseName);
        board = findViewById(R.id.graduationBoard);
        sem1 = findViewById(R.id.sem1Per);
        sem2 = findViewById(R.id.sem2Per);
        sem3 = findViewById(R.id.sem3Per);
        sem4 = findViewById(R.id.sem4Per);
        sem5 = findViewById(R.id.sem5Per);
        sem6 = findViewById(R.id.sem6Per);
        sem7 = findViewById(R.id.sem7Per);
        sem8 = findViewById(R.id.sem8Per);
        sem1Year = findViewById(R.id.sem1Year);
        sem2Year = findViewById(R.id.sem2Year);
        sem3Year = findViewById(R.id.sem3Year);
        sem4Year = findViewById(R.id.sem4Year);
        sem5Year = findViewById(R.id.sem5Year);
        sem6Year = findViewById(R.id.sem6Year);
        sem7Year = findViewById(R.id.sem7Year);
        sem8Year = findViewById(R.id.sem8Year);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        userName = i.getStringExtra("username");
        selectedJob = i.getStringExtra("selectedJob");
        companyName = i.getStringExtra("companyName");
         //Toast.makeText(StudentSelectedApplication.this,companyName,Toast.LENGTH_LONG).show();

        jobRef = database.getReference("companies/"+ companyName+"/job/"+selectedJob);

        jobRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot;
                try {
                    jobTitle.setText(value.child("jobTitle").getValue().toString());
                    jobDescription.setText(value.child("jobDescription").getValue().toString());
                    jobRequirement.setText(value.child("jobRequirement").getValue().toString());
                    keyResponsibilities.setText(value.child("keyResponsibilities").getValue().toString());
                    salaryRange.setText(value.child("salaryRange").getValue().toString());
                    criteria.setText(value.child("criteria").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(StudentSelectedApplication.this,"error loading",Toast.LENGTH_LONG );
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        personalref= database.getReference("students/" + userName+"/profile");

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
                    Toast.makeText(StudentSelectedApplication.this,e.toString(),Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        sscRef = database.getReference("students/" + userName+"/profile/academicDetails/ssc");

        sscRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                valuessc = dataSnapshot;
                try {
                    sscPercentage.setText(valuessc.child("sscPercentage").getValue().toString());
                    sscBoard.setText(valuessc.child("sscBoard").getValue().toString());
                    sscYear.setText(valuessc.child("sscYear").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(StudentSelectedApplication.this,"Student Not\nupdated Academic \ndetails",Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


        higherRef = database.getReference("students/" + userName+"/profile/academicDetails/higher");

        higherRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                valuehigher = dataSnapshot;
                try {
                    higherPercentage.setText(valuehigher.child("higherPercentage").getValue().toString());
                    higherBoard.setText(valuehigher.child("higherBoard").getValue().toString());
                    higherYear.setText(valuehigher.child("higherYear").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(StudentSelectedApplication.this,"Student Not\nupdated Academic \ndetails",Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });



        gradRef = database.getReference("students/" + userName+"/profile/academicDetails/graduation");

        gradRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                valuegrad = dataSnapshot;
                try {
                    courseName.setText(valuegrad.child("courseName").getValue().toString());
                    board.setText(valuegrad.child("university").getValue().toString());
                    sem1.setText(valuegrad.child("sem1").getValue().toString());
                    sem1Year.setText(valuegrad.child("sem1Year").getValue().toString());
                    sem2.setText(valuegrad.child("sem2").getValue().toString());
                    sem2Year.setText(valuegrad.child("sem2Year").getValue().toString());
                    sem3.setText(valuegrad.child("sem3").getValue().toString());
                    sem3Year.setText(valuegrad.child("sem3Year").getValue().toString());
                    sem4.setText(valuegrad.child("sem4").getValue().toString());
                    sem4Year.setText(valuegrad.child("sem4Year").getValue().toString());
                    sem5.setText(valuegrad.child("sem5").getValue().toString());
                    sem5Year.setText(valuegrad.child("sem5Year").getValue().toString());
                    sem6.setText(valuegrad.child("sem6").getValue().toString());
                    sem6Year.setText(valuegrad.child("sem6Year").getValue().toString());
                    sem7.setText(valuegrad.child("sem7").getValue().toString());
                    sem7Year.setText(valuegrad.child("sem7Year").getValue().toString());
                    sem8.setText(valuegrad.child("sem8").getValue().toString());
                    sem8Year.setText(valuegrad.child("sem8Year").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(StudentSelectedApplication.this,"Student Not\nupdated Academic \ndetails",Toast.LENGTH_LONG ).show();
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
