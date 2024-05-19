package com.example.trash2cash.RewardSettings;

import com.example.trash2cash.Reward;

public interface RewardRecyclerInterface {
    void removeCardOnClick(int position);

    void moveCardUp(int position);

    void updateReward(Reward reward, int position) throws Exception;
}
