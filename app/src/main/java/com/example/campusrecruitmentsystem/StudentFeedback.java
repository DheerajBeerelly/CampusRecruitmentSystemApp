package com.example.campusrecruitmentsystem;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StudentFeedback extends Fragment {

    Bundle b;
    EditText feedback;
    Button submit;
    View view;
    String username ;
    FirebaseDatabase database;
    DatabaseReference myRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.student_activity_feedback, container, false);

        b = getArguments();
        username = b.getString("username");

        feedback = view.findViewById(R.id.feedback);
        submit = view.findViewById(R.id.feedbackButton);

        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("studentsfeedback/"+ username);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    myRef.setValue(feedback.getText().toString());
                    Toast.makeText(getActivity(),"Feedback posted \nSucessfully",Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(),"error updating",Toast.LENGTH_LONG );
                }
            }
        });



        return view;
    }


}