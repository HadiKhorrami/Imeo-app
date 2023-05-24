package com.example.imeo_app.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.imeo_app.Fragment.Dashboard;
import com.example.imeo_app.Fragment.Profile;
import com.example.imeo_app.Fragment.Reports;
import com.example.imeo_app.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        load_SmoothBottomBar();
        loadDefaultFragment();

    }
    private void load_SmoothBottomBar() {
        bottomNavigationView = findViewById(R.id.bottomnavigation_main);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment selectedFragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.itemdashboard:
                        selectedFragment = Dashboard.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        return true;
                    case R.id.itemreports:
                        selectedFragment = Reports.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        return true;
                    case R.id.itemprofile:
                        selectedFragment = Profile.newInstance();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, selectedFragment).commit();
                        return true;

                    default:
                        return true;
                }
            }
        });
    }
    private void loadDefaultFragment() {
        Fragment fragment = Dashboard.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
    }


}