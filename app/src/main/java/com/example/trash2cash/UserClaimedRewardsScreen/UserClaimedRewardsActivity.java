package com.example.trash2cash.UserClaimedRewardsScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.RewardList;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.LoginRegisterActivity;
import com.example.trash2cash.MaterialLogger.MaterialLoggerActivity;
import com.example.trash2cash.R;
import com.example.trash2cash.RecyclableMaterialSettings.RecyclableMaterialSettingsActivity;
import com.example.trash2cash.RewardScreen.RewardsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserClaimedRewardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_claimed_rewards);

        User currentUser =  RecyclableManager.getRecyclableManager().getUser();
        RewardList claimedRewardsList = currentUser.getRewardList();

        RecyclerView claimedRewardsRecycler = findViewById(R.id.claimedRewardRecycler);

        // Adds CardView components to the Recycler
        ClaimedRewardsRecyclerAdapter claimedRewardsRecyclerAdapter = new ClaimedRewardsRecyclerAdapter(this, claimedRewardsList);
        claimedRewardsRecycler.setAdapter(claimedRewardsRecyclerAdapter);

        // Sets a vertical layout for the Recycler
        claimedRewardsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(claimedRewardsRecycler);

        setAmountOfRewards(findViewById(R.id.claimedRewardsAmount), claimedRewardsList.size());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.ProfileItem);
        setupNavigationListener(bottomNavigationView);
    }

    public void setAmountOfRewards(TextView textView, int amount) {
        String text = textView.getText().toString() + " (" + amount + ")";
        textView.setText(text);
    }

    public void setupNavigationListener(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RecycleItem){
                startActivity(new Intent(UserClaimedRewardsActivity.this, MaterialLoggerActivity.class));
            } else if(item.getItemId() == R.id.RewardsItem) {
                startActivity(new Intent(UserClaimedRewardsActivity.this, RewardsActivity.class));
            } else if(item.getItemId() == R.id.MaterialsItem){
                startActivity(new Intent(UserClaimedRewardsActivity.this, RecyclableMaterialSettingsActivity.class));
            } else if(item.getItemId() == R.id.LogoutItem){
                startActivity(new Intent(UserClaimedRewardsActivity.this, LoginRegisterActivity.class));
            }

            return true;
        });
    }
}