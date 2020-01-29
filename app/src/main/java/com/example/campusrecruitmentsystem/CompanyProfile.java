package com.example.campusrecruitmentsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CompanyProfile extends Fragment {

    View view;
    TextInputEditText companyName, address, city , contact;
    Button update;
    DataSnapshot value = null;
    Bundle b3;
    String username , name;


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.company_activity_profile, container, false);

        b3 = getArguments();
        username = b3.getString("username");
        myRef = database.getReference("companies/"+username+"/profile");






        companyName = view.findViewById(R.id.companyName1);
        address = view.findViewById(R.id.address1);
        city = view.findViewById(R.id.city1);
        contact = view.findViewById(R.id.contact1);
        update = view.findViewById(R.id.update);






        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you sure to update details")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                boolean valid = validate(companyName.getText().toString(),
                                        address.getText().toString(), city.getText().toString(), contact.getText().toString());
                                if (valid) {

                                    try {
                                        myRef.child("companyName").setValue(companyName.getText().toString());
                                        myRef.child("address").setValue(address.getText().toString());
                                        myRef.child("city").setValue(city.getText().toString());
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









        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                value = dataSnapshot;
               // Toast.makeText(getActivity()," loading",Toast.LENGTH_LONG ).show();
                try {
                    companyName.setText(value.child("companyName").getValue().toString());
                    city.setText(value.child("city").getValue().toString());
                    address.setText(value.child("address").getValue().toString());
                    contact.setText(value.child("contact").getValue().toString());
                }
                catch(Exception e){
                    Toast.makeText(getActivity(),"error loading",Toast.LENGTH_LONG ).show();
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
    public boolean validate(String name, String address, String city, String contact){
        if(name.length()==0 ){
            companyName.requestFocus();
            companyName.setError("Field cannot be empty");
            return false;
        }
        else if(!name.matches("[a-zA-Z]+")){
            companyName.requestFocus();
            companyName.setError("Enter Alphabets only");
            return false;
        }
        else if(address.length()==0){
            this.address.requestFocus();
            this.address.setError("Field cannot be empty");
            return false;
        }
        else if(city.length()==0){
            this.city.requestFocus();
            this.city.setError("Field cannot be empty");
            return false;
        }
        else if(!(contact.length()==10)){
            this.contact.requestFocus();
            this.contact.setError("Enter correct Number");
            return false;
        }
        else{
            return true;
        }
    }

}