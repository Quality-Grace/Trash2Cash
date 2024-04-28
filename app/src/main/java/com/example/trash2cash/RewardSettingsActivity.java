package com.example.trash2cash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;

public class RewardSettingsActivity extends AppCompatActivity {
    // This variable will probably move
    static RecyclableMaterialTypes recyclableMaterialTypes = new RecyclableMaterialTypes();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_settings);

        RecyclerView recyclerView = findViewById(R.id.rewardSettingsRecyclerView);

        // Adds CardView components to the Recycler
        RewardSettingsRecyclerAdapter adapter = new RewardSettingsRecyclerAdapter(this, recyclableMaterialTypes);
        recyclerView.setAdapter(adapter);

        // Sets a vertical layout for the Recycler
         recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);
    }
}