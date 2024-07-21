package com.example.trash2cash.RewardSettings;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash2cash.R;
import com.example.trash2cash.Entities.RewardList;
import com.squareup.picasso.Picasso;

public class RewardSettingsRecyclerAdapter extends RecyclerView.Adapter<RewardSettingsRecyclerAdapter.MyViewHolder> {
    private final Context context;
    private final RewardList rewardList;
    private final RewardSettingsRecyclerInterface rewardRecyclerInterface;

    public RewardSettingsRecyclerAdapter(Context context, RewardList rewardList, RewardSettingsRecyclerInterface rewardRecyclerInterface){
        this.context = context;
        this.rewardList = rewardList;
        this.rewardRecyclerInterface = rewardRecyclerInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reward_settings_recycler_view, parent, false);
        return new MyViewHolder(view, rewardRecyclerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Initializes the title of the reward card and a listener to allow the editing of its value
        holder.titleText.setText(String.valueOf(rewardList.get(position).getTitle()));
        setupEditTextListener(holder.titleText, position, "title");

        // Initializes the cost of the reward card and a listener to allow the editing of its value
        holder.costText.setText(String.valueOf(rewardList.get(position).getCost()));
        setupEditTextListener(holder.costText, position, "cost");

        // Initializes the level requirement of the reward card and a listener to allow the editing of its value
        holder.levelRequiredText.setText(String.valueOf(rewardList.get(position).getLevel()));
        setupEditTextListener(holder.levelRequiredText, position, "level");

        // Loads the reward image
        Picasso.with(context).load(rewardList.get(position).getIcon()).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(holder.rewardImage);
        holder.rewardImage.setTag(rewardList.get(position).getIcon());
    }

    private void setupEditTextListener(EditText editText, int position, String type) {
        editText.setOnFocusChangeListener((view, b) -> {
            if(type.equals("cost")){
                rewardList.get(position).setCost(UserInputParser.parseEditableTextToInt(editText.getText()));
            } else if(type.equals("level")) {
                rewardList.get(position).setLevel(UserInputParser.parseEditableTextToInt((editText.getText())));
            } else {
                rewardList.get(position).setTitle(UserInputParser.parseEditableTextToString((editText.getText())));
            }

            // Updates the reward db to contain the new value
            try {
                rewardRecyclerInterface.updateReward(rewardList.get(position), position);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final EditText costText, levelRequiredText, titleText;
        private final ImageView rewardImage;

        public MyViewHolder(@NonNull View itemView, RewardSettingsRecyclerInterface rewardRecyclerInterface) {
            super(itemView);

            titleText = itemView.findViewById(R.id.rewardTitleText);
            costText = itemView.findViewById(R.id.costText);
            levelRequiredText = itemView.findViewById(R.id.levelRequiredText);
            rewardImage = itemView.findViewById(R.id.rewardImage);

            CardView cardView = itemView.findViewById(R.id.rewardSettingsCard);
            // Adds the ability for the user to remove a card with a long click
            cardView.setOnLongClickListener(view -> {
                if(rewardRecyclerInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        rewardRecyclerInterface.removeCardOnLongClick(pos);
                        return true;
                    }
                }
               return false;
            });
        }
    }
}
