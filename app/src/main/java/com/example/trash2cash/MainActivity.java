package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.trash2cash.R1.LoginUser;

public class MainActivity extends AppCompatActivity {
    private static final String IP = "temp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the initial fragment when the activity is created
        loadFragment(new LoginUser());
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_frameLayout, fragment)
                .commit();
    }

    // getter for the IP address
    public static String getIP() {
        return IP;
    }
}
