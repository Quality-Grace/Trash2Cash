package com.example.trash2cash.RewardScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.net.URL;

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
        if(available) view = inflater.inflate(R.layout.available_rewards_recycler_view, parent, false);
        else view = inflater.inflate(R.layout.other_rewards_recycler_view, parent, false);
        return new RewardsRecyclerAdapter.MyViewHolder(view, rewardsRecyclerAdapter, available);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardsRecyclerAdapter.MyViewHolder holder, int position) {
        holder.titleText.setText(String.valueOf(rewardList.get(position).getTitle()));
        holder.titleText.setMovementMethod(new ScrollingMovementMethod());

        holder.costText.setText(String.valueOf(rewardList.get(position).getCost()));

        try {
            URL url = new URL(rewardList.get(position).getIcon());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.rewardImage.setImageBitmap(bmp);
            holder.rewardImage.setTag(rewardList.get(position).getIcon());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!available) {
            String requirement = rewardList.get(position).getCost() - currentUser.getRewardPoints() + " points left";
            holder.requiredLevelText.setText(requirement);
            holder.requiredLevelText.setMovementMethod(new ScrollingMovementMethod());

            requirement = rewardList.get(position).getLevel() - currentUser.getLevel()+ " levels left";
            holder.requiredCostText.setText(requirement);
            holder.requiredCostText.setMovementMethod(new ScrollingMovementMethod());
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
