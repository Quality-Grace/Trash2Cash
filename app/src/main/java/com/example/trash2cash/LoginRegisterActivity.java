package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.trash2cash.R1.LoginUser;
import com.example.trash2cash.R1.RegisterUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        ((BottomNavigationView)findViewById(R.id.bottomNavigationView)).setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.loginItem) {
                switchFragment(new LoginUser());
            } else {
                switchFragment(new RegisterUser());
            }

            return true;
        });
    }

    public void switchFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.commit();
    }
}
