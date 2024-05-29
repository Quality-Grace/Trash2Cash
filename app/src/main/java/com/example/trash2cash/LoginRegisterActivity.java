package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trash2cash.R1.LoginUser;
import com.example.trash2cash.R1.RegisterUser;

public class LoginRegisterActivity extends AppCompatActivity {
    private static final String IP = "172.18.12.79";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        // Set the initial fragment when the activity is created
        Button regBtnButton = findViewById(R.id.registerButton);
        Button logBtnButton = findViewById(R.id.loginButton);
        regBtnButton.setVisibility(View.VISIBLE);
        logBtnButton.setVisibility(View.VISIBLE);
        regBtnButton.setOnClickListener(v -> {
            regBtnButton.setVisibility(View.INVISIBLE);
            logBtnButton.setVisibility(View.INVISIBLE);
            loadFragment(new RegisterUser());
        });
        logBtnButton.setOnClickListener(v -> {
            regBtnButton.setVisibility(View.INVISIBLE);
            logBtnButton.setVisibility(View.INVISIBLE);
            loadFragment(new LoginUser());
        });
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
