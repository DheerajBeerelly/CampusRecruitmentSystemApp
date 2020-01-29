package com.example.campusrecruitmentsystem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class StudentProfile extends Fragment {

    TextView studentName, idNumber, email,dob ;
    TextInputEditText contact;
    Button update , academicDetails;
    DataSnapshot value = null;
    Bundle b3;
    String username , name;
    View view;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.student_activity_profile, container, false);

        studentName = view.findViewById(R.id.studentName);
        dob = view.findViewById(R.id.dob);
        idNumber = view.findViewById(R.id.idNumber);
        email = view.findViewById(R.id.email);
        contact = view.findViewById(R.id.contact);
        update = view.findViewById(R.id.update);
        academicDetails = view.findViewById(R.id.academicDetails);



        b3 = getArguments();
        username = b3.getString("username");

        myRef = database.getReference("students/"+username+"/profile");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure to update details")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                boolean valid = validate(
                         contact.getText().toString());
                if (valid) {

                    try {
                        myRef.child("contact").setValue(contact.getText().toString());
                        Toast.makeText(getActivity(), "Details Updated \n Sucessfully", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "error updating", Toast.LENGTH_LONG);
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Correct the details",Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("cancel",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        academicDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),StudentAcademicDetails.class);
                i.putExtra("username",username);
                startActivity(i);
            }
        });




        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot;
                try {
                    studentName.setText(value.child("studentName").getValue().toString());
                    idNumber.setText(value.child("idNumber").getValue().toString());
                   dob.setText(value.child("dob").getValue().toString());
                    email.setText(value.child("email").getValue().toString());
                    contact.setText(value.child("contact").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(getActivity(),"error loading",Toast.LENGTH_LONG );
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return view;
    }
    public boolean validate(String contact){

        if(!(contact.length()==10)){
            this.contact.requestFocus();
            this.contact.setError("Enter correct Number");
            return false;
        }
        else{
            return true;
        }
    }
}