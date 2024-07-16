package com.example.trash2cash.UserClaimedRewardsScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.RewardList;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.R;

public class UserClaimedRewardsFragment extends Fragment {

    public UserClaimedRewardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_claimed_rewards, container, false);
        User currentUser =  RecyclableManager.getRecyclableManager().getUser();
        RewardList claimedRewardsList = currentUser.getRewardList();

        RecyclerView claimedRewardsRecycler = rootView.findViewById(R.id.claimedRewardRecycler);

        // Adds CardView components to the Recycler
        ClaimedRewardsRecyclerAdapter claimedRewardsRecyclerAdapter = new ClaimedRewardsRecyclerAdapter(getContext(), claimedRewardsList);
        claimedRewardsRecycler.setAdapter(claimedRewardsRecyclerAdapter);

        // Sets a vertical layout for the Recycler
        claimedRewardsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(claimedRewardsRecycler);

        // Updates the title of the screen to contain the amount of rewards that the user has claimed
        setAmountOfRewards(rootView.findViewById(R.id.claimedRewardsAmount), claimedRewardsList.size());
        return rootView;
    }
    public void setAmountOfRewards(TextView textView, int amount) {
        String text = textView.getText().toString() + " (" + amount + ")";
        textView.setText(text);
    }

}