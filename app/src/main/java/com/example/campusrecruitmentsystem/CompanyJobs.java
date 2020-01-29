package com.example.campusrecruitmentsystem;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CompanyJobs extends Fragment {

    ListView listView;
    View view;
    FloatingActionButton addJob;
    Bundle b3;
    String username;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> jobs = new ArrayList<>();

    public CompanyJobs(){
        //empty
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.company_activity_jobs, container, false);

        b3 = getArguments();
        username = b3.getString("username");
       database = FirebaseDatabase.getInstance();
        myRef =  database.getReference("companies/"+ username+"/job");


        listView = (ListView) view.findViewById(R.id.listview);


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,jobs);
        listView.setAdapter(arrayAdapter);




            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(view.getContext(),CompanySelectedJobDetails.class);
                    intent.putExtra("jobTitle",arrayAdapter.getItem(i).toString());
                    intent.putExtra("username",username);
                    startActivity(intent);
                }
            });


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    jobs.clear();
                    for(DataSnapshot child: dataSnapshot.getChildren()) {

                        String key = child.getKey().toString();
                        jobs.add(key);
                        arrayAdapter.notifyDataSetChanged();
                    }

                }
                catch(Exception e){
                    Toast.makeText(getActivity(),"error loading",Toast.LENGTH_LONG );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        addJob = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),CompanyAddNewJob.class);
                i.putExtra("username",username);
            startActivity(i);
            }
        });

        return view;
    }


}