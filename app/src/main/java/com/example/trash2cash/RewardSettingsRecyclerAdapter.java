package com.example.trash2cash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RewardSettingsRecyclerAdapter extends RecyclerView.Adapter<RewardSettingsRecyclerAdapter.MyViewHolder> {
    private final Context context;
    private final RecyclableMaterialTypes recyclableMaterialTypes;

    public RewardSettingsRecyclerAdapter(Context context, RecyclableMaterialTypes recyclableMaterialTypes){
        this.context = context;
        this.recyclableMaterialTypes = recyclableMaterialTypes;
    }

    @NonNull
    @Override
    public RewardSettingsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reward_settings_recycler_view, parent, false);
        return new RewardSettingsRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RewardSettingsRecyclerAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(recyclableMaterialTypes.get(position).getType());

        holder.imageView.setImageResource(recyclableMaterialTypes.get(position).getImage());

        holder.expNum.setText(String.valueOf(recyclableMaterialTypes.get(position).getExp()));
        holder.expBar.setProgress(recyclableMaterialTypes.get(position).getExp());
        setupSeekBarListener(holder.expBar, holder.expNum, position, "exp");

        holder.rewardNum.setText(String.valueOf(recyclableMaterialTypes.get(position).getRewardAmount()));
        holder.rewardBar.setProgress(recyclableMaterialTypes.get(position).getRewardAmount());
        setupSeekBarListener(holder.rewardBar, holder.rewardNum, position, "rewardAmount");
    }

    @Override
    public int getItemCount() {
        return recyclableMaterialTypes.size();
    }

    private void setupSeekBarListener(SeekBar seekBar, EditText editText, int position, String type) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editText.setText(String.valueOf(progress));
                if(type.equals("exp")){
                    recyclableMaterialTypes.get(position).setExp(progress);
                } else {
                    recyclableMaterialTypes.get(position).setRewardAmount(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private final EditText expNum, rewardNum;
        private final ImageView imageView;
        private final SeekBar expBar, rewardBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            expBar = itemView.findViewById(R.id.expBar);
            expNum = itemView.findViewById(R.id.expNum);
            rewardBar = itemView.findViewById(R.id.rewardBar);
            rewardNum = itemView.findViewById(R.id.rewardNum);
        }
    }
}
