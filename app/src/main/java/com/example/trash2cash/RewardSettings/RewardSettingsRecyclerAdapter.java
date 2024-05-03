package com.example.trash2cash.RewardSettings;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash2cash.R;
import com.example.trash2cash.RewardList;

public class RewardSettingsRecyclerAdapter extends RecyclerView.Adapter<RewardSettingsRecyclerAdapter.MyViewHolder> {
    private final Context context;
    private final RewardList rewardList;
    private final RewardRecyclerInterface rewardRecyclerInterface;

    public RewardSettingsRecyclerAdapter(Context context, RewardList rewardList, RewardRecyclerInterface rewardRecyclerInterface){
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
        holder.costText.setText(String.valueOf(rewardList.get(position).getCost()));
        setupEditTextListener(holder.costText, position, "cost");

        holder.levelRequiredText.setText(String.valueOf(rewardList.get(position).getLevel()));
        setupEditTextListener(holder.costText, position, "level");

        holder.rewardImage.setImageResource(rewardList.get(position).getIcon());
        holder.rewardImage.setTag(rewardList.get(position).getIcon());

    }

    private void setupEditTextListener(EditText editText, int position, String type) {
        editText.setOnFocusChangeListener((view, b) -> {
            if(type.equals("cost")){
                rewardList.get(position).setCost(Integer.parseInt(String.valueOf(editText.getText())));
            } else {
                rewardList.get(position).setLevel(Integer.parseInt(String.valueOf(editText.getText())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final EditText costText, levelRequiredText;
        private final ImageView rewardImage, closeButton;
        public MyViewHolder(@NonNull View itemView, RewardRecyclerInterface rewardRecyclerInterface) {
            super(itemView);

            costText = itemView.findViewById(R.id.costText);
            levelRequiredText = itemView.findViewById(R.id.levelRequiredText);
            rewardImage = itemView.findViewById(R.id.rewardImage);
            closeButton = itemView.findViewById(R.id.closeButton);


            closeButton.setOnClickListener(view -> {
                if(rewardRecyclerInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        rewardRecyclerInterface.removeCardOnClick(pos);
                    }
                }
            });
        }
    }
}
