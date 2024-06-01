package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.trash2cash.AdminRequestsLogger.AdminRequestsLoggerActivity;
import com.example.trash2cash.MaterialLogger.MaterialLoggerActivity;
import com.example.trash2cash.RecyclableMaterialSettings.RecyclableMaterialSettingsActivity;
import com.example.trash2cash.RewardScreen.RewardsActivity;
import com.example.trash2cash.RewardSettings.RewardSettingsActivity;

public class MainActivity extends AppCompatActivity {

    private Button btn;
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
        btn = findViewById(R.id.user_profile_btn);

        // Set click listener for the button
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);

                // Optionally, send data to the new activity
                // String message = "Hello from MainActivity!";
                // intent.putExtra("message_key", message);

                // Start the new activity
                startActivity(intent);
            }
        });
    }
}