package com.example.trash2cash.RewardSettings;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.IOException;
import java.net.URL;

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
        holder.titleText.setText(String.valueOf(rewardList.get(position).getTitle()));
        setupEditTextListener(holder.titleText, position, "title");

        holder.costText.setText(String.valueOf(rewardList.get(position).getCost()));
        setupEditTextListener(holder.costText, position, "cost");

        holder.levelRequiredText.setText(String.valueOf(rewardList.get(position).getLevel()));
        setupEditTextListener(holder.costText, position, "level");

        try {
            URL url = new URL(rewardList.get(position).getIcon());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.rewardImage.setImageBitmap(bmp);
            holder.rewardImage.setTag(rewardList.get(position).getIcon());
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        public MyViewHolder(@NonNull View itemView, RewardRecyclerInterface rewardRecyclerInterface) {
            super(itemView);

            titleText = itemView.findViewById(R.id.rewardTitleText);
            costText = itemView.findViewById(R.id.costText);
            levelRequiredText = itemView.findViewById(R.id.levelRequiredText);
            rewardImage = itemView.findViewById(R.id.rewardImage);
            ImageView closeButton = itemView.findViewById(R.id.closeButton);

            closeButton.setOnClickListener(view -> {
                if(rewardRecyclerInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        rewardRecyclerInterface.removeCardOnClick(pos);
                    }
                }
            });

            CardView cardView = itemView.findViewById(R.id.rewardSettingsCard);
            cardView.setOnLongClickListener(view -> {
                if(rewardRecyclerInterface != null){
                    int pos = getAdapterPosition();

                    if(pos != RecyclerView.NO_POSITION){
                        rewardRecyclerInterface.moveCardUp(pos);
                        return true;
                    }
                }
               return false;
            });
        }
    }
}
