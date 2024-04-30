package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trash2cash.RecyclabeMaterialSettings.RecyclableMaterialSettingsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Temp button to navigate to the RewardSettingsActivity
        findViewById(R.id.rewardSettingsTempButton).setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RecyclableMaterialSettingsActivity.class)));
    }
}