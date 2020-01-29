package com.example.campusrecruitmentsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class StudentAcademicDetails extends AppCompatActivity {

    TextInputEditText sscPercentage , sscBoard, sscYear;
    TextInputEditText higherPercentage , higherBoard , higherYear;
    TextInputEditText courseName,board,sem1,sem2,sem3,sem4,sem5,sem6,sem7,sem8,
            sem1Year,sem2Year,sem3Year,sem4Year,sem5Year,sem6Year,sem7Year,sem8Year;
    Button update;
    String userName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference compRef1,compRef,myRef;
    DataSnapshot valuessc,valuehigher,valuegrad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity_academic_details);

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
        update = findViewById(R.id.update);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        userName = i.getStringExtra("username");
        //Toast.makeText(StudentAcademicDetails.this,userName,Toast.LENGTH_LONG).show();

        compRef1 = database.getReference("students/" + userName+"/profile/academicDetails/ssc");
        myRef = database.getReference("students/" + userName+"/profile/academicDetails/higher");
        compRef = database.getReference("students/" + userName+"/profile/academicDetails/graduation");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(StudentAcademicDetails.this);
                builder.setMessage("Are you sure to update details")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                boolean valid = validate(sscPercentage.getText().toString(),
                        sscBoard.getText().toString(),sscYear.getText().toString(),higherPercentage.getText().toString()
                        ,higherBoard.getText().toString(),higherYear.getText().toString(),courseName.getText().toString(), board.getText().toString(),
                        sem1.getText().toString(),sem2.getText().toString(),sem3.getText().toString(),
                        sem4.getText().toString(),sem5.getText().toString(),sem6.getText().toString(),
                        sem1Year.getText().toString(),sem2Year.getText().toString(),sem3Year.getText().toString()
                        ,sem4Year.getText().toString(),sem5Year.getText().toString(),sem6Year.getText().toString());

                if(valid) {

                    try {
                        compRef1.child("sscPercentage").setValue(sscPercentage.getText().toString());
                        compRef1.child("sscBoard").setValue(sscBoard.getText().toString());
                        compRef1.child("sscYear").setValue(sscYear.getText().toString());
                    }
                    catch(Exception e){
                        Toast.makeText(StudentAcademicDetails.this,"Please\nupdate Academic \ndetails",Toast.LENGTH_LONG ).show();
                    }

                    try {
                        myRef.child("higherPercentage").setValue(higherPercentage.getText().toString());
                        myRef.child("higherBoard").setValue(higherBoard.getText().toString());
                        myRef.child("higherYear").setValue(higherYear.getText().toString());

                    }
                    catch(Exception e){
                        Toast.makeText(StudentAcademicDetails.this,"Please\nupdate Academic \ndetails",Toast.LENGTH_LONG ).show();
                    }
                    try {
                        compRef.child("courseName").setValue(courseName.getText().toString());
                        compRef.child("university").setValue(board.getText().toString());
                        compRef.child("sem1").setValue(sem1.getText().toString());
                        compRef.child("sem2").setValue(sem2.getText().toString());
                        compRef.child("sem3").setValue(sem3.getText().toString());
                        compRef.child("sem4").setValue(sem4.getText().toString());
                        compRef.child("sem5").setValue(sem5.getText().toString());
                        compRef.child("sem6").setValue(sem6.getText().toString());
                        compRef.child("sem7").setValue(sem7.getText().toString());
                        compRef.child("sem8").setValue(sem8.getText().toString());
                        compRef.child("sem1Year").setValue(sem1Year.getText().toString());
                        compRef.child("sem2Year").setValue(sem2Year.getText().toString());
                        compRef.child("sem3Year").setValue(sem3Year.getText().toString());
                        compRef.child("sem4Year").setValue(sem4Year.getText().toString());
                        compRef.child("sem5Year").setValue(sem5Year.getText().toString());
                        compRef.child("sem6Year").setValue(sem6Year.getText().toString());
                        compRef.child("sem7Year").setValue(sem7Year.getText().toString());
                        compRef.child("sem8Year").setValue(sem8Year.getText().toString());
                    }
                    catch(Exception e){
                        //Toast.makeText(StudentAcademicDetails.this,"Please\nupdate Academic \ndetails",Toast.LENGTH_LONG ).show();
                    }

                    Toast.makeText(StudentAcademicDetails.this,"Updation\n Sucessfull",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(StudentAcademicDetails.this,"Correct the details",Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        compRef1.addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(StudentAcademicDetails.this,"Please\nupdate Secondary \ndetails",Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(StudentAcademicDetails.this,"Please\nupdate higher \ndetails",Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        compRef.addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(StudentAcademicDetails.this,"Please\nupdate Graduation \ndetails",Toast.LENGTH_LONG ).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public boolean validate(String sscPercentage, String sscBoard, String sscYear, String higherPercentage,
                            String higherBoard, String higherYear,String courseName, String university, String sem1, String sem2, String sem3,
                            String sem4, String sem5, String sem6, String sem1Year, String sem2Year,
                            String sem3Year, String sem4Year, String sem5Year, String sem6Year){
        if(sscPercentage.length()==0 ){
            this.sscPercentage.requestFocus();
            this.sscPercentage.setError("Field cannot be empty");
            return false;
        }

        else if(sscBoard.length()==0){
            this.sscBoard.requestFocus();
            this.sscBoard.setError("Field cannot be empty");
            return false;
        }
        else if(sscYear.length()==0){
            this.sscYear.requestFocus();
            this.sscYear.setError("Field cannot be empty");
            return false;
        }
        else if(higherPercentage.length()==0){
            this.higherPercentage.requestFocus();
            this.higherPercentage.setError("Field cannot be empty");
            return false;
        }
        else if(higherBoard.length()==0){
            this.higherBoard.requestFocus();
            this.higherBoard.setError("Field cannot be empty");
            return false;
        }
        else if(higherYear.length()==0){
            this.higherYear.requestFocus();
            this.higherYear.setError("Field cannot be empty");
            return false;
        }
        else if(courseName.length()==0 ){
            this.courseName.requestFocus();
            this.courseName.setError("Field cannot be empty");
            return false;
        }

        else if(university.length()==0){
            this.board.requestFocus();
            this.board.setError("Field cannot be empty");
            return false;
        }
        else if(sem1.length()==0){
            this.sem1.requestFocus();
            this.sem1.setError("Field cannot be empty");
            return false;
        }
        else if(sem2.length()==0){
            this.sem2.requestFocus();
            this.sem2.setError("Field cannot be empty");
            return false;
        }
        else if(sem3.length()==0){
            this.sem3.requestFocus();
            this.sem3.setError("Field cannot be empty");
            return false;
        }
        else if(sem4.length()==0){
            this.sem4.requestFocus();
            this.sem4.setError("Field cannot be empty");
            return false;
        }
        else if(sem5.length()==0){
            this.sem5.requestFocus();
            this.sem5.setError("Field cannot be empty");
            return false;
        }
        else if(sem6.length()==0){
            this.sem6.requestFocus();
            this.sem6.setError("Field cannot be empty");
            return false;
        }
        else if(sem1Year.length()==0){
            this.sem1Year.requestFocus();
            this.sem1Year.setError("Field cannot be empty");
            return false;
        }
        else if(sem2Year.length()==0){
            this.sem2Year.requestFocus();
            this.sem2Year.setError("Field cannot be empty");
            return false;
        }
        else if(sem3Year.length()==0){
            this.sem3Year.requestFocus();
            this.sem3Year.setError("Field cannot be empty");
            return false;
        }
        else if(sem4Year.length()==0){
            this.sem4Year.requestFocus();
            this.sem4Year.setError("Field cannot be empty");
            return false;
        }
        else if(sem5Year.length()==0){
            this.sem5Year.requestFocus();
            this.sem5Year.setError("Field cannot be empty");
            return false;
        }
        else if(sem6Year.length()==0){
            this.sem6Year.requestFocus();
            this.sem6Year.setError("Field cannot be empty");
            return false;
        }
        else{
            return true;
        }
    }
}
