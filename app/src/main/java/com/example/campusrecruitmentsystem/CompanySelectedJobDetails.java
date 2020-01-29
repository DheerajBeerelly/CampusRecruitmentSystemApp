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

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CompanySelectedJobDetails extends AppCompatActivity {

    TextInputEditText  jobDescription, jobRequirement, keyResponsibilities, criteria, salaryRange;
    TextView jobTitle;
    Button update , delete;
    String username,selectedJob;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference compRef;
    DataSnapshot value = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.company_activity_selected_job_details);


        jobTitle = findViewById(R.id.jobTitle);
        jobDescription = findViewById(R.id.jobDescription);
        jobRequirement = findViewById(R.id.jobRequirements);
        keyResponsibilities = findViewById(R.id.keyResponsibilities);
        criteria = findViewById(R.id.criteria);
        salaryRange = findViewById(R.id.salaryRange);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.jobDelete);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent intent = getIntent();
        username = intent.getStringExtra("username");
        selectedJob = intent.getStringExtra("jobTitle");

       // DatabaseReference compRef = database.getReference("companies/"+ username+"/job/"+selectedJob);
        //Toast.makeText(SelectedJobDetails.this,username,Toast.LENGTH_LONG).show();
       // Toast.makeText(SelectedJobDetails.this,selectedJob,Toast.LENGTH_LONG).show();
        final DatabaseReference compRef = database.getReference("companies/"+ username+"/job/"+selectedJob);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CompanySelectedJobDetails.this);
                builder.setMessage("Are you sure to update details")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                boolean valid = validate(jobTitle.getText().toString(),
                        jobDescription.getText().toString(),jobRequirement.getText().toString(),keyResponsibilities.getText().toString(),
                        criteria.getText().toString(),salaryRange.getText().toString());
                if(valid) {
                    DatabaseReference compRef = database.getReference("companies/"+ username+"/job/"+selectedJob);
                    compRef.child("jobDescription").setValue(jobDescription.getText().toString());
                    compRef.child("jobRequirement").setValue(jobRequirement.getText().toString());
                    compRef.child("keyResponsibilities").setValue(keyResponsibilities.getText().toString());
                    compRef.child("criteria").setValue(criteria.getText().toString());
                    compRef.child("salaryRange").setValue(salaryRange.getText().toString());
                    Toast.makeText(CompanySelectedJobDetails.this,"Job Updated",Toast.LENGTH_LONG).show();


                }
                else{
                    Toast.makeText(CompanySelectedJobDetails.this,"Correct the details",Toast.LENGTH_LONG).show();
                }
                            }
                        }).setNegativeButton("cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CompanySelectedJobDetails.this);
                builder.setMessage("Are you sure to delete job")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                compRef.removeValue();
                Toast.makeText(CompanySelectedJobDetails.this,"Sucessfully Deleted:\n"+selectedJob,Toast.LENGTH_LONG).show();
               // Intent i = new Intent(SelectedJobDetails.this, Jobs.class);
               // startActivity(i);
            }
        }).setNegativeButton("cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        compRef.addValueEventListener(new ValueEventListener() {
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
                    Toast.makeText(CompanySelectedJobDetails.this,"error loading",Toast.LENGTH_LONG );
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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


