package com.example.trash2cash.Entities;


public class RecyclableMaterial {
    private final String type;
    private int exp;
    private int rewardAmount;
    private final String image;

    public RecyclableMaterial(String type, int exp, int rewardAmount, String image) {
        this.type = type;
        this.exp = exp;
        this.rewardAmount = rewardAmount;
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public int getExp() {
        return this.exp;
    }

    public int getRewardAmount(){
        return this.rewardAmount;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setRewardAmount(int rewardAmount) {
        this.rewardAmount = rewardAmount;
    }
}
