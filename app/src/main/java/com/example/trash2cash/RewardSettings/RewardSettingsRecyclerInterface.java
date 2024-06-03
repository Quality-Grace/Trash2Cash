package com.example.trash2cash.RewardSettings;

import com.example.trash2cash.Entities.Reward;

public interface RewardSettingsRecyclerInterface {

    void removeCardOnLongClick(int position);

    void updateReward(Reward reward, int position) throws Exception;
}
