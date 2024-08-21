package com.example.trash2cash.RewardScreen;

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

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.Reward;
import com.example.trash2cash.Entities.RewardList;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.R;

import java.util.Comparator;

public class RewardsFragment extends Fragment implements RewardRecyclerInterface {
    private final RewardList rewardList = new RewardList();
    private final  RewardList availableRewards = new RewardList();
    private final RewardList otherRewards = new RewardList();
    private RewardsRecyclerAdapter availableRewardsAdapter, otherRewardsAdapter;
    private View rootView;

    public RewardsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_rewards, container, false);
        setAmountOfRewards(rootView.findViewById (R.id.availableRewardsText), 0);
        setAmountOfRewards(rootView.findViewById (R.id.otherRewardsText),0);

        RecyclerView availableForYouRecycler = rootView.findViewById(R.id.availableForYouRecycler);
        initializeRecycler(availableForYouRecycler, availableRewards, true);

        RecyclerView otherRewardsRecycler = rootView.findViewById(R.id.otherRewardsRecycler);
        initializeRecycler(otherRewardsRecycler, otherRewards, false);

        new Thread(()->{
            rewardList.clear();
            rewardList.addAll(new RewardList(OkHttpHandler.getPATH()+"populateRewards.php"));

            // Fills the availableRewards and otherRewards lists
            initializeLists();
            updateAmountOfRewards(rootView.findViewById (R.id.availableRewardsText), availableRewards.size());
            updateAmountOfRewards(rootView.findViewById (R.id.otherRewardsText), otherRewards.size());

            try{
                requireActivity().runOnUiThread(()->{
                    availableRewardsAdapter.notifyDataSetChanged();
                    otherRewardsAdapter.notifyDataSetChanged();
                });
            } catch (Exception ignored) {}
        }).start();

        return rootView;
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
            String finalText = text;
            try{
                requireActivity().runOnUiThread(()->textView.setText(finalText));
            } catch (Exception ignored){}
        }
    }

    public void initializeRecycler(RecyclerView recycler, RewardList rewardList, Boolean available){
        // Adds CardView components to the Recycler
        if(available) {
            availableRewardsAdapter = new RewardsRecyclerAdapter(getContext(), rewardList, available, this);
            recycler.setAdapter(availableRewardsAdapter);
        } else {
            otherRewardsAdapter = new RewardsRecyclerAdapter(getContext(), rewardList, available, this);
            recycler.setAdapter(otherRewardsAdapter);
        }

        // Sets a vertical layout for the Recycler
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Adds snappy scrolling
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recycler);
    }

    @Override
    public void claimReward(int position) {
        User currentUser =  RecyclableManager.getRecyclableManager().getUser();

        // Adds the reward to the user list and then updates the db
        currentUser.addReward(availableRewards.get(position));
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.updateUser(currentUser);
        okHttpHandler.updateUserRewardList(currentUser);

        // Removes the reward from the available reward list and informs the user
        availableRewards.remove(position);
        availableRewardsAdapter.notifyItemRemoved(position);
        for(int i=availableRewards.size()-1; i>=0; i--){
            // After the user points get altered it removes from the available rewards list all the rewards that are no longer claimable with his current points
            if(availableRewards.get(i).getCost()>currentUser.getRewardPoints()){
                otherRewards.add(0, availableRewards.get(i));
                otherRewardsAdapter.notifyItemInserted(0);
                availableRewards.remove(i);
                availableRewardsAdapter.notifyItemRemoved(i);
            }
        }

        // Updates the amount of rewards texts
        updateAmountOfRewards(rootView.findViewById (R.id.availableRewardsText), availableRewards.size());
        updateAmountOfRewards(rootView.findViewById (R.id.otherRewardsText), otherRewards.size());
    }
}