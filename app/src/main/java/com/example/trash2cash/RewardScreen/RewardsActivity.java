package com.example.trash2cash.RewardScreen;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


import com.example.trash2cash.R;
import com.example.trash2cash.RewardList;


public class RewardsActivity extends AppCompatActivity {
    private final RewardList rewardList = new RewardList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        setAmountOfRewards(findViewById (R.id.availableRewardsText), rewardList.size());
        setAmountOfRewards(findViewById (R.id.otherRewardsText), rewardList.size());

        RecyclerView availableForYouRecycler = findViewById(R.id.availableForYouRecycler);
        initializeRecycler(availableForYouRecycler, rewardList, true);

        RecyclerView otherRewardsRecycler = findViewById(R.id.otherRewardsRecycler);
        initializeRecycler(otherRewardsRecycler, rewardList, false);
    }

    public void setAmountOfRewards(TextView textView, int amount) {
        String text = textView.getText() + " (" + amount + ")";
        textView.setText(text);
    }

    public void initializeRecycler(RecyclerView recycler, RewardList rewardList, Boolean available){
        // Adds CardView components to the Recycler
        RewardsRecyclerAdapter adapter = new RewardsRecyclerAdapter(this, rewardList, available);
        recycler.setAdapter(adapter);

        // Sets a vertical layout for the Recycler
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recycler);
    }
}