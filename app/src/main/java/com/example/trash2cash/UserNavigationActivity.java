package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.trash2cash.MaterialLogger.MaterialLoggerFragment;
import com.example.trash2cash.RewardScreen.RewardsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ((BottomNavigationView)findViewById(R.id.bottomNavigationView)).setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RewardsItem) {
                switchFragment(new RewardsFragment());
            } else if(item.getItemId() == R.id.ProfileItem) {
                switchFragment(new UserProfileFragment());
            } else if(item.getItemId() == R.id.LogoutItem) {
                startActivity(new Intent(this, LoginRegisterActivity.class));
            } else if(item.getItemId() == R.id.RecycleItem) {
                switchFragment(new MaterialLoggerFragment());
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
