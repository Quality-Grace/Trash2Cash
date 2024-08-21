package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.trash2cash.AdminRequestsLogger.AdminRequestsLoggerFragment;
import com.example.trash2cash.RecyclableMaterialSettings.RecyclableMaterialSettingsFragment;
import com.example.trash2cash.RewardSettings.RewardSettingsFragment;
import com.example.trash2cash.ViewStats.AdminStatisticsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminNavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ((BottomNavigationView)findViewById(R.id.bottomNavigationView)).setSelectedItemId(R.id.RecycleItem);

        ((BottomNavigationView)findViewById(R.id.bottomNavigationView)).setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RewardsItem) {
                switchFragment(new RewardSettingsFragment());
            } else if(item.getItemId() == R.id.StatsItem) {
                switchFragment(new AdminStatisticsFragment());
            } else if(item.getItemId() == R.id.LogoutItem) {
                startActivity(new Intent(this, LoginRegisterActivity.class));
            } else if(item.getItemId() == R.id.MaterialsItem) {
                switchFragment(new RecyclableMaterialSettingsFragment());
            } else if(item.getItemId() == R.id.RecycleItem) {
                switchFragment(new AdminRequestsLoggerFragment());
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