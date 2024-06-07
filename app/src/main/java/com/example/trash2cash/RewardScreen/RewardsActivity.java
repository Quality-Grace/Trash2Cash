package com.example.trash2cash.RewardScreen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.Reward;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.LoginRegisterActivity;
import com.example.trash2cash.MainActivity;
import com.example.trash2cash.MaterialLogger.MaterialLoggerActivity;
import com.example.trash2cash.R;
import com.example.trash2cash.Entities.RewardList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Comparator;


public class RewardsActivity extends AppCompatActivity implements RewardRecyclerInterface {
    private final RewardList rewardList = new RewardList(OkHttpHandler.getPATH()+"populateRewards.php");
    private final  RewardList availableRewards = new RewardList();
    private final RewardList otherRewards = new RewardList();
    private RewardsRecyclerAdapter availableRewardsAdapter, otherRewardsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        // Fills the availableRewards and otherRewards lists
        initializeLists();

        setAmountOfRewards(findViewById (R.id.availableRewardsText), availableRewards.size());
        setAmountOfRewards(findViewById (R.id.otherRewardsText), otherRewards.size());

        RecyclerView availableForYouRecycler = findViewById(R.id.availableForYouRecycler);
        initializeRecycler(availableForYouRecycler, availableRewards, true);

        RecyclerView otherRewardsRecycler = findViewById(R.id.otherRewardsRecycler);
        initializeRecycler(otherRewardsRecycler, otherRewards, false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.RewardsItem);
        setupNavigationListener(bottomNavigationView);
    }

    public void setupNavigationListener(BottomNavigationView bottomNavigationView){
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.RecycleItem){
                startActivity(new Intent(RewardsActivity.this, MaterialLoggerActivity.class));
            } else if(item.getItemId() == R.id.ProfileItem) {
                startActivity(new Intent(RewardsActivity.this, MainActivity.class));
            } else if(item.getItemId() == R.id.LogoutItem){
                startActivity(new Intent(RewardsActivity.this, LoginRegisterActivity.class));
            }

            return true;
        });
    }

    public void initializeLists(){
        User currentUser =  RecyclableManager.getRecyclableManager().getUser();
        for(Reward reward : rewardList){
            if(!currentUser.hasReward(reward)){
                if(reward.getLevel() <= currentUser.getLevel() && reward.getCost() <= currentUser.getRewardPoints()){
                    availableRewards.add(reward);
                } else {
                    otherRewards.add(reward);
                }
            }
        }

        availableRewards.sort(Comparator.comparingInt(Reward::getCost));
        otherRewards.sort(Comparator.comparingInt(Reward::getLevel));
    }

    // Adds the amount of rewards to the given textview
    public void setAmountOfRewards(TextView textView, int amount) {
        String text = textView.getText().toString() + " (" + amount + ")";
        textView.setText(text);
    }

    // Updates the amount of rewards of the given textview
    private void updateAmountOfRewards(TextView textView, int amount){
        String text = textView.getText().toString();
        int indexOfPar = text.indexOf("(");

        if(indexOfPar != -1) {
            text = text.substring(0, indexOfPar-1) + " (" + amount + ")";
            textView.setText(text);
        }
    }

    public void initializeRecycler(RecyclerView recycler, RewardList rewardList, Boolean available){
        // Adds CardView components to the Recycler
        if(available) {
            availableRewardsAdapter = new RewardsRecyclerAdapter(this, rewardList, available, this);
            recycler.setAdapter(availableRewardsAdapter);
        } else {
            otherRewardsAdapter = new RewardsRecyclerAdapter(this, rewardList, available, this);
            recycler.setAdapter(otherRewardsAdapter);
        }

        // Sets a vertical layout for the Recycler
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recycler);
    }

    @Override
    public void claimReward(int position) {
        User currentUser =  RecyclableManager.getRecyclableManager().getUser();

        currentUser.addReward(availableRewards.get(position));
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.updateUserRewardList(currentUser);

        availableRewards.remove(position);
        availableRewardsAdapter.notifyItemRemoved(position);
        updateAmountOfRewards(findViewById (R.id.availableRewardsText), availableRewards.size());
    }
}