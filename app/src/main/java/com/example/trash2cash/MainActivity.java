package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trash2cash.AdminRequestsLogger.AdminRequestsLoggerActivity;
import com.example.trash2cash.MaterialLogger.MaterialLoggerActivity;
import com.example.trash2cash.RecyclableMaterialSettings.RecyclableMaterialSettingsActivity;
import com.example.trash2cash.RewardScreen.RewardsActivity;
import com.example.trash2cash.RewardSettings.RewardSettingsActivity;

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
    }
}