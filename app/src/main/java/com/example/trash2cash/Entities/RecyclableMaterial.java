package com.example.trash2cash.Entities;

import android.media.Image;

public class RecyclableMaterial {
    private final String type;
    private int exp;
    private int rewardAmount;
    private final int image;

    public RecyclableMaterial(String type, int exp, int rewardAmount, int image) {
        this.type = type;
        this.exp = exp;
        this.rewardAmount = rewardAmount;
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(int rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

    public int getImage() {
        return image;
    }
}
