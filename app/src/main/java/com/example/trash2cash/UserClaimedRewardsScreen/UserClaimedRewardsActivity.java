package com.example.trash2cash.UserClaimedRewardsScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;

import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.RewardList;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.R;

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
    }
}