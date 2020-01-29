package com.example.campusrecruitmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdminViewCompanyJobs extends AppCompatActivity {

    ListView listView;
    String username,companyName1;
    DataSnapshot value = null;
    DatabaseReference myRef , compRef;
    ArrayList<String> job = new ArrayList<>();
    TextView companyName, address, city , contact;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_view_company_jobs);

        companyName = findViewById(R.id.companyname);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        contact = findViewById(R.id.contact);

        Intent i = getIntent();
        username = i.getStringExtra("username");
        companyName1 = i.getStringExtra("companyName");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myRef = database.getReference("companies/"+companyName1+"/profile");

        compRef =  database.getReference("companies/"+companyName1+"/job");

        //Toast.makeText(StudentViewCompanyJobs.this,companyName1+""+username,Toast.LENGTH_LONG ).show();
        listView = (ListView) findViewById(R.id.listview);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AdminViewCompanyJobs.this, android.R.layout.simple_list_item_1,job);
        listView.setAdapter(arrayAdapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(),AdminViewCompanyJobDetails.class);
                intent.putExtra("selectedJob",arrayAdapter.getItem(i).toString());
                intent.putExtra("companyName",companyName1);
                intent.putExtra("username",username);
                startActivity(intent);
                //Toast.makeText(StudentViewCompanyJobs.this,arrayAdapter.getItem(i).toString(),Toast.LENGTH_LONG ).show();
            }
        });


        compRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    job.clear();
                    for(DataSnapshot child: dataSnapshot.getChildren()) {

                        String key = child.getKey().toString();
                        job.add(key);
                        arrayAdapter.notifyDataSetChanged();
                    }

                }
                catch(Exception e){
                    Toast.makeText(AdminViewCompanyJobs.this,"error loading",Toast.LENGTH_LONG );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot;
                try {
                    companyName.setText(value.child("companyName").getValue().toString());
                    city.setText(value.child("city").getValue().toString());
                    address.setText(value.child("address").getValue().toString());
                    contact.setText(value.child("contact").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(AdminViewCompanyJobs.this,"error loading",Toast.LENGTH_LONG ).show();
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

