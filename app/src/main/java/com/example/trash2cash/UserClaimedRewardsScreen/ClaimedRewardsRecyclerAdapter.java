package com.example.trash2cash.UserClaimedRewardsScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash2cash.Entities.RewardList;
import com.example.trash2cash.R;

import java.io.IOException;
import java.net.URL;

public class ClaimedRewardsRecyclerAdapter extends RecyclerView.Adapter<ClaimedRewardsRecyclerAdapter.MyViewHolder>{
    private final Context context;
    private final RewardList rewardList;


    public ClaimedRewardsRecyclerAdapter(Context context, RewardList rewardList){
        this.context = context;
        this.rewardList = rewardList;
   }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.claimed_rewards_recycler_view, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Sets the title of the card
        holder.titleText.setText(String.valueOf(rewardList.get(position).getTitle()));
        holder.titleText.setMovementMethod(new ScrollingMovementMethod());

        // Sets the cost of the card
        holder.codeText.setText(rewardList.get(position).getCode());
        holder.codeText.setMovementMethod(new ScrollingMovementMethod());

        // Loads the image of the card
        try {
            URL url = new URL(rewardList.get(position).getIcon());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            holder.rewardImage.setImageBitmap(bmp);
            holder.rewardImage.setTag(rewardList.get(position).getIcon());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView titleText, codeText;
        private final ImageView rewardImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.rewardTitleText);
            codeText = itemView.findViewById(R.id.codeText);
            rewardImage = itemView.findViewById(R.id.rewardImage);
        }
    }
}
