package com.example.trash2cash.RecyclableMaterialSettings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trash2cash.DB.OkHttpHandler;
import com.example.trash2cash.Entities.RecyclableMaterial;
import com.example.trash2cash.R;
import com.example.trash2cash.Entities.RecyclableMaterialTypes;
import com.example.trash2cash.RewardSettings.UserInputParser;
import com.squareup.picasso.Picasso;

public class RecyclableMaterialSettingsRecyclerAdapter extends RecyclerView.Adapter<RecyclableMaterialSettingsRecyclerAdapter.MyViewHolder> {
    private final Context context;
    private final RecyclableMaterialTypes recyclableMaterialTypes;

    public RecyclableMaterialSettingsRecyclerAdapter(Context context, RecyclableMaterialTypes recyclableMaterialTypes){
        this.context = context;
        this.recyclableMaterialTypes = recyclableMaterialTypes;
    }

    @NonNull
    @Override
    public RecyclableMaterialSettingsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclable_material_settings_recycler_view, parent, false);
        return new RecyclableMaterialSettingsRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclableMaterialSettingsRecyclerAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(recyclableMaterialTypes.get(position).getType());

        // Loads the background image
        String urlString = OkHttpHandler.getPATH() + recyclableMaterialTypes.get(position).getImage();
        Picasso.with(context).load(urlString).placeholder(R.drawable.ic_launcher_foreground).error(R.drawable.ic_launcher_foreground).into(holder.imageView);

        // Sets the text and a listener for the exp text field
        holder.expNum.setText(String.valueOf(recyclableMaterialTypes.get(position).getExp()));
        setupEdiTextListener(holder.expBar, holder.expNum, position, "exp");

        // Sets the bar and a listener for the exp seek bar
        holder.expBar.setProgress(recyclableMaterialTypes.get(position).getExp());
        setupSeekBarListener(holder.expBar, holder.expNum, position, "exp");

        // Sets the text and a listener for the reward amount text field
        holder.rewardNum.setText(String.valueOf(recyclableMaterialTypes.get(position).getRewardAmount()));
        setupEdiTextListener(holder.rewardBar, holder.rewardNum, position, "rewardAmount");

        // Sets the bar and a listener for the reward amount seek bar
        holder.rewardBar.setProgress(recyclableMaterialTypes.get(position).getRewardAmount());
        setupSeekBarListener(holder.rewardBar, holder.rewardNum, position, "rewardAmount");

        // Sets the text and a listener for the recycle amount text field
        holder.recycleNum.setText(String.valueOf(recyclableMaterialTypes.get(position).getRecycleAmount()));
        setupEdiTextListener(null, holder.recycleNum, position, "recycleAmount");

        // Sets a listener to the update types button that updates the db when clicked
        holder.updateTypesButton.setOnClickListener(view -> {
            holder.view.clearFocus();
            updateTypes(position);
        });
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
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    updateMaterialType(recyclableMaterialTypes.get(position));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void updateMaterialType(RecyclableMaterial materialType) throws Exception {
        OkHttpHandler okHttpHandler = new OkHttpHandler();
        okHttpHandler.updateMaterialType(OkHttpHandler.getPATH()+"updateMaterialType.php?TYPE=\"" + materialType.getType() + "\"&EXP=" + materialType.getExp() + "&REWARD_AMOUNT=" + materialType.getRewardAmount() + "&RECYCLE_AMOUNT=" + materialType.getRecycleAmount());
    }

    private void setupEdiTextListener(SeekBar seekBar, EditText editText, int position, String type) {
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            int amount = UserInputParser.parseEditableTextToInt(editText.getText());

            if(type.equals("exp")){
                if(amount>1000) amount = 1000; // The max amount of xp that an admin can give
                recyclableMaterialTypes.get(position).setExp(amount);
            } else if(type.equals("rewardAmount")){
                if(amount>100) amount = 100; // The max amount of reward points that an admin can give
                recyclableMaterialTypes.get(position).setRewardAmount(amount);
            } else {
                if(amount==0) amount = 1; // The parser returns 0 by default if it can't parse but the minimum amount for recycling should always be 1
                editText.setText(String.valueOf(amount));
                recyclableMaterialTypes.get(position).setRecycleAmount(amount);
            }

            // Updates the seekbar to the amount that the adming tped
            if(seekBar!=null) seekBar.setProgress(amount);
        });
    }

    private void updateTypes(int position){
        try {
            updateMaterialType(recyclableMaterialTypes.get(position));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private final EditText expNum, rewardNum, recycleNum;
        private final ImageView imageView;
        private final SeekBar expBar, rewardBar;
        private final Button updateTypesButton;
        private final View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            view = itemView;
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.profile_photo);
            expBar = itemView.findViewById(R.id.expBar);
            expNum = itemView.findViewById(R.id.expNum);
            rewardBar = itemView.findViewById(R.id.rewardBar);
            rewardNum = itemView.findViewById(R.id.rewardNum);
            recycleNum = itemView.findViewById(R.id.recylceNum);
            updateTypesButton = itemView.findViewById(R.id.updateTypesButton);
        }
    }
}
