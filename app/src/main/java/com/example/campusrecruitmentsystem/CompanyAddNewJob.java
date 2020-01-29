package com.example.campusrecruitmentsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CompanyAddNewJob extends AppCompatActivity {

    TextInputEditText jobTitle, jobDescription, jobRequirement, keyResponsibilities, criteria, salaryRange;
    Button save;
    String username;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_activity_add_new_job);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");




        jobTitle = findViewById(R.id.jobTitle);
        jobDescription = findViewById(R.id.jobDescription);
        jobRequirement = findViewById(R.id.jobRequirements);
        keyResponsibilities = findViewById(R.id.keyResponsibilities);
        criteria = findViewById(R.id.criteria);
        salaryRange = findViewById(R.id.salaryRange);
        save = findViewById(R.id.saveJob);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CompanyAddNewJob.this);
                builder.setMessage("Are you sure to add new job")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

               boolean valid = validate(jobTitle.getText().toString(),
                        jobDescription.getText().toString(),jobRequirement.getText().toString(),keyResponsibilities.getText().toString(),
                        criteria.getText().toString(),salaryRange.getText().toString());
                if(valid) {
                    DatabaseReference compRef = database.getReference("companies/"+ username+"/job/"+jobTitle.getText().toString());
                    compRef.child("jobTitle").setValue(jobTitle.getText().toString());
                    compRef.child("jobDescription").setValue(jobDescription.getText().toString());
                    compRef.child("jobRequirement").setValue(jobRequirement.getText().toString());
                    compRef.child("keyResponsibilities").setValue(keyResponsibilities.getText().toString());
                    compRef.child("criteria").setValue(criteria.getText().toString());
                    compRef.child("salaryRange").setValue(salaryRange.getText().toString());
                    Toast.makeText(CompanyAddNewJob.this,"New Job Posted",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(CompanyAddNewJob.this,"Correct the details",Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });











    }

    public boolean validate(String jobTitle, String jobDescription, String jobRequirement, String keyResponsibilities, String criteria, String salaryRange) {
        if (jobTitle.length() == 0) {
            this.jobTitle.requestFocus();
            this.jobTitle.setError("Field cannot be empty");
            return false;
        }
        else if(jobDescription.length()==0){
            this.jobDescription.requestFocus();
            this.jobDescription.setError("Field cannot be empty");
            return false;
        }
        else if(jobRequirement.length()==0){
            this.jobRequirement.requestFocus();
            this.jobRequirement.setError("Field cannot be empty");
            return false;
        }
        else if(keyResponsibilities.length()==0){
            this.keyResponsibilities.requestFocus();
            this.keyResponsibilities.setError("Field cannot be empty");
            return false;
        }
        else if(criteria.length()==0){
            this.criteria.requestFocus();
            this.criteria.setError("Field cannot be empty");
            return false;
        }
        else if(salaryRange.length()==0){
            this.salaryRange.requestFocus();
            this.salaryRange.setError("Field cannot be empty");
            return false;
        }
        else{
            return true;
        }
    }

}