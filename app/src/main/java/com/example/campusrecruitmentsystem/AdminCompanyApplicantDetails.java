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

public class AdminCompanyApplicantDetails extends AppCompatActivity {

    TextView companyName, city, address ,contact;
    String userName,password;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference loginref,personalref;
    DataSnapshot value;
    Button confirm,reject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_company_applicant_details);

        companyName =findViewById(R.id.companyName);
        city =findViewById(R.id.city);
        address =findViewById(R.id.address);
        contact =findViewById(R.id.contact);
        confirm = findViewById(R.id.confirm);
        reject = findViewById(R.id.reject);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        userName = i.getStringExtra("username");
         password = i.getStringExtra("password");


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCompanyApplicantDetails.this);
                builder.setMessage("Are you sure to Confirm")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loginref= database.getReference("companyLogin");
                                loginref.child(userName).setValue(password);
                                loginref = database.getReference("companiesToBeRegistered/"+userName);
                                loginref.removeValue();
                                Toast.makeText(AdminCompanyApplicantDetails.this,"CONFIRMED",Toast.LENGTH_LONG ).show();
                            }
                        }).setNegativeButton("cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminCompanyApplicantDetails.this);
                builder.setMessage("Are you sure to Reject")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loginref = database.getReference("companiesToBeRegistered/"+userName);
                                loginref.removeValue();
                                loginref = database.getReference("companies/"+userName);
                                loginref.removeValue();
                                Toast.makeText(AdminCompanyApplicantDetails.this, "REJECTED", Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        personalref= database.getReference("companies/" + userName+"/profile");
        personalref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value= dataSnapshot;
                try {
                    companyName.setText(value.child("companyName").getValue().toString());
                    address.setText(value.child("address").getValue().toString());
                    contact.setText(value.child("contact").getValue().toString());
                    city.setText(value.child("city").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(AdminCompanyApplicantDetails.this,e.toString(),Toast.LENGTH_LONG ).show();
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
