package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trash2cash.AdminRequestsLogger.AdminRequestsLoggerActivity;
import com.example.trash2cash.MaterialLogger.MaterialLoggerActivity;
import com.example.trash2cash.RecyclableMaterialSettings.RecyclableMaterialSettingsActivity;
import com.example.trash2cash.RewardScreen.RewardsActivity;
import com.example.trash2cash.RewardSettings.RewardSettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonMaterialLogger)
                .setOnClickListener(view -> startActivity(new Intent(MainActivity.this, MaterialLoggerActivity.class)));

        findViewById(R.id.adminLoggerBtn)
                .setOnClickListener(view -> startActivity(new Intent(MainActivity.this, AdminRequestsLoggerActivity.class)));

        findViewById(R.id.RecyclableMaterialSettingsTempButton)
                .setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RecyclableMaterialSettingsActivity.class)));

        findViewById(R.id.RewardSettingsTempButton)
                .setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RewardSettingsActivity.class)));

        findViewById(R.id.RewardsTempButton)
                .setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RewardsActivity.class)));

        // Find the button by its ID
        findViewById(R.id.user_profile_btn)
                .setOnClickListener(view -> startActivity(new Intent(MainActivity.this, UserProfileActivity.class)));

        setupNavigationListener(findViewById(R.id.bottomNavigationView));
    }

    public void setupNavigationListener(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RecycleItem){
                startActivity(new Intent(MainActivity.this, MaterialLoggerActivity.class));
            } else if(item.getItemId() == R.id.RewardsItem) {
                startActivity(new Intent(MainActivity.this, RewardsActivity.class));
            } else if(item.getItemId() == R.id.MaterialsItem){
                startActivity(new Intent(MainActivity.this, RecyclableMaterialSettingsActivity.class));
            } else if(item.getItemId() == R.id.LogoutItem){
                startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
            }

//            if(item.getItemId() == R.id.RecycleItem){
//                startActivity(new Intent(MainActivity.this, AdminRequestsLoggerActivity.class));
//            } else if(item.getItemId() == R.id.RewardsItem) {
//                startActivity(new Intent(MainActivity.this, RewardSettingsActivity.class));
//            } else if(item.getItemId() == R.id.MaterialsItem){
//                startActivity(new Intent(MainActivity.this, RecyclableMaterialSettingsActivity.class));
//            } else if(item.getItemId() == R.id.LogoutItem){
//                startActivity(new Intent(MainActivity.this, LoginRegisterActivity.class));
//            }

            return true;
        });
    }
}