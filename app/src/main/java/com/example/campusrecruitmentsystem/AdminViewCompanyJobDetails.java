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

public class AdminViewCompanyJobDetails extends AppCompatActivity {

    TextView jobDescription, jobRequirement, keyResponsibilities, criteria, salaryRange ,jobTitle;
    String username,selectedJob,companyName;
    DataSnapshot value = null;
    DatabaseReference myRef,compRef,root ;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_view_company_job_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jobTitle = findViewById(R.id.jobTitle);
        jobDescription = findViewById(R.id.jobDescription);
        jobRequirement = findViewById(R.id.jobRequirements);
        keyResponsibilities = findViewById(R.id.keyResponsibilities);
        criteria = findViewById(R.id.criteria);
        salaryRange = findViewById(R.id.salaryRange);



        Intent i = getIntent();
        username = i.getStringExtra("username");
        selectedJob = i.getStringExtra("selectedJob");
        companyName = i.getStringExtra("companyName");


        root = database.getReference("companies/"+ companyName+"/jobApplications");
        myRef = database.getReference("companies/"+ companyName+"/job/"+selectedJob);
        compRef = database.getReference("students/"+ username+"/jobsApplied");







        myRef.addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(AdminViewCompanyJobDetails.this,"error loading",Toast.LENGTH_LONG );
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
