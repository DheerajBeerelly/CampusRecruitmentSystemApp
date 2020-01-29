package com.example.campusrecruitmentsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;

public class CompanyNavigationFragment extends AppCompatActivity {
    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    String username;
    Bundle b1 = new Bundle();

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedPreferences;

    public NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CompanyNavigationFragment.this.setTheme(R.style.AppTheme1);
        setContentView(R.layout.company_activity_navigation_fragment);


        loadFragment(new CompanyApplications());


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        b1.putString("username",username);




        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout =findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.applications:
                        loadFragment(new CompanyApplications());
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.profile:
                        loadFragment(new CompanyProfile());
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.job:
                        loadFragment(new CompanyJobs());
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.viewStudents:
                        loadFragment(new CompanyViewStudents());
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.feedback:
                        loadFragment(new CompanyFeedback());
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.logout:
                        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("logged",false);
                        editor.clear();
                        editor.commit();
                        openLogin();
                        drawerLayout.closeDrawers();
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        drawerLayout.openDrawer(GravityCompat.START);


        return super.onOptionsItemSelected(item);
    }

    public void loadFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragment.setArguments(b1);
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    public void openLogin(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}

