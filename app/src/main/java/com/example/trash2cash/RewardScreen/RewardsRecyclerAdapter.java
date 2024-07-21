package com.example.trash2cash.RewardScreen;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash2cash.Entities.RecyclableManager;
import com.example.trash2cash.Entities.User;
import com.example.trash2cash.R;
import com.example.trash2cash.Entities.RewardList;
import com.squareup.picasso.Picasso;


public class RewardsRecyclerAdapter extends RecyclerView.Adapter<RewardsRecyclerAdapter.MyViewHolder>{
    private final Context context;
    private final RewardList rewardList;
    private final Boolean available;
    private final RewardRecyclerInterface rewardsRecyclerAdapter;
    private final User currentUser;

    public RewardsRecyclerAdapter(Context context, RewardList rewardList, boolean available, RewardRecyclerInterface rewardsRecyclerAdapter){
        this.context = context;
        this.rewardList = rewardList;
        this.available = available;
        this.rewardsRecyclerAdapter = rewardsRecyclerAdapter;
        this.currentUser = RecyclableManager.getRecyclableManager().getUser();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        // The same adapter is used for the available rewards and the other ones
        if(available) view = inflater.inflate(R.layout.available_rewards_recycler_view, parent, false);
        else view = inflater.inflate(R.layout.other_rewards_recycler_view, parent, false);
        return new RewardsRecyclerAdapter.MyViewHolder(view, rewardsRecyclerAdapter, available);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsRecyclerAdapter.MyViewHolder holder, int position) {
        // Sets the reward card title text
        holder.titleText.setText(String.valueOf(rewardList.get(position).getTitle()));
        holder.titleText.setMovementMethod(new ScrollingMovementMethod());

        // Sets the reward card cost text
        holder.costText.setText(String.valueOf(rewardList.get(position).getCost()));

        // Loads the reward image
        Picasso.with(context).load(rewardList.get(position).getIcon()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(holder.rewardImage);
        holder.rewardImage.setTag(rewardList.get(position).getIcon());

        if(!available) {
            // This is used for the other rewards.
            // It sets the text in the reward cards informing the user of what he is lacking in order to claim the reward.
            float levelsLeft = rewardList.get(position).getLevel() - currentUser.getLevel();
            if(levelsLeft>0) {
                String requirement = levelsLeft + " levels left to unlock";
                holder.requiredLevelText.setText(requirement);
                holder.requiredLevelText.setMovementMethod(new ScrollingMovementMethod());
            } else {
                holder.requiredLevelText.setVisibility(View.GONE);
            }

            float pointsLeft = rewardList.get(position).getCost() - currentUser.getRewardPoints();
            if(pointsLeft>0){
                String requirement = pointsLeft + " points left to unlock";
                holder.requiredCostText.setText(requirement);
                holder.requiredCostText.setMovementMethod(new ScrollingMovementMethod());
            } else {
                holder.requiredCostText.setVisibility(View.GONE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView costText, requiredLevelText, requiredCostText, titleText;
        private final ImageView rewardImage;

        public MyViewHolder(@NonNull View itemView, RewardRecyclerInterface rewardRecyclerInterface, boolean available) {
            super(itemView);

            titleText = itemView.findViewById(R.id.rewardTitleText);
            costText = itemView.findViewById(R.id.costText);
            requiredLevelText = itemView.findViewById(R.id.requiredLevelText);
            requiredCostText = itemView.findViewById(R.id.requiredCostText);
            rewardImage = itemView.findViewById(R.id.rewardImage);

            if(available){
                Button claimRewardButton = itemView.findViewById(R.id.claimRewardButton);
                // Adds the ability for the user to claim a reward
                claimRewardButton.setOnClickListener(view -> {
                    if(rewardRecyclerInterface != null){
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            rewardRecyclerInterface.claimReward(pos);
                        }
                    }
                });
            }
        }
    }
}
